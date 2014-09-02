<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register Page</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/resources/css/signin.css" />" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<h1>Office Library</h1>

<div class="container">

    <h2 class="form-signin-heading" style="text-align: center;">Register</h2>

    <form:form commandName="user" class="form-signin" role="form" method="POST"
               onsubmit="return validateRegisterForm();">
        <form:errors path="*" cssClass="bg-info form-signin-msg" style="display:block;"/>

        <div class="login-inputs">

            <div class="input-group input-group-lg">
                <span class="input-group-addon">@</span>
                <form:input path="username" class="form-control" id="usernameinput"/>
            </div>

            <div class="input-group input-group-lg">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <form:password path="password" class="form-control" id="userpasswordinput" name="pwd1"/>
            </div>

            <div class="input-group input-group-lg">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <form:password path="repeatedPassword" class="form-control" id="userpasswordinput2" name="pwd2"/>
            </div>

        </div>

        <div class="row">&nbsp;</div>

        <input name="submit" class="btn btn-lg btn-primary btn-block" type="submit" value="Register"/>
    </form:form>
    <div class="panel-footer">
        Back to <a href="<c:url value="/user/login" />">Login page</a>
    </div>
</div>

</body>

<script>
    $("#usernameinput").attr('required', '');
    $("#usernameinput").attr('autofocus', '');
    $("#usernameinput").attr('placeholder', 'email');

    $("#userpasswordinput").attr('required', '');
    $("#userpasswordinput").attr('placeholder', 'password');

    $("#userpasswordinput2").attr('required', '');
    $("#userpasswordinput2").attr('placeholder', 'repeat password');


    function validateRegisterForm() {

        var pwd1 = $("#userpasswordinput").val();
        var pwd2 = $("#userpasswordinput2").val();

        if (pwd1 != pwd2) {
            alert("Passwords are diffrent!");
            return false;
        }

        return true;
    }
</script>
</html>



