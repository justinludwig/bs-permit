package org.c02e.plugin.permit

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.codehaus.groovy.grails.web.errors.GrailsExceptionResolver
import org.springframework.web.servlet.ModelAndView

/**
 * Suppresses logging for exception class names listed by suppress property.
 */
public class LogSuppressingExceptionResolver extends GrailsExceptionResolver {
    Collection<String> suppress = [NotPermittedException.class.name] as HashSet

    ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception exception) {

        // GRAILS-10471
        request.'org.codehaus.groovy.grails.FORWARD_IN_PROGRESS' = true
        super.resolveException request, response, handler, exception
    }

    protected void logStackTrace(Exception e, HttpServletRequest request) {
        // skip if exception is, or is subclass of, a suppressed class
        for (def check = e?.class; check != null; check = check.superclass)
            if (check.name in suppress)
                return
        super.logStackTrace e, request
    }

}
