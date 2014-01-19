package test

import org.c02e.plugin.permit.Permit

@Permit('localhost')
class TestManagementController {
    def testManagementService

    def index() {
        [
            organizations: testManagementService.organizations,
            roles: testManagementService.roles,
            user: testManagementService.user,
        ]
    }

    def updateUser() {
        testManagementService.user.organizations = params.organizations.
            findAll { k,v -> v }.collect { k,v -> k }
        testManagementService.user.roles = params.roles.
            findAll { k,v -> v }.collect { k,v -> k }
        redirect action: 'index'
    }
}
