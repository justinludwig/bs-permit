<!doctype html>
<html>
<head>
    <title>BS Permit Test</title>
</head>
<body>
    <h1>BS Permit Test</h1>

    <permit:if test="!viewer">
        <p style="color:red">You don't have any roles yet --
            use the Manage link to give yourself some.</p>
    </permit:if>
    <permit:elseif test="!user.organizations">
        <p style="color:magenta">You also don't have any organizations yet --
            go back to the Manage link to give yourself some.</p>
    </permit:elseif>

    <ul>
        <li>Blueberries
            | <g:link controller="testResource" action="view"
                params="[type:'blueberries']">View</g:link>
            | <g:link controller="testResource" action="edit"
                params="[type:'blueberries']">Edit</g:link>
            | <g:link controller="testResource" action="admin"
                params="[type:'blueberries']">Admin</g:link>
            | <g:link controller="testResource" action="nope"
                params="[type:'blueberries']">Nope</g:link>
        <li>Grapes
            | <g:link controller="testResource" action="view"
                params="[type:'grapes']">View</g:link>
            | <g:link controller="testResource" action="edit"
                params="[type:'grapes']">Edit</g:link>
            | <g:link controller="testResource" action="admin"
                params="[type:'grapes']">Admin</g:link>
            | <g:link controller="testResource" action="nope"
                params="[type:'grapes']">Nope</g:link>
        <li>Watermelons
            <ol>
            <g:each var="id" in="${1..5}">
                <li>
                    <g:link controller="testResource" action="view" id="${id}"
                        params="[type:'watermelons']">View</g:link> |
                    <g:link controller="testResource" action="edit" id="${id}"
                        params="[type:'watermelons']">Edit</g:link> |
                    <g:link controller="testResource" action="admin" id="${id}"
                        params="[type:'watermelons']">Admin</g:link>
            </g:each>
            </ol>
    </ul>

    <p><g:link controller="testManagement">Manage</g:link></p>
</body>
</html>
