<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- <jsp:include page="header.jsp"/>-->
<jsp:include page="navigation.jsp"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Lunched</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
      <link href="${contextPath}/resources/images/"></link>
      
  </head>
<body>

	<h3 class="form-heading" style="height: 100%;width: 100%;display: flex;/*! position: fixed; */align-items: center;justify-content: center;">Cluster creation lunched</h3>

  <div style="width: 100%;display: flex;position: fixed;align-items: center;justify-content: center;">
  <img src="/resources/images/activeviam-logo-tagline.svg">
 	
  </div>

<div style="width: 100%;display: flex;position: fixed;align-items: center;justify-content: center;">
<img id="imgwait" src="/resources/images/done.gif" style="width: 100px;padding-top: 20%;"> 	
  </div>
  
  
        


      
      
     


		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>

  
  
  
  
</body>
</html>
