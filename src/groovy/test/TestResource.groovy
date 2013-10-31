package test

/**
 * Sample resource object.
 */
class TestResource {
    long id
    List<String> organizations = []
    String type = ''

    String toString() {
        def s = type?.capitalize() ?: 'Unknown'
        id ? "${s} #${id}" : s
    }
}
