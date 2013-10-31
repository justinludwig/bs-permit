package test

/**
 * Sample filters for initializing permit context.
 */
class TestContextFilters {
    def basicSessionlessPermitContextService
    def testManagementService

    def filters = {
        checkPermit(controller: '*') { before = {
            if (params.type)
                basicSessionlessPermitContextService.resource = new TestResource(
                    id: params.id ==~ /\d+/ ? params.id as Long : 0,
                    organizations: testManagementService.organizations.findAll {
                        params.type.contains it[0]
                    },
                    type: params.type,
                )
            basicSessionlessPermitContextService.user = testManagementService.user
        } }
    }
}
