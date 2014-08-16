<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html ng-app="MyApp">
<head>
	<title>Home</title>
	<script src="<c:url value='/resources/js/angular.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>
	<script src="<c:url value='/resources/js/SoldController.js' />"></script>
</head>
<body ng-controller="SoldController">
<div>
<button ng-click="stop()" ng-disabled="!work || !isrun">STOP</button><br/>
<button ng-click="start()" ng-disabled="work || !isrun">RESUME</button><br/>
<div>
{{remain}} 
</div>
</div>
</body>
</html>
 