<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/common.css">
    <script>
        function setUserInfo() {
            const infoStr = "${info}".replace(/&quot;/g, '\"');
            const info = JSON.parse(infoStr);
            const pretty = JSON.stringify(info, null, 2);
            document.getElementById("userJsonData").innerText = pretty;
        }
    </script>
</head>
<body onload="setUserInfo()">
<#include "navbar.html" parse="false">
<main>
    <br> <br>
    <h1>You are welcome, ${userName}!</h1>
    <br> <br>
    <h2>Your data:</h2>
    <div id="userJsonData"></div>
</main>
</body>
</html>