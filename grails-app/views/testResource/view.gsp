<!doctype html>
<html>
<head>
    <title>View <permit:context property="resource" /></title>
</head>
<body>
    <h1>View <permit:context property="resource" /></h1>

    <permit:withContext>
        <h3>${permit.resource.type.capitalize()} Organizations</h3>
        <ul>
            <g:each var="item" in="${permit.resource.organizations.sort()}">
                <li>${item}
            </g:each>
        </ul>
    </permit:withContext>

    <p><g:link controller="testPublic">Home</g:link></p>
</body>
</html>
