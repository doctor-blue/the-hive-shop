import concurrent.futures
import json
import socket
import re
import web

from error_handler import ErrorHandler
from model import ProductModel, UserModel, createProductModel, createUserModel
from response_handler import ResponseHandler

routes = (
    '/products', 'Product',
    '/authentication', 'User'
)

err_handler = ErrorHandler()
res_handler = ResponseHandler()

products = []
users = []
tokens = []

# get all products
with open('product_test.json', 'r') as file:
    products = createProductModel(json.load(file))

# get all users
with open('users.json', 'r') as file:
    users = createUserModel(json.load(file))
# get Ip Address
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.connect(('8.8.8.8', 80))

ip_address = s.getsockname()[0] + ':8080'

s.close()
print('ip_address = ', ip_address)


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


class User:
    def __init__(self):
        pass

    def GET(self):
        params = web.input(user_name='')
        user_name = params['user_name']
        password = params['password']

        user = self.find_user(user_name)
        if user_name is not None:
            if user.password == password:
                return res_handler.get_with_results(user.to_json())
            else:
                return err_handler.handle_not_found_error({"message": "User name or password is incorrect!"})
        else:
            return err_handler.handle_not_found_error({"message": "User name or password is incorrect!"})

    def find_user(self, user_name):
        for user in users:
            if user.user_name == user_name:
                return user
        return None
    
class Test(Exception):
    pass


if __name__ == "__main__":
    app = web.application(routes, globals())
    app.run()
