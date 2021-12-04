<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Questions List</title>
    <link rel="stylesheet" href="/question-card.css">
</head>
<body>
<#include "navbar.html" parse="false">
<main>
    <h1>Questions</h1>
    <#list questions as question>
        <div class="question-card">
            <div class="question-title">${question.title}</div>
            <div class="question-content">${question.content}</div>
            <div class="question-author">By <#if question.author?has_content>${question.author}<#else>unknown</#if></div>
        </div>
    </#list>
</main>
</body>
</html>