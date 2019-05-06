<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Valider pour créer</title>

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
        <form:form method="POST" action="${contextPath}/validateCreation" modelAttribute="cluster" class="form-signin">
        
                <div class="form-group" style="margin-top:20px">
                    <p  class="form-control" >groupName: ${cluster.groupName}</p>
                </div>

                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >aksName: ${cluster.aksName}</p>
                </div>

                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >User name: ${cluster.userName}</p>
                </div>

                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >Vm count: ${cluster.vmCount}</p>
                </div>

                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >Vm size: ${cluster.vmSize}</p>
                </div>


                <div class="form-group ${status.error ? 'has-error' : ''}">
				<p  class="form-control" >Tag: ${cluster.tag}</p>                </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<button name="button_1" id="btnValidate"  class="btn btn-lg btn-primary btn-block" style="margin-top:20px" formaction="/createcluster" type="submit">Submit</button>
            <button name="button_2" class="btn btn-lg btn-primary btn-block" style="margin-top:20px"  id="btnDisable" formaction="${contextPath}/" type="submit">Revert</button> 
        </form:form>

    </div>
		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  
</body>
</html>
