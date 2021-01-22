import web
import json


class ResponseHandler:
    def __init__(self):
        pass

    # Will return 200 with results
    def get_with_results(self, data):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.OK(data='OK', headers={})
        return json.dumps(data)

    # Will return 201 with results
    def created_with_results(self, thermostat):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.Created(data='Created', headers={})
        return json.dumps(thermostat)

    # Will return 200 with results
    def updated_with_results(self, thermostat):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.OK(data='OK', headers={})
        return json.dumps(thermostat)

    # Will return 200 with result
    def deleted_with_results(self, id):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.OK(data='OK', headers={})
        return json.dumps({'Deleted': id})
