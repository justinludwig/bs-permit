package test

/**
 * Sample helper for this app's silly user management system.
 */
class TestManagementService {
    static transactional = false

    List<String> organizations = 'blues greens reds'.split(/ /) as List
    List<String> roles = 'admin editor viewer'.split(/ /) as List

    TestUser user = new TestUser()
}
