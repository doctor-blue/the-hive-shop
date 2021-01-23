import concurrent.futures
import json
import socket

import web
from error_handler import ErrorHandler
from model import ProductModel, createProductModel, createCart
from response_handler import ResponseHandler

routes = (
    '/products', 'Product',
    '/cart', 'Cart'
)

err_handler = ErrorHandler()
res_handler = ResponseHandler()

products = []
cart = []

# get all products
with open('product_test.json', 'r') as file:
    products = createProductModel(json.load(file))

# get all item from cart
with open('cart.json', 'r') as file:
    cart = createCart(json.load(file))

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


if __name__ == "__main__":
    app = web.application(routes, globals())
    app.run()
