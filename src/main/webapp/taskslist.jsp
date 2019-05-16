<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
        
      
  </head>
<body>

<h3 class="form-heading" style="margin-top: 0%;margin-left: 47%">Tasks </h3>
<div  id="divContainer" class="container">
  
   
<table id="tableToReload" border="1">
        <tr>
          <th>Tasks</th>
        </tr>
        <c:forEach  items="${tasks}" var ="task">
        <tr>
          <td>${task.type} by ${task.developper}</td>
<!--     <td><img id="${task.id}_blue" src="${pageContext.request.contextPath}/resources/images/blue.png" style="visibility: hidden;"> <img style="visibility: hidden;" id="${task.id}_yellow" src="${pageContext.request.contextPath}/resources/images/yellow.png"> <img style="visibility: hidden;" id="${task.id}_load" src="${pageContext.request.contextPath}/resources/images/loader.gif"></td> -->
    <td><img id="${task.id}" > </td>

        </tr>
        </c:forEach>
      </table>
</div>		 	


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  
  
  <script>
/* 	$(document).ready(function() {

		var tasklist = ${list};
		$.each(tasklist, function( index, value ) {
			console.log(value.status+" ****")
			if(value.status==0){
				console.log(value.status+" 0**")
				$('#'+value.id+'_yellow').css("visibility", "visible");}
			else if(value.status==1){
				console.log(value.status+" 1***")

				$('#'+value.id+'_blue').css("visibility", "visible");}
			else{$('#'+value.id+'_load').css("visibility", "visible");}
		});
	});
 */
	$(document).ready(function() {

		var tasklist = ${list};
		$.each(tasklist, function( index, value ) {
 			if(value.status==0){
				$('#'+value.id).css("src", "${pageContext.request.contextPath}/resources/images/yellow.png");
				
				$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/yellow.png");
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
					if(value.status==0){
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/yellow.png");
					}
					else if(value.status==1){
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/blue.png");
					}
					else{
						$('#'+value.id).attr('src', "${pageContext.request.contextPath}/resources/images/loader.gif");
					}
				});
		          
		          console.log("Success: ", result);
/* 		        }else{
		          console.log("Fail: ", result);
		        }
 */		      },
		      error : function(e) {
		        console.log("ERROR:*************** ", e);
		      }
		    });  
		  }
	
	


	    setInterval(ajaxGet, 10000);
</script>
  <!-- 
         <script type="text/javascript">
 
            var auto_refresh = setInterval(
            function ()
            {
                $('#tableToReload').load('/taskslist');
            }, 30000); 
        </script>
  

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
