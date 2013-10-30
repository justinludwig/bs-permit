package com.pitchstone.plugin.permit

/**
 * Throws NotPermittedException if action not permitted.
 */
class BasicSessionlessPermitFilters {
    def basicSessionlessPermitCheckingService
    def grailsApplication

    def getDependsOn() {
        def depends = config?.dependsOn
        if (!depends) return []

        if (depends instanceof Class)
            depends = [depends]
        else if (!(depends instanceof Collection))
            depends = depends.toString().split(/\s*[,\s]+/) as List

        depends.findAll { it }.collect { depend ->
            if (!(depend instanceof Class))
                depend = forName(depend.toString())
            return depend
        }
    }

    def filters = {
        checkPermit(config?.rule ?: [controller: '*']) { before = {
            basicSessionlessPermitCheckingService.failUnlessActionPermitted(
                controllerName, actionName)
        } }
    }

    protected getConfig() {
        grailsApplication?.config?.grails?.plugin?.basicSessionlessPermit?.filter
    }

    protected Class forName(String name) {
        Class.forName name, true, Thread.currentThread().contextClassLoader
    }
}
