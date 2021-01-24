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
        product = ProductModel()
        product.set_data(json.loads(web.webapi.data()))

        for index, item in enumerate(cart):
            if item.id == product.id:
                item.increase_amount(product.price)
                with open('cart.json', 'w') as file:
                    json.dump(convert_cart_to_json(), file)
                return res_handler.created_with_results(product.to_json())

        cart.append(product.convert_to_item_in_cart())
        with open('cart.json', 'w') as file:
            json.dump(convert_cart_to_json(), file)
        return res_handler.created_with_results(product.to_json())

    def DELETE(self):
        product = ProductModel()
        product.set_data(json.loads(web.webapi.data()))
        print("product", product.title)
        for index, item in enumerate(cart):
            if item.id == product.id:
                print("index", index)
                cart.pop(index)
                return res_handler.created_with_results(product.to_json())


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
