package com.pitchstone.plugin.permit

class BasicSessionlessPermitTagLib {
    static namespace = 'permit'

    def basicSessionlessPermitCheckingService

    /**
     * Prints an HTML-encoded property value of the current permit context.
     * @param property Name of property to print.
     */
    def context = { attrs, body ->
        if (!attrs.property) return

        def pc = permitContext
        if (!pc) return

        def value = pc[attrs.property]
        if (!value) return

        out << value?.toString()?.encodeAsHTML()
    }

    /**
     * Adds the current permit context to the current scope.
     * @param var (optional) Name of context variable (defaults to 'permit').
     */
    def withContext = { attrs, body ->
        def pc = permitContext
        if (!pc) return

        def var = attrs.var ?: 'permit'
        out << body((var): pc)
    }

    /**
     * Executes body if the specified expression evaluates to true
     * for the current permit context.
     * @param test Expression string to test.
     */
    Closure getIf() {
        return { attrs, body ->
            def result = service.check(attrs.test)
            if (result)
                out << body()
            lastTest = result
        }
    }

    /**
     * Executes body if the previous permit:if was false
     * and the specified expression evaluates to true
     * for the current permit context.
     * @param test Expression string to test.
     */
    def elseif = { attrs, body ->
        if (!lastTest)
            this.if(attrs, body)
    }

    /**
     * Executes body if the previous permit:if was false.
     */
    Closure getElse() {
        return { attrs, body ->
            if (!lastTest)
                out << body()
            lastTest = true
        }
    }

    protected getService() {
        basicSessionlessPermitCheckingService
    }

    protected getPermitContext() {
        service.basicSessionlessPermitContextService
    }

    protected boolean getLastTest() {
        request.basicSessionlessPermitTagLibLastTest
    }

    protected void setLastTest(boolean test) {
        request.basicSessionlessPermitTagLibLastTest = test
    }

}
