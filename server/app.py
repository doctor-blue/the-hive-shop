import concurrent.futures
import json
import socket

import web
from error_handler import ErrorHandler
from model import *
from response_handler import ResponseHandler

routes = (
    '/products', 'Product',
    '/cart', 'Cart',
    '/authentication/signin', 'SignIn',
    '/authentication/signup', 'SignUp',
    '/authentication/profile', 'Profile'
)

err_handler = ErrorHandler()
res_handler = ResponseHandler()

products = []
cart = []
users = []

# get all products
with open('product_test.json', 'r') as file:
    products = createProductModel(json.load(file))

# get all item from cart
with open('cart.json', 'r') as file:
    cart = createCart(json.load(file))

# get all users (J4F)
with open('users.json', 'r') as file:
    users = createUserModel(json.load(file))

# get Ip Address
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.connect(('8.8.8.8', 80))

ip_address = s.getsockname()[0] + ':8080'

s.close()
print('ip_address = ', ip_address)


def convert_cart_to_json():
    cart_json = []
    for item in cart:
        item_in_cart = []
        for product in item['items']:
            item_in_cart.append(product.to_json())
        cart_json.append({
            'email':  item['email'],
            'items': item_in_cart
        })
    print(cart_json)
    return cart_json


def get_cart_json_by_email(email):
    for item in convert_cart_to_json():
        if item['email'] == email:
            return item['items']
    return []


def get_cart_by_email(email):
    for item in cart:
        if item['email'] == email:
            return item['items']
    return None


def find_item_in_cart(list, product):
    for item in list:
        if item.id == product.id:
            return item


def convert_users_to_json():
    users_json = []
    for item in users:
        users_json.append(item.to_json())
    return users_json


class Product:
    def __init__(self):
        pass

    def GET(self):
        try:
            products_json = []
            for product in products:
                products_json.append(product.to_json())
            return res_handler.get_with_results(products_json)
        except Exception as err:
            return err_handler.handle_server_error(err)


class Cart:
    def __init__(self):
        pass

    def GET(self):
        user = UserModel()
        user.set_data(json.loads(web.webapi.data()))
        try:
            cart_json = get_cart_json_by_email(user.email)
            return res_handler.get_with_results(cart_json)
        except Exception as err:
            return err_handler.handle_server_error(err)

    def POST(self):
        item = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item.set_data(json_data)
        email = json_data['email']

        cart_by_mail = get_cart_by_email(email)
        if cart_by_mail is not None:
            item_in_cart = find_item_in_cart(cart_by_mail, item)
            if item_in_cart is not None:
                item_in_cart.amount = item.amount
            else:
                cart_by_mail.append(item)
        else:
            cart_by_mail = []
            cart_by_mail.append(item)
            cart.append(
                {
                    'email': email,
                    'items': cart_by_mail
                }
            )

        with open('cart.json', 'w') as file:
            json.dump(convert_cart_to_json(), file)
        return res_handler.created_with_results(item.to_json())

    def DELETE(self):
        item_in_cart = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item_in_cart.set_data(json_data)
        email = json_data['email']

        cart_by_mail = get_cart_by_email(email)

        for index, item in enumerate(cart_by_mail):
            if item.id == item_in_cart.id:
                cart_by_mail.pop(index)
                with open('cart.json', 'w') as file:
                    json.dump(convert_cart_to_json(), file)
                return res_handler.deleted_with_results(item.to_json())

    def PATCH(self):
        item = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item.set_data(json_data)
        email = json_data['email']

        cart_by_mail = get_cart_by_email(email)
        if cart_by_mail is not None:
            item_in_cart = find_item_in_cart(cart_by_mail, item)
            if item_in_cart is not None:
                item_in_cart.amount = item.amount
                with open('cart.json', 'w') as file:
                    json.dump(convert_cart_to_json(), file)
                return res_handler.created_with_results(item.to_json())
        return err_handler.handle_not_found_error("Cannot find product in cart")


def find_user(email):
    for user in users:
        if user.email == email:
            return user
    return None
# J4F


class SignIn:
    def __init__(self):
        pass

    def POST(self):
        user_obj = UserModel()
        user_obj.set_data(json.loads(web.webapi.data()))

        user = find_user(user_obj.email)
        if user is not None:
            if user.password == user_obj.password:
                return res_handler.get_with_results(user.to_json())
            else:
                return err_handler.handle_input_error({'message': 'Email or password is incorrect!'})
        else:
            return err_handler.handle_input_error({'message': 'Email or password is incorrect!'})


class SignUp:
    def __init__(self):
        pass

    def POST(self):
        user_obj = UserModel()
        user_obj.set_data(json.loads(web.webapi.data()))

        user = find_user(user_obj.email)

        if user is None:
            users.append(user_obj)
            with open('users.json', 'w') as file:
                json.dump(convert_users_to_json(), file)
        else:
            return err_handler.handle_input_error({"message": "Email is already taken"})


class Profile:
    def __init__(self):
        pass

    def PUT(self):
        user_obj = UserModel()
        user_obj.set_data(json.loads(web.webapi.data()))

        user = find_user(user_obj.email)
        if user is not None:
            user.address = user_obj.address
            user.is_male = user_obj.is_male
            user.password = user_obj.password

            with open('users.json', 'w') as file:
                json.dump(convert_users_to_json(), file)

            return res_handler.get_with_results(user_obj.to_json())
        else:
            return err_handler.handle_input_error({"message": "User not found!"})


if __name__ == "__main__":
    app = web.application(routes, globals())
    app.run()
