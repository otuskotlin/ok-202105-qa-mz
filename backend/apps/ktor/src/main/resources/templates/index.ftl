<#-- @ftlvariable name="entries" type="kotlin.collections.List<com.jetbrains.handson.website.BlogEntry>" -->

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Opinion</title>
</head>
<body style="text-align: center; font-family: sans-serif">
<h1>Your Honest Opinion</h1>
<hr>
<h2>Questions</h2>
<#list questions as question>
    <div>
        <h3>${question.title}</h3>
    </div>
</#list>
</body>
</html>
