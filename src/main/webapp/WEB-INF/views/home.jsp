<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html ng-app="MyApp">
<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.7/angular.min.js" type="text/javascript"></script>
	<script src="http://code.angularjs.org/1.2.7/angular-route.min.js" type="text/javascript"></script>
	<script src="http://code.angularjs.org/1.2.7/angular-animate.min.js" type="text/javascript"></script>
	<script src="http://code.angularjs.org/1.2.7/angular-sanitize.min.js" type="text/javascript"></script>   
	<script src="<c:url value='/resources/js/main.js' />"></script>
	<script src="<c:url value='/resources/js/PDFController.js' />"></script>
	<script src="<c:url value='/resources/js/OCRController.js' />"></script>
</head>
<body>
<div ng-controller="PDFController">
<div>Thread number<input ng-model="threadNumber" ng-disabled="loading"><br/><button ng-click="restart()" ng-disabled="loading">Restart</button></div>
<div>PDF COUNT : {{countpdf}}</div>
<div>PDF FAIL : {{failpdf}}</div>
<div>PDF Exist : {{existpdf}}</div>
<div>PDF Waiting : {{waitingpdf}}</div>
</div>
<hr/>
<div ng-controller="OCRController">
<div>Thread number<input ng-model="threadNumber" ng-disabled="loading"><br/><button ng-click="restart()" ng-disabled="loading">Restart</button></div>
<div>OCR COUNT : {{goodocr}}</div>
<div>OCR FAIL : {{failocr}}</div>
<div>OCR Exist : {{existocr}}</div>
</div>
</body>
</html>
 