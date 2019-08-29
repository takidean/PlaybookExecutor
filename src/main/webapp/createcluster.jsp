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
        <form:form method="POST" action="${contextPath}/createcluster/createcluster" modelAttribute="cluster" class="form-signin" >
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
						<form:option value="Standard_B2ms">Standard_B2ms</form:option>
						<form:option value="Standard_B4ms">Standard_B4ms</form:option>
						<form:option value="Standard_B8ms">Standard_B8ms</form:option>
						<form:option value="Standard_B2ms">Standard_B16ms</form:option>
						<form:option value="Standard_B2ms">Standard_B12ms</form:option>
						<form:option value="Standard_B2ms">Standard_B20ms</form:option>
						<form:option value="Standard_D16s_v3">Standard_D16s_v3</form:option>
						<form:option value="Standard_D48s_v3">Standard_D48s_v3</form:option>
						<form:option value="Standard_D64s_v3">Standard_D64s_v3</form:option>
						<form:option value="Standard_D8as_v3">Standard_D8as_v3</form:option>
						<form:option value="Standard_D16as_v3">Standard_D16as_v3</form:option>
						<form:option value="Standard_D32as_v3">Standard_D32as_v3</form:option>
						<form:option value="Standard_D48as_v3">Standard_D48as_v3</form:option>
						<form:option value="Standard_D64as_v3">Standard_D64as_v3</form:option>
						<form:option value="Standard_D8_v3">Standard_D8_v3</form:option>
						<form:option value="Standard_D16_v3">Standard_D16_v3</form:option>
						<form:option value="Standard_D32_v3">Standard_D32_v3</form:option>
						<form:option value="Standard_D48_v3">Standard_D48_v3</form:option>
						<form:option value="Standard_D64_v3">Standard_D64_v3</form:option>
						<form:option value="Standard_D8a_v3">Standard_D8a_v3</form:option>
						<form:option value="Standard_D16a_v3">Standard_D16a_v3</form:option>
						<form:option value="Standard_D32a_v3">Standard_D32a_v3</form:option>
						<form:option value="Standard_D48a_v3">Standard_D48a_v3</form:option>
						<form:option value="Standard_D64a_v3">Standard_D64a_v3</form:option>
						<form:option value="Standard_DS4_v2">Standard_DS4_v2</form:option>
						<form:option value="Standard_DS5_v2">Standard_DS5_v2</form:option>
						<form:option value="Standard_D3_v2">Standard_D3_v2</form:option>
						<form:option value="Standard_D4_v2">Standard_D4_v2</form:option>
						<form:option value="Standard_D5_v2">Standard_D5_v2</form:option>
						<form:option value="Standard_A4m_v2">Standard_A4m_v2</form:option>
						<form:option value="Standard_A8m_v2">Standard_A8m_v2</form:option>
					</form:select>
				</div>
			</spring:bind>
  
            <spring:bind path="dbAdminPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="dbAdminPassword" class="form-control" placeholder="dbAdminPassword"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

			<spring:bind path="keycloakPassword">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="keycloakPassword" class="form-control"
						placeholder="keycloakPassword" autofocus="true"></form:input>
				</div>
			</spring:bind>

			<spring:bind path="dockerPassword">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="dockerPassword" class="form-control"
						placeholder="dockerPassword" autofocus="true"></form:input>
				</div>
			</spring:bind>

			<spring:bind path="domainName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="domainName" class="form-control" placeholder="domainName"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>

			<spring:bind path="location">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="location" class="form-control" placeholder="location"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>


            <spring:bind path="githubRepository">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="githubRepository" class="form-control" placeholder="githubRepository"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>
            
            <spring:bind path="githubBranch">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="githubBranch" class="form-control" placeholder="githubBranch"
                                autofocus="true"></form:input>
                </div>
            </spring:bind>
            
			<spring:bind path="tag">
				<div class="form-group">
					<form:select path="tag" class="form-control">
						<form:option value="UAT">UAT</form:option>
						<form:option value="Production">Production</form:option>
						<form:option selected="Dev" value="Dev">Dev</form:option>
					</form:select>
				</div>
			</spring:bind>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<button id="btnValidate"  class="btn btn-lg btn-primary btn-block" style="margin-top:20px">Submit</button>
   <!--     <button class="btn btn-lg btn-primary btn-block" style="margin-top:20px"  id="btnDisable">Valider</button>  -->
        </form:form>

    </div>
		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="/resources/js/bootstrap.min.js"></script>
  
</body>
</html>
