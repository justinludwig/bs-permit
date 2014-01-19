package org.c02e.plugin.permit

import org.codehaus.groovy.grails.commons.GrailsClassUtils

/**
 * Checks permit expressions.
 */
class BasicSessionlessPermitCheckingService {
    static transactional = false

    /** Map of controllerNames to a map of actionNames to expressions to check. */
    volatile protected Map<String,Map<String,String>> controllers

    def basicSessionlessPermitContextService
    def grailsApplication

    /**
     * Returns true if the specified expression evaluates to true
     * for the specified context.
     * @param expression Expression to evaluate.
     * @param context (optional) Expression context; defaults to
     * basicSessionlessPermitContextService bean reference.
     */
    boolean check(String expression, context = null) {
        if (!expression) return true
        context = context ?: basicSessionlessPermitContextService

        try {
            Eval.x context, "x.with { $expression }"
        } catch (e) {
            log.error "failed to evaluate expression: $expression", e
            throw e
        }
    }

    /**
     * Throws an exception unless the specified expression evaluates to true
     * for the specified context.
     * @param expression Expression to evaluate.
     * @param context (optional) Expression context; defaults to
     * basicSessionlessPermitContextService bean reference.
     * @throws NotPermittedException unless expression evaluates to true.
     */
    void failUnless(String expression, context = null) {
        def permitted = false
        try {
            permitted = check(expression, context)
        } catch (e) {
            throw new NotPermittedException(
                "failed to evaluate expression: $expression", e)
        }
        if (!permitted)
            throw new NotPermittedException()
    }

    /**
     * Throws an exception unless the expression specified by the @Permit annotation
     * for the specified controller action evaluates to true
     * for the specified context.
     * @param controller Controller name.
     * @param action Action name (or null for default action).
     * @param context (optional) Expression context; defaults to
     * basicSessionlessPermitContextService bean reference.
     * @throws NotPermittedException unless expression evaluates to true.
     */
    void failUnlessActionPermitted(String controller, String action = null, context = null) {
        if (controllers == null)
            controllers = buildControllerMap()

        def actions = controllers[controller]
        if (!actions) return

        def expr = actions[action] ?: actions.DEFAULT
        if (!expr) return

        log.info "check '$expr' for $controller/$action"
        failUnless expr, context
    }

    /**
     * Not thread-safe; intended for dev-mode only.
     */
    void clearControllerMap() {
        controllers = null
    }

    protected Map buildControllerMap() {
        grailsApplication.controllerClasses.inject([:]) { map, controller ->
            def actions = [:]

            def controllerPolicy = getPolicy(controller.clazz)
            // skip if empty to keep map smaller
            if (controllerPolicy)
                actions.DEFAULT = controllerPolicy

            // closure actions
            controller.clazz.declaredFields.findAll { field ->
                !(field.name ==~ /class|metaClass|\$.*/)
            }.each { field ->
                def policy = getPolicy(field)
                // skip if empty or same as controller to keep map smaller
                if (policy && policy != controllerPolicy)
                    actions[field.name] = policy
            }

            // method actions
            controller.clazz.declaredMethods.each { field ->
                def policy = getPolicy(field)
                // skip if empty or same as controller to keep map smaller
                if (policy && policy != controllerPolicy)
                    actions[field.name] = policy
            }

            // handle url-mappings where actionName is not specified
            def defaultAction = GrailsClassUtils.getStaticPropertyValue(
                controller.clazz, 'defaultAction') ?: 'index'
            def defaultActionPolicy = actions[defaultAction]
            // skip if empty or same as controller to keep map smaller
            if (defaultActionPolicy && defaultActionPolicy != controllerPolicy)
                actions[null] = defaultActionPolicy

            if (actions)
                map[controller.logicalPropertyName] = actions
            return map
        }
    }

    protected String getPolicy(entity) {
        entity.getAnnotation(Permit.class)?.value()
    }
}
