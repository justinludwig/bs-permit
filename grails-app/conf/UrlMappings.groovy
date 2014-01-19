import org.c02e.plugin.permit.NotPermittedException

class UrlMappings {
	static mappings = {
        '500' controller: 'testError', action: 'notPermitted',
            exception: NotPermittedException
		'500' view: '/error'
        '/' controller: 'testPublic'
        '/management' controller: 'testManagement', action: 'index'
        "/management/$action?" controller: 'testManagement'
        "/resource/$type/$action/$id?" controller: 'testResource'
	}
}
