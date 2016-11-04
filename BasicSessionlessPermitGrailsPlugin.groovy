class BasicSessionlessPermitGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        'grails-app/views/error.gsp',
        'grails-app/*/test*/*.*',
        'src/*/test/*.*',
    ]

    def observe = ['controllers']

    def title = "Basic Sessionless Permit Plugin" // Headline display name of the plugin
    def author = "Justin Ludwig"
    def authorEmail = "justin@codetechnology.com"
    def description = '''
Provides @Permit annotation for checking permisssions.
'''.trim()

    // URL to the plugin's documentation
    def documentation = ''//"http://grails.org/plugin/basic-sessionless-permit"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [
        name: "CODE Technology",
        url: "http://www.codetechnology.com/",
    ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/justinludwig/bs-permit" ]

    def onChange = { event ->
        if (event.source && application.isControllerClass(event.source))
            event.ctx.basicSessionlessPermitCheckingService.clearControllerMap()
    }
}
