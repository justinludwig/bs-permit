package org.c02e.plugin.permit

import spock.lang.Specification

class BasicSessionlessPermitFiltersSpec extends Specification {

    def filters = new BasicSessionlessPermitFilters(
        grailsApplication: new ConfigObject(),
    )

    def "un-configured dependsOn is empty list"() {
        expect: filters.dependsOn == []
    }

    def "dependsOn configured with single class is converted to list"() {
        setup: config.dependsOn = TestObjectOne
        expect: filters.dependsOn*.name*.replaceFirst(/.*\./, '') ==
            ['TestObjectOne']
    }

    def "dependsOn configured with class list is passed thru"() {
        setup: config.dependsOn = [TestObjectOne, TestObjectTwo]
        expect: filters.dependsOn*.name*.replaceFirst(/.*\./, '') ==
            ['TestObjectOne', 'TestObjectTwo']
    }

    def "dependsOn configured with string list is loaded as classes"() {
        setup: config.dependsOn = [
            "${this.class.package.name}.TestObjectOne",
            "${this.class.package.name}.TestObjectTwo",
        ]
        expect: filters.dependsOn*.name*.replaceFirst(/.*\./, '') ==
            ['TestObjectOne', 'TestObjectTwo']
    }

    def "dependsOn configured with single string is loaded as list of classes"() {
        setup: config.dependsOn = """
            ${this.class.package.name}.TestObjectOne,
            ${this.class.package.name}.TestObjectTwo,
        """
        expect: filters.dependsOn*.name*.replaceFirst(/.*\./, '') ==
            ['TestObjectOne', 'TestObjectTwo']
    }

    protected getConfig() {
        filters.grailsApplication.config.grails.plugin.basicSessionlessPermit.filter
    }

}

class TestObjectOne {
}

class TestObjectTwo {
}
