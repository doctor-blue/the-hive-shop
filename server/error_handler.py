import web
import json


class ErrorHandler(Exception):
    def __init__(self):
        pass

    # Will return 400 BadRequest with error message
    def handle_property_error(self, message):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.BadRequest(message)
        return json.dumps(message)

    # Will return 404 Not Found
    def handle_not_found_error(self, message):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.NotFound(message)
        return json.dumps(message)

    # Will return 400 BadRequest with error message
    def handle_input_error(self, message):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.BadRequest(message)
        return json.dumps(message)

    # Will return 500 InternalError with error message
    def handle_server_error(self, message):
        web.webapi.header('Content-Type', 'application/json', unique=True)
        web.webapi.InternalError(message)
        return json.dumps(message)
