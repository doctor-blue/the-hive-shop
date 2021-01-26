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
        cart_json.append(item.to_json())
    return cart_json


def convert_users_to_json():
    users_json = []
    for item in users:
        users_json.append(item.to_json())
    return users_json


def find_item_in_cart(product):
    for item in cart:
        if item.id == product.id:
            return item
    return None


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
        try:
            cart_json = convert_cart_to_json()
            return res_handler.get_with_results(cart_json)
        except Exception as err:
            return err_handler.handle_server_error(err)

    def POST(self):
        item_in_cart = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item_in_cart.set_data(json_data)

        item = find_item_in_cart(item_in_cart)
        if item is None:
            cart.append(item_in_cart)
            with open('cart.json', 'w') as file:
                json.dump(convert_cart_to_json(), file)
            return res_handler.created_with_results(item_in_cart.to_json())

    def DELETE(self):
        item_in_cart = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item_in_cart.set_data(json_data)

        for index, item in enumerate(cart):
            print('item ', item.id, item_in_cart.id)
            if item.id == item_in_cart.id:
                cart.pop(index)
                with open('cart.json', 'w') as file:
                    json.dump(convert_cart_to_json(), file)
                return res_handler.deleted_with_results(item_in_cart.to_json())

    def PATCH(self):
        item_in_cart = ItemInCart()
        json_data = json.loads(web.webapi.data())
        item_in_cart.set_data(json_data)

        item = find_item_in_cart(item_in_cart)
        if item is not None:
            item.amount = item_in_cart.amount
            with open('cart.json', 'w') as file:
                json.dump(convert_cart_to_json(), file)
            return res_handler.created_with_results(item_in_cart.to_json())


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

    def POST(self):
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
