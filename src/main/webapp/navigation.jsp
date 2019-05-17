<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
}

li {
  float: left;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover {
  background-color: #111;
}
</style>
<style>
* {box-sizing: border-box;}

body { 
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.header {
  overflow: hidden;
  
  padding: 20px 10px;
}

.header a {
  float: left;
  color: black;
  text-align: center;
  padding-bottom: 12px;
  text-decoration: none;
  line-height: 21px;
  border-radius: 44px;
}

.header a.logo {
  font-size: 25px;
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


<ul>
  <li><a class="active" href="/">Home</a></li>
  <li><a href="/">logout</a></li>
  <li><a href="/taskslist">task list</a></li>
  <li style="
    /* padding-right: 0px; */
    style=&quot;float: right&quot;;
    float: right;
">  
<div class="header" style="
    padding-bottom: 0px;
    padding-top: 0px;
">
  
  <div class="header-right">
    <a id="tasksNum" class="active" href="/taskslist">Tasks 0</a>
  </div>
</div>
    </li>
</ul>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
  <script src="${contextPath}/resources/js/app.js"></script>
      
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
      
    

</body>
</html>
