<!doctype html>
<html>
<head>
    <title>BS Permit Management</title>
</head>
<body>
    <h1>BS Permit Management</h1>
    <g:form action="updateUser">
        <fieldset>
            <legend>Organizations</legend>
            <g:each var="item" in="${organizations}">
                <label for="organizations.${item}"><g:checkBox
                    name="organizations.${item}"
                    checked="${item in user.organizations}" />
                    ${item.capitalize()}</label>
            </g:each>
        </fieldset>
        <fieldset>
            <legend>Roles</legend>
            <g:each var="item" in="${roles}">
                <label for="roles.${item}"><g:checkBox
                    name="roles.${item}"
                    checked="${item in user.roles}" />
                    ${item.capitalize()}</label>
            </g:each>
        </fieldset>
        <button type="submit">Update</button>
    </g:form>
    <p><g:link controller="testPublic">Home</g:link></p>
</body>
</html>
