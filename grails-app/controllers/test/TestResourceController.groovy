package test

import com.pitchstone.plugin.permit.Permit

@Permit('false')
class TestResourceController {

    def nope() { }

    @Permit('inResourceOrganization && "admin" in user.roles')
    def admin = { }

    @Permit('inResourceOrganization && inRole("admin", "editor")')
    def edit() { }

    @Permit('inResourceOrganization && viewer')
    def view() { }
}
