class ProductModel:
    def __init__(self, id=0, title="", url="", price=0, description=""):
        self.id = id
        self.title = title
        self.url = url
        self.price = price
        self.description = description

    def set_data(self, data):
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


class UserModel:
    def __init__(self, user_name, password, email, address, is_male, is_admin):
        self.user_name = user_name
        self.password = password
        self.email = email
        self.address = address
        self.is_male = is_male
        self.is_admin = is_admin

    def set_data(self, data):
        self.user_name = data['user_name']
        self.password = data['password']
        self.address = data['address']
        self.is_male = data['is_male']
        self.is_admin = data['is_admin']
        self.email = data['email']
        return self

    def to_json(self):
        data = {
            'user_name': self.user_name,
            'password': self.password,
            'address': self.address,
            'is_male': self.is_male,
            'is_admin': self.is_admin,
            'email': self.email
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
                data['description']
            )
        )
    return products


def createUserModel(list_data):
    users = []
    for data in list_data:
        users.append(
            UserModel(
                data['user_name'],
                data['password'],
                data['email'],
                data['address'],
                data['is_male'],
                data['is_admin']
            )
        )
    return users
