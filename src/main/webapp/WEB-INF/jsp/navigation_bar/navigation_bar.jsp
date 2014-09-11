<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/resources/images/banner-graphic.png" var="banner" />

<html>
<head>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

</head>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<span class="navbar-brand">Masz te robote</span>
	<div class="navbar-collapse collapse" id="navbar-main">
		<ul class="nav navbar-nav">
			<li><a href="<spring:url value="/" htmlEscape="true" />"><i
					class="glyphicon glyphicon-home"></i> Start</a></li>
			<li><a
				href="<spring:url value="/questions/allQuestions" htmlEscape="true" />"><i
					class="glyphicon glyphicon-list-alt"></i> Lista pytań</a></li>
			<li><a
				href="<spring:url value="/company/allCompanies" htmlEscape="true" />"><i
					class="glyphicon glyphicon-th-list"></i> Lista firm</a></li>
			<li><a
				href="<spring:url value="/answers/allAnswers" htmlEscape="true" />"><i
					class="glyphicon glyphicon-comment"></i> Ostatnie odpowiedzi</a></li>
			<sec:authorize access="isAuthenticated()">
				<li><a
					href="<spring:url value="/questions/add" htmlEscape="true" />"><i
						class="glyphicon glyphicon-edit"></i> Dodaj pytanie</a></li>
				<li><a
					href="<spring:url value="/user/myProfile" htmlEscape="true" />"><i
						class="glyphicon glyphicon-user"></i> Moje konto</a></li>
				<li><a
					href="<spring:url value="/user/messages" htmlEscape="true" />"><span
						class="glyphicon glyphicon-envelope" id="msgCount"></span> <span
						class="badge">14</span></a></li>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<div class="welcome-message pull-right">
					<b>${pageContext.request.userPrincipal.name}</b> | <a
						href="<spring:url value="/admin/showSettings"/>"><span
						class="glyphicon glyphicon-cog"></span> Ustawienia</a> | <a
						href="<c:url value="/j_spring_security_logout"/>"> <span
						class="glyphicon glyphicon-log-out"></span> Wyloguj
					</a>
				</div>

			</sec:authorize>

			<sec:authorize access="isAnonymous()">
				<li></li>
		</ul>

		<div>
			<button class="btn btn-sm btn-warning"
				style="position: absolute; left: 1350px; top: 10px;"
				data-toggle="modal" data-target=".slacker-modal">Logowanie
				/ rejestracja</button>
		</div>
		</sec:authorize>
	</div>
</div>

<a id="back-to-top" href="#" class="btn btn-warning btn-lg back-to-top"
	role="button" data-toggle="tooltip" data-placement="left"><span
	class="glyphicon glyphicon-chevron-up"></span></a>

<!-- login modal -->
<div class="modal fade in slacker-modal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="false">
	<div class="modal-dialog modal-slacker">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">x</button>

				<br>
				<div class="bs-example bs-example-tabs">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#signin" data-toggle="tab"><h3
									class="modal-title">Zaloguj się</h3></a></li>
						<li class=""><a href="#signup" data-toggle="tab"><h3
									class="modal-title">Zarejestruj się</h3></a></li>
					</ul>
				</div>
				<div class="modal-body">
					<div id="myTabContent" class="tab-content">

						<div class="tab-pane fade active in" id="signin">
							<form id="loginForm" class="form-horizontal" method="POST"
								action="<c:url value='/j_spring_security_check' />">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="userid">Nazwa
											użytkownika:</label>
										<div class="controls">
											<input id="userid" name="username" type="text"
												class="form-control" placeholder="" class="input-medium"
												required>
										</div>
									</div>

									<!-- Password input-->
									<div class="control-group">
										<label class="control-label" for="passwordinput">Hasło:</label>
										<div class="controls">
											<input required id="passwordinput" name="password"
												class="form-control" type="password" placeholder=""
												class="input-medium">
										</div>
									</div>

									<!-- Multiple Checkboxes (inline)
            <div class="control-group">
              <label class="control-label" for="rememberme"></label>
              <div class="controls">
                <label class="checkbox inline" for="rememberme-0">
                  <input type="checkbox" name="rememberme" id="rememberme-0" value="Remember me">
                  Zapamiętaj mnie
                </label>
              </div>
            </div>
-->
									<!-- Button -->
									<div class="control-group">
										<label class="control-label" for="signin"></label>
										<div class="controls">

											<button type="submit" id="signin" name="redirect"
												class="btn btn-warning">Zaloguj</button>

											<br> <br> Lub: <br> <br> <a
												class="btn btn-block btn-social btn-facebook"> <i
												class="fa fa-facebook"></i> Zaloguj się kontem Facebooka
											</a> <a class="btn btn-block btn-social btn-google-plus"> <i
												class="fa fa-google-plus"></i> Zaloguj się kontem Google
											</a>

										</div>
									</div>
								</fieldset>
							</form>
						</div>

						<div class="tab-pane fade" id="signup">
							<form class="form-horizontal">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="Email">Email:</label>
										<div class="controls">
											<input id="Email" name="Email" class="form-control"
												type="text" placeholder="" class="input-large" required>
										</div>
									</div>

									<!-- Text input-->
									<div class="control-group">
										<label class="control-label" for="userid">Nazwa
											użytkownika:</label>
										<div class="controls">
											<input id="userid" name="userid" class="form-control"
												type="text" placeholder="" class="input-large" required>
										</div>
									</div>

									<!-- Password input-->
									<div class="control-group">
										<label class="control-label" for="password">Hasło:</label>
										<div class="controls">
											<input id="password" name="password" class="form-control"
												type="password" placeholder="" class="input-large" required>
										</div>
									</div>

									<!-- Text input-->
									<div class="control-group">
										<label class="control-label" for="reenterpassword">Powtórz
											Hasło:</label>
										<div class="controls">
											<input id="reenterpassword" class="form-control"
												name="reenterpassword" type="password" placeholder=""
												class="input-large" required>
										</div>
									</div>

									<!-- Button -->
									<div class="control-group">
										<label class="control-label" for="confirmsignup"></label>
										<div class="controls">
											<button id="confirmsignup" name="confirmsignup"
												class="btn btn-warning">Zarejestruj</button>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
</html>
<script>

$(document).ready(function(){
    $(window).scroll(function () {
           if ($(this).scrollTop() > 50) {
               $('#back-to-top').fadeIn();
           } else {
               $('#back-to-top').fadeOut();
           }
       });
       $('#back-to-top').click(function () {
           $('#back-to-top').tooltip('hide');
           $('body,html').animate({
               scrollTop: 0
           }, 800);
           return false;
       });
       

});

$('#loginForm input[type=text]').on('change invalid', function() {
    var textfield = $(this).get(0);
    
    textfield.setCustomValidity('');
    
    if (!textfield.validity.valid) {
      textfield.setCustomValidity('Proszę wypełnić to pole');  
    }
});

	//$(document).ready(poll);

	//setInterval(poll, 100000);

	function poll() {
		$.ajax({
			url : 'http://10.30.0.57:8080/interview/user/pollMsg'

		}).done(
				function(data) {
					count = data;
					document.getElementById("msgCount").innerHTML = "\xa0"
							+ data;

					if (data > 0) {
						var newStyle = document.getElementById("msgCount")
								.getAttribute("class").concat(" red");
						document.getElementById("msgCount").setAttribute(
								"class", newStyle);

					} else {
						var newStyle = document.getElementById("msgCount")
								.getAttribute("class").replace(" red", "");
						document.getElementById("msgCount").setAttribute(
								"class", newStyle);

					}
				});
	}
</script>

