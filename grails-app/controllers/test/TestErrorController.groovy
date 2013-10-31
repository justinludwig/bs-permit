package test

class TestErrorController {
    def notPermitted() {
        response.status = 403
        render view: 'index', model: [
            title: 'Not Authorized',
            message: 'Sorry, you are not permitted to access this resource',
        ]
    }
}
