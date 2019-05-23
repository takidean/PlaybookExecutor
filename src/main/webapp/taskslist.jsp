<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- <jsp:include page="header.jsp"/>-->
<jsp:include page="navigation.jsp"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
 
 <style>
.tab {border-collapse:collapse;}
.tab .first {border-bottom:1px solid #EEE;}
.tab .second {border-top:1px solid #CCC;box-shadow: inset 0 1px 0 #CCC;}
</style>

</head>
<body>

<h3 class="form-heading" style="margin-top: 0%;margin-left: 47%">Tasks </h3>
<div  id="divContainer" class="container">
  
   
<table id="tableToReload" class="tab">
        <tr>
          <th></th>
        </tr>
        <c:forEach  items="${tasks}" var ="task">
        <tr>
          <td><a href="<c:url value="/logs/${task.id}" />"> ${task.type} by ${task.developper}  : </a>  </td>
    <td><img id="${task.id}" > </td>

        </tr>
        </c:forEach>
      </table>
</div>		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  
  
  <script>
	$(document).ready(function() {

		var tasklist = ${list};
		$.each(tasklist, function( index, value ) {
 			if(value.status==0){
				$('#'+value.id).css("src", "${pageContext.request.contextPath}/resources/images/red.png");
				
				$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/red.png");
 			}
			else if(value.status==1){
$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/blue.png");
 				}
			else{
 			
			$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/loader.gif");
			}
		});
	});

 

	
	  function ajaxGet(){
		    $.ajax({
		      type : "GET",
		      url : "taskslistrefresh",
		      success: function(result){
		    	  console.log(result);
		     //   if(result.status == "Done"){

		  		$.each(result, function( index, value ) {
		  			console.log("task status "+value.status==0)
					if(value.status==0){
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/red.png");
					}
					else if(value.status==1){
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/blue.png");
					}
					else{
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/loader.gif");
					}
				});
		          
		          console.log("Success: ", result);
		      },
		      error : function(e) {
		        console.log("ERROR:*************** ", e);
		      }
		    });  
		  }
	
	


	    setInterval(ajaxGet, 60000);
</script>
</body>
</html>
