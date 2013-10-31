package com.pitchstone.plugin.permit

import javax.servlet.http.HttpServletRequest
import org.codehaus.groovy.grails.web.errors.GrailsExceptionResolver

/**
 * Suppresses logging for exception class names listed by suppress property.
 */
public class LogSuppressingExceptionResolver extends GrailsExceptionResolver {
    Collection<String> suppress = [NotPermittedException.class.name] as HashSet

    protected void logStackTrace(Exception e, HttpServletRequest request) {
        if (!(e?.class?.name in suppress))
            super.logStackTrace e, request
    }

}
