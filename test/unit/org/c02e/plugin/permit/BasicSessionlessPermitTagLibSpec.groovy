package org.c02e.plugin.permit

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BasicSessionlessPermitTagLib)
@Mock(BasicSessionlessPermitCheckingService)
class BasicSessionlessPermitTagLibSpec extends Specification {

	def "context renders nothing by default"() {
        setup: demandContext(null)
        expect:
            applyTemplate('<permit:context />') == ''
            applyTemplate('<permit:context property="foo" />') == ''
	}

	def "context renders property when remembered and specified"() {
        setup: demandContext foo: 'bar'
        expect: applyTemplate('<permit:context property="foo" />') == 'bar'
	}

	def "context renders nothing when no property"() {
        setup: demandContext foo: 'bar'
        expect: applyTemplate('<permit:context />') == ''
	}

	def "context renders nothing when property is falsey"() {
        setup: demandContext foo: false
        expect: applyTemplate('<permit:context property="foo" />') == ''
	}

	def "context renders objects to string"() {
        setup: demandContext foo: [1]
        expect: applyTemplate('<permit:context property="foo" />') == '[1]'
	}

	def "context encodes value as HTML"() {
        setup: demandContext foo: 'M&M'
        expect: applyTemplate('<permit:context property="foo" />') == 'M&amp;M'
	}


	def "withContext renders nothing by default"() {
        expect:
            applyTemplate('<permit:withContext>foo</permit:withContext>') == ''
            applyTemplate('<permit:withContext var="p">foo</permit:withContext>') == ''
	}

	def "withContext renders body when remembered"() {
        setup: demandContext foo: 'bar'
        expect: applyTemplate(
            '<permit:withContext>foo</permit:withContext>'
        ) == 'foo'
	}

	def "withContext makes context properties available via context var by default"() {
        setup: demandContext foo: 'bar'
        expect: applyTemplate(
            '<permit:withContext>${permit.foo}</permit:withContext>'
        ) == 'bar'
	}

	def "withContext makes context properties available via custom var"() {
        setup: demandContext foo: 'bar'
        expect: applyTemplate(
            '<permit:withContext var="p">${p.foo}</permit:withContext>'
        ) == 'bar'
	}


	def "if renders body by default"() {
        expect: applyTemplate('<permit:if>if</permit:if>') == 'if'
	}

	def "when true, if renders body"() {
        expect: applyTemplate('<permit:if test="true">if</permit:if>') == 'if'
	}

	def "when false, if renders nothing"() {
        expect: applyTemplate('<permit:if test="false">if</permit:if>') == ''
	}


	def "elseif renders body by default"() {
        expect: applyTemplate('<permit:elseif>elseif</permit:elseif>') == 'elseif'
	}

	def "when true, elseif renders body"() {
        expect: applyTemplate('<permit:elseif test="true">elseif</permit:elseif>') == 'elseif'
	}

	def "when false, elseif renders nothing"() {
        expect: applyTemplate('<permit:elseif test="false">elseif</permit:elseif>') == ''
	}

	def "first and second elseif renders nothing if previous if was true"() {
        expect: applyTemplate('''
            <permit:if test="true">if</permit:if>
            <permit:elseif test="true">elseif 1</permit:elseif>
            <permit:elseif test="true">elseif 2</permit:elseif>
        ''').trim() == 'if'
	}

	def "first elseif renders body if previous if was false"() {
        expect: applyTemplate('''
            <permit:if test="false">if</permit:if>
            <permit:elseif test="true">elseif 1</permit:elseif>
            <permit:elseif test="true">elseif 2</permit:elseif>
        ''').trim() == 'elseif 1'
	}

	def "secound elseif renders body if first elseif was false"() {
        expect: applyTemplate('''
            <permit:if test="false">if</permit:if>
            <permit:elseif test="false">elseif 1</permit:elseif>
            <permit:elseif test="true">elseif 2</permit:elseif>
        ''').trim() == 'elseif 2'
	}


	def "else renders body by default"() {
        expect: applyTemplate('<permit:else>else</permit:else>') == 'else'
	}

	def "else renders nothing if previous if was true"() {
        expect: applyTemplate('''
            <permit:if test="true">if</permit:if>
            <permit:else>else</permit:else>
        ''').trim() == 'if'
	}

	def "else renders body if previous if was false"() {
        expect: applyTemplate('''
            <permit:if test="false">if</permit:if>
            <permit:else>else</permit:else>
        ''').trim() == 'else'
	}

	def "else renders nothing if previous elseif was true"() {
        expect: applyTemplate('''
            <permit:if test="false">if</permit:if>
            <permit:elseif test="true">elseif</permit:elseif>
            <permit:else>else</permit:else>
        ''').trim() == 'elseif'
	}

	def "else renders body if previous elseif was false"() {
        expect: applyTemplate('''
            <permit:if test="false">if</permit:if>
            <permit:elseif test="false">elseif</permit:elseif>
            <permit:else>else</permit:else>
        ''').trim() == 'else'
	}


	def "nested if else renders correctly"() {
        expect: applyTemplate('''
            <permit:if test="true">
                <permit:if test="false">
                    TF
                </permit:if>
                <permit:else>
                    <permit:if test="true">
                        TFT
                    </permit:if>
                    <permit:else>
                        TFF
                    </permit:else>
                </permit:else>
            </permit:if>
            <permit:else>
                F
            </permit:else>
            <permit:if test="true">
                +T
            </permit:if>
            <permit:if test="false">
                -F
            </permit:if>
            <permit:else>
                +F
            </permit:else>
        ''').replaceAll(/\s+/, '') == 'TFT+T+F'
	}


    protected service(Closure c) {
        def control = mockFor(BasicSessionlessPermitCheckingService)
        c.call(control)
        tagLib.basicSessionlessPermitCheckingService = control.createMock()
    }

    protected demandContext(Map context = [:], int times = 1) {
        service {
            it.demand.getBasicSessionlessPermitContextService((times)..(times)) { ->
                context
            }
        }
    }
}
