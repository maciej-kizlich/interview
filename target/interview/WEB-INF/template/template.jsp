<%@ include file="/WEB-INF/template/includes.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html/>
<html>
<head>
    <title><tiles:insertAttribute name="title"/></title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <meta charset="utf-8">
    <link href="<c:url value="/resources/css/bootstrap-multiselect.css" />" rel="stylesheet">
    <script type="text/javascript" src="<c:url value="/resources/js/lib/bootstrap-multiselect.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrapValidator.min.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/lib/bootstrapValidator.min.js" />"></script>

    <link href="<c:url value="/resources/css/star-rating.css" />" media="all" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/resources/js/lib/star-rating.js" />" type="text/javascript"></script>
</head>


<body>

<div class="container">

    <div id="navigation-bar">
        <tiles:insertAttribute name="navigation_bar"/>
    </div>

    <div id="banner">
        <tiles:insertAttribute name="header"/>
    </div>

    <div id="page">
        <tiles:insertAttribute name="body"/>
    </div>

	  

</div>
<div id="footer">
    <tiles:insertAttribute name="footer"/>
</div>  

</body>
</html>