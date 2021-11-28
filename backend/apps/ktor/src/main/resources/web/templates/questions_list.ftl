<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Questions List</title>
</head>
<body>
<h1>Questions</h1>
<#list questions as question>
    <div>
        <h3>${question.title}</h3>
    </div>
</#list>
</body>
</html>