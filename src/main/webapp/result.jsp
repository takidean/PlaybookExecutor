<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!-- <jsp:include page="header.jsp"/>-->
<jsp:include page="navigation.jsp"/>

<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<title>Processing</title>
      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
	<h2>Cluster creation logs</h2>

	${logs}

</body>
</html>
