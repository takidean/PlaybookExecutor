<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="header.jsp"/>

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
        <form:form method="POST" action="${contextPath}/validation" modelAttribute="cluster" class="form-signin">
        
                <div class="form-group" style="margin-top:20px">
                    <p  class="form-control" >subscriptionId: ${cluster.subscriptionId}</p>
                </div>

                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >aksName: ${cluster.aksName}</p>
                </div>



                <div class="form-group ${status.error ? 'has-error' : ''}">
                <p  class="form-control" >Vm size: ${cluster.vmSize}</p>
                </div>


                <div class="form-group ${status.error ? 'has-error' : ''}">
				<p  class="form-control" >Tag: ${cluster.tag}</p>                </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<button name="button_1" id="btnValidate"  class="btn btn-lg btn-primary btn-block" style="margin-top:20px" type="submit">Create</button>
            <button name="button_2" class="btn btn-lg btn-primary btn-block" style="margin-top:20px"  id="btnDisable" formaction="${contextPath}/" type="submit">Revert</button> 
      
      	<h2 id="waitmessage"
		style="display: none ; margin-top:20px ; position: absolute; left: 50%; padding: 25px; -ms-transform: translateX(-50%) translateY(-50%); -webkit-transform: translate(-50%, -50%); transform: translate(-50%, -50%)">Creating
		cluster, please wait</h2>

	<img id="imgwait"
		src="${pageContext.request.contextPath}/resources/images/transparent-google-loader-gif-4.gif"
		style=" display: none;  position: absolute;  bottom: 0; left: 0; right: 0; margin: auto; width: 100px;" />
      
      
        </form:form>


    </div>
		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$('#btnValidate').click(function() {
		
        $("#btnDisable").hide();
        $("#btnValidate").hide();

        $("#imgwait").show();
        $("#waitmessage").show();

		$.ajax({
			type: form.attr('method'),
			url: form.attr('action'),
			data: form.serialize(),

			success: function(data) {
                var msg = data.message;
                if(msg === "fail") {
                    alert("Failure to connect");
                }
                else {
                    $.get('result.jsp');
                }
            }	
			});
	});
});


</script>
  
    
<!--         <script language="javascript">
        $(document).ready(function () {
            $("#btnValidate").on("click", function () {
                $("#divContainer").find("input, button, submit, textarea, select").attr("disabled", "disabled");
                
                $("#btnDisable").hide();
                $("#btnValidate").hide();

                $("#imgwait").show();
                $("#waitmessage").show();

                
            });
        });
    </script> -->
  
  
  
  
</body>
</html>
