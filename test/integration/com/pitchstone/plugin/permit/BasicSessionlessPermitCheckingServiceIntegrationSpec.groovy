package com.pitchstone.plugin.permit

import grails.plugin.spock.IntegrationSpec

class BasicSessionlessPermitCheckingServiceIntegrationSpec extends IntegrationSpec {

    def basicSessionlessPermitCheckingService

    def setupSpec() {
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
