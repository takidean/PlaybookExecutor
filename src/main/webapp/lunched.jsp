<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="header.jsp"/>

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

	<h3 class="form-heading" style="margin-top: 0%;margin-left: 46%">Create your cluster</h3>

  <div style="margin-top: 1%;margin-left: 41%;">
  <img src="${pageContext.request.contextPath}/resources/images/activeviam-logo-tagline.svg">
  </div>
  
    <div  id="divContainer" class="container">
        <div class="form-signin">
        
      	<h2 id="waitmessage"
		style="display: none ; margin-top:20px ; position: absolute; left: 50%; padding: 25px; -ms-transform: translateX(-50%) translateY(-50%); -webkit-transform: translate(-50%, -50%); transform: translate(-50%, -50%)">Cluster creation lunched</h2>

	<img id="imgwait"
		src="${pageContext.request.contextPath}/resources/images/done.gif"
		style=" display: none;  position: absolute;  bottom: 0; left: 0; right: 0; margin: auto; width: 100px;" />
      
      
        </div>


    </div>
		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>

  
  
  
  
</body>
</html>
