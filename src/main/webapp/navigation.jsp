<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {box-sizing: border-box;}

body { 
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.header {
  overflow: hidden;
  }

.header a {
  float: left;
  color: black;
  text-align: center;
  padding: 12px;
  text-decoration: none;
  font-size: 10px; 
  line-height: 15px;
  border-radius: 4px;
}

.header a.logo {
  font-size:15px;
  font-weight: bold;
}

.header a:hover {
  background-color: #ddd;
  color: black;
}

.header a.active {
  background-color: dodgerblue;
  color: white;
}

.header-right {
  float: right;
}

@media screen and (max-width: 500px) {
  .header a {
    float: none;
    display: block;
    text-align: left;
  }
  
  .header-right {
    float: none;
  }
}
</style>


</head>

<body>
<div class="header">
  <div class="header-right">
    <a id="home" class="active" href="/">home</a>
  </div>
  
    <div class="header-right">
    <a id="home" class="active" href="/">logout</a>
  </div>
  
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
  <script src="${contextPath}/resources/js/app.js"></script>
      
    

</body>
</html>
