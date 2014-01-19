package org.c02e.plugin.permit

import spock.lang.Specification

class BasicSessionlessPermitCheckingServiceSpec extends Specification {

    def controllers = [:]
    def service = new BasicSessionlessPermitCheckingService(controllers: controllers)

    def "when no permits set, does nothing"() {
        when: service.failUnlessActionPermitted 'foo'
        then: notThrown Exception
    }

    def "when no permits set for controller, does nothing"() {
        setup: controllers.bar = [DEFAULT:'false']
        when: service.failUnlessActionPermitted 'foo'
        then: notThrown Exception
    }

    def "when permits set to deny for default action, denies default"() {
        setup: controllers.foo = [DEFAULT:'false']
        when: service.failUnlessActionPermitted 'foo'
        then:
            def e = thrown(NotPermittedException)
            !e.message
    }

    def "when permits set to allow for default action, allows default"() {
        setup: controllers.foo = [DEFAULT:'true']
        when: service.failUnlessActionPermitted 'foo'
        then: notThrown Exception
    }

    def "when no permits for action but permits set to deny for default action, denies"() {
        setup: controllers.foo = [DEFAULT:'false']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then:
            def e = thrown(NotPermittedException)
            !e.message
    }

    def "when no permits for action but permits set to allow for default action, allows"() {
        setup: controllers.foo = [DEFAULT:'true']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then: notThrown Exception
    }

    def "when no permits for action set to deny, denies"() {
        setup: controllers.foo = [DEFAULT:'true', bar:'false']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then:
            def e = thrown(NotPermittedException)
            !e.message
    }

    def "when no permits for action set to allow, allows"() {
        setup: controllers.foo = [DEFAULT:'false', bar:'true']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then: notThrown Exception
    }

    def "when checking permits and no context, throws NotPermittedException with message"() {
        setup: controllers.foo = [DEFAULT:'false', bar:'yup']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then:
            def e = thrown(NotPermittedException)
            e.message == 'failed to evaluate expression: yup'
    }

    def "when checking permits with no context, uses default context"() {
        setup:
            service.basicSessionlessPermitContextService = [yup:true]
            controllers.foo = [DEFAULT:'false', bar:'yup']
        when: service.failUnlessActionPermitted 'foo', 'bar'
        then: notThrown Exception
    }

    def "when checking permits with context, uses specified context"() {
        setup:
            service.basicSessionlessPermitContextService = [yup:false]
            controllers.foo = [DEFAULT:'false', bar:'yup']
        when: service.failUnlessActionPermitted 'foo', 'bar', [yup:true]
        then: notThrown Exception
    }

}
