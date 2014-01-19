package org.c02e.plugin.permit

import spock.lang.Specification

class BasicSessionlessPermitCheckingServiceIntegrationSpec extends Specification {

    def basicSessionlessPermitCheckingService

    def setup() {
        service.controllers = service.buildControllerMap()
    }

    def "public controller has no permits"() {
        expect: service.controllers.testPublic == null
    }

    def "management controller has permit on controller only"() {
        expect: service.controllers.testManagement == [DEFAULT: 'localhost']
    }

    def "resource controller has permit on controller and actions"() {
        expect:
            service.controllers.testResource.DEFAULT == 'false'
            service.controllers.testResource.nope == null
            service.controllers.testResource.admin
            service.controllers.testResource.edit
            service.controllers.testResource.view
    }

    protected getService() {
        basicSessionlessPermitCheckingService
    }

}
