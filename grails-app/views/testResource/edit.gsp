<!doctype html>
<html>
<head>
    <title>Edit <permit:context property="resource" /></title>
</head>
<body>
    <h1>Edit <permit:context property="resource" /></h1>

    <permit:withContext>
        <h3>${permit.resource.type.capitalize()} Organizations</h3>
        <g:textArea name="organizations"
            value="${permit.resource.organizations.sort().join('\n')}" />
    </permit:withContext>

    <p><g:link controller="testPublic">Home</g:link></p>
</body>
</html>
