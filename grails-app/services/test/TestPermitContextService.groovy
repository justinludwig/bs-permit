package test

import org.springframework.web.context.request.RequestAttributes as RA
import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * Sample implementation for basicSessionlessPermitContextService,
 * providing context for permit checks.
 */
class TestPermitContextService {
    static transactional = false

    /**
     * Current user or null.
     */
    TestUser getUser() {
        getAttr 'test-user'
    }

    /**
     * Current user or null.
     */
    void setUser(TestUser user) {
        setAttr 'test-user', user
    }

    /**
     * Current resource or null.
     */
    TestResource getResource() {
        getAttr 'test-resource'
    }

    /**
     * Current resource or null.
     */
    void setResource(TestResource resource) {
        setAttr 'test-resource', resource
    }

    /**
     * True if the user belongs to at least one organization
     * to which the resource also belongs.
     */
    boolean isInResourceOrganization() {
        !user?.organizations?.disjoint(resource?.organizations)
    }

    /**
     * True if the user is in any of the specified roles.
     */
    boolean inRole(String... roles) {
        !user?.roles?.disjoint(roles as List)
    }

    /**
     * True if the user belongs to either the viewer, editor, or admin roles.
     */
    boolean isViewer() {
        inRole 'viewer', 'editor', 'admin'
    }

    /**
     * True if request is from localhost.
     */
    boolean isLocalhost() {
        RCH?.requestAttributes?.request?.remoteAddr == '127.0.0.1'
    }

    /**
     * Returns the specified request attribute or null.
     */
    protected Object getAttr(key) {
        RCH?.requestAttributes?.getAttribute key, RA.SCOPE_REQUEST
    }

    /**
     * Sets the specified request attribute.
     */
    protected void setAttr(key, value) {
        if (value == null)
            RCH?.requestAttributes?.removeAttribute key, RA.SCOPE_REQUEST
        else
            RCH?.requestAttributes?.setAttribute key, value, RA.SCOPE_REQUEST
    }
}
