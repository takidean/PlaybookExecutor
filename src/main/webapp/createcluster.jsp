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
      <title>Create your aks cluster</title>

      <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="/resources/css/common.css" rel="stylesheet">
        <link href="/resources/images/"></link>
      
  </head>
<body>

  
    <div  id="divContainer" class="container">
        <form:form method="POST" action="${contextPath}/createcluster/createcluster" modelAttribute="cluster" class="form-signin" enctype="multipart/form-data">
 	<h3 class="form-heading" style="margin-top: 0%">Create your cluster</h3>

  <div style="margin-top: 1% ;align-items: center;justify-content: center;height: 129px;">
<img src="/resources/images/activeviam-logo-tagline.svg" style="margin-top: 1% ;align-items: center;justify-content: center;height: 129px;">  </div>
              
            <spring:bind path="subscriptionId">
                <div class="form-group" style="margin-top:20px">
                    <form:input type="text" path="subscriptionId" class="form-control" placeholder="Subscription Id"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

            <spring:bind path="aksName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="aksName" class="form-control" placeholder="aksName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>
 
			<spring:bind path="vmSize">
				<div class="form-group">
					<form:select path="vmSize" class="form-control">
						<form:option value="Standard_D2s_v3">Standard_D2s_v3</form:option>
						<form:option value="Standard_DS2_v2">Standard_DS2_v2</form:option>
						<form:option value="Standard_DS1_v2">Standard_DS1_v2</form:option>
						<form:option selected="Standard_DS1_v2" value="Standard_DS1_v2">Standard_DS1_v2</form:option>
						<form:option value="Standard_D16s_v3">16vCPU-64GiB D16s_v3</form:option>
						<form:option value="Standard_B8ms">8vCPU-32GiB B8ms</form:option>
						<form:option value="Standard_B4ms">4vCPU-16GiB B4ms</form:option>
						<form:option value="Standard_B2ms">2vCPU-8GiB B2ms</form:option>
					</form:select>
				</div>
			</spring:bind>

            <spring:bind path="dbServerName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dbServerName" class="form-control" placeholder="dbServerName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

            <spring:bind path="dbName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dbName" class="form-control" placeholder="dbName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

            <spring:bind path="dbAdminUsername">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dbAdminUsername" class="form-control" placeholder="dbAdminUsername"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

            <spring:bind path="dbAdminPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dbAdminPassword" class="form-control" placeholder="dbAdminPassword"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

            <spring:bind path="keycloakUser">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="keycloakUser" class="form-control" placeholder="keycloakUser"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>


			<spring:bind path="keycloakPassword">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="keycloakPassword" class="form-control"
						placeholder="keycloakPassword" autofocus="true"></form:input>
				</div>
			</spring:bind>


            <spring:bind path="dockerUserName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dockerUserName" class="form-control" placeholder="dockerUserName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>


			<spring:bind path="dockerPassword">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="dockerPassword" class="form-control"
						placeholder="dockerPassword" autofocus="true"></form:input>
				</div>
			</spring:bind>

			<spring:bind path="dockerEmail">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="dockerEmail" class="form-control"
						placeholder="dockerEmail" autofocus="true"></form:input>
				</div>
			</spring:bind>


			<spring:bind path="domainName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="domainName" class="form-control" placeholder="domainName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>


          <!--   <spring:bind path="tag">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="tag" class="form-control" placeholder="tag"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>
 -->
			<spring:bind path="tag">
				<div class="form-group">
					<form:select path="tag" class="form-control">
						<form:option value="UAT">UAT</form:option>
						<form:option value="Production">Production</form:option>
						<form:option selected="Dev" value="Dev">Dev</form:option>
					</form:select>
				</div>
			</spring:bind>
			<div class="form-group">
				key:<input type="file" name="key" class="form-control" />
			</div>
			<div class="form-group">
				ssl_certificate:<input type="file" name=" ssl_certificate"
					class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<button id="btnValidate"  class="btn btn-lg btn-primary btn-block" style="margin-top:20px">Submit</button>
   <!--     <button class="btn btn-lg btn-primary btn-block" style="margin-top:20px"  id="btnDisable">Valider</button>  -->
        </form:form>

    </div>
		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="/resources/js/bootstrap.min.js"></script>
  
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
