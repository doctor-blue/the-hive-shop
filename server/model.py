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
        return ItemInCart(self.id, self.title, self.url, float(self.price), self.description, amount=1)


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
        self.price = float(data['price'])
        self.description = data['description']
        self.amount = int(data['amount'])
        return self

    def to_json(self):
        data = {
            'id': self.id,
            'title': self.title,
            'url': self.url,
            'price': float(self.price),
            'description': self.description,
            'amount': int(self.amount)
        }
        return data


class UserModel:
    def __init__(self, email="", password="", address="", is_male=False, is_admin=False, date_of_birth="", phone_number="",):
        self.password = password
        self.email = email
        self.address = address
        self.is_male = is_male
        self.is_admin = is_admin
        self.date_of_birth = date_of_birth
        self.phone_number = phone_number

    def set_data(self, data):
        self.password = data['password']
        self.address = data['address']
        self.is_male = data['is_male']
        self.is_admin = data['is_admin']
        self.email = data['email']
        self.date_of_birth = data['date_of_birth']
        self.phone_number = data['phone_number']
        return self

    def to_json(self):
        data = {
            'password': self.password,
            'address': self.address,
            'is_male': self.is_male,
            'is_admin': self.is_admin,
            'email': self.email,
            'date_of_birth': self.date_of_birth,
            'phone_number': self.phone_number
        }
        return data


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
        items = data['items']
        items_in_cart = []
        for item in items:
            items_in_cart.append(
                ItemInCart(
                    item['id'],
                    item['title'],
                    item['url'],
                    float(item['price']),
                    item['description'],
                    int(item['amount'])
                )
            )
        cart.append(
            {
                'email': data['email'],
                'items': items_in_cart
            }
        )
    return cart


def createUserModel(list_data):
    users = []
    for data in list_data:
        users.append(
            UserModel(
                data['email'],
                data['password'],
                data['address'],
                data['is_male'],
                data['is_admin'],
                data['date_of_birth'],
                data['phone_number']
            )
        )
    return users
