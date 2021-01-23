class ProductModel:
    def __init__(self, id=0, title="", url="", price=0, description=""):
        self.id = id
        self.title = title
        self.url = url
        self.price = price
        self.description = description

    def set_data(self, data):
        self.id = data['id']
        self.title = data['title']
        self.url = data['url']
        self.price = data['price']
        self.description = data['description']
        return self

    def to_json(self):
        data = {
            'id': self.id,
            'title': self.title,
            'url': self.url,
            'price': self.price,
            'description': self.description,
        }
        return data

    def convert_to_item_in_cart(self):
        return ItemInCart(self.id, self.title,self.url ,float(self.price), self.description, amount=1)


class ItemInCart:
    def __init__(self, id=0, title="", url="", price=0, description="", amount=0):
        self.id = id
        self.title = title
        self.url = url
        self.price = price
        self.description = description
        self.amount = amount

    def set_data(self, data):
        self.id = data['id']
        self.title = data['title']
        self.url = data['url']
        self.price = data['price']
        self.description = data['description']
        self.amount = data['amount']
        return self

    def to_json(self):
        data = {
            'id': self.id,
            'title': self.title,
            'url': self.url,
            'price': self.price,
            'description': self.description,
            'amount': self.amount
        }
        return data

    def increase_amount(self, price):
        self.price = self.price+float(price)
        self.amount += 1


def createProductModel(list_data):
    products = []
    for data in list_data:
        products.append(
            ProductModel(
                data['id'],
                data['title'],
                data['url'],
                data['price'],
                data['description'],
                
            )
        )
    return products


def createCart(list_data):
    cart = []
    for data in list_data:
        cart.append(
            ItemInCart(
                data['id'],
                data['title'],
                data['url'],
                float(data['price']),
                data['description'],
                int(data['amount'])
            )
        )
    return cart
