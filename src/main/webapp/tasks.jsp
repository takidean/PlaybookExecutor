<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="header.jsp"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- https://spring.io/guides/gs/messaging-stomp-websocket/ -->
<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Tasks</title>

  <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
  <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
  <link href="${contextPath}/resources/images/"></link>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
        
      
  </head>
<body>

	<h3 class="form-heading" style="margin-top: 0%;margin-left: 47%">steps</h3>
   <div  id="divContainer" class="container">
  
    <div class="form-signin" style="max-width: 35%;">
  <div class="dropdown" style="margin-bottom: 1%;"> 
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Step 1: Resource group creation
    <span class="caret"></span></button>
    <img src="${pageContext.request.contextPath}/resources/images/blue.png"> <img src="${pageContext.request.contextPath}/resources/images/yellow.png"> <img src="${pageContext.request.contextPath}/resources/images/loader.gif">
    <ul class="dropdown-menu">
    <li>
 <textarea rows="10" cols="100">HERE loooogs</textarea>
  </li>
  </ul>
  </div>

  <div class="dropdown" style="margin-bottom: 1%;">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" style="padding-right: 12%;">Step 2: Aks cluster creation
    <span class="caret"></span></button>
    <img src="${pageContext.request.contextPath}/resources/images/blue.png"> <img src="${pageContext.request.contextPath}/resources/images/yellow.png"> <img src="${pageContext.request.contextPath}/resources/images/loader.gif"> 
    <ul class="dropdown-menu">
    <li>
 <textarea rows="10" cols="100">HERE loooogs</textarea>
  </li>
  </ul>
  </div>

  <div class="dropdown" style="margin-bottom: 1%;">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Step 3: Standard cluster creation
    <span class="caret"></span></button>
    <img src="${pageContext.request.contextPath}/resources/images/blue.png"> <img src="${pageContext.request.contextPath}/resources/images/yellow.png"> <img src="${pageContext.request.contextPath}/resources/images/loader.gif">
 
    <ul class="dropdown-menu">
    <li>
 <textarea rows="10" cols="100">HERE loooogs</textarea>
  </li>
  </ul>
  </div>
    </div>
</div>		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  
 <!-- 
        <script language="javascript">
        $(document).ready(function () {
            $("#btnDisable").on("click", function () {
                $("#divContainer").find("input, button, submit, textarea, select").attr("disabled", "disabled");
                
                $("#btnValidate").removeAttr("disabled");  
                $('#btnValidate').show();
                $("#btnDisable").hide();
                
            });
            $("#btnEnable").on("click", function () {
                $("#divContainer").find("input, button, submit, textarea, select").removeAttr("disabled");
                $("#divContainer").find("a").removeClass("disablehyper").unbind("click");
            });
        });
    </script>
   --> 
</body>
</html>
