var stompClient = null;


function connect() {
    var socket = new SockJS('/test');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/tasks', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}





function showGreeting(message) {
	console.log(message);

    $("#tasksNum").text("" + message + "");
}

function sendName() {
 
	stompClient.send("/app/hello", {}, JSON.stringify({'name': "start"}));

}


window.onload = function() {
	connect();
    setTimeout(sendName, 1000);

    setInterval(() => {
    	stompClient.send("/app/hello", {}, JSON.stringify({'name': "start"}));
		
	}, 20000);

	};
	
/*
	var stompClient = null;


	function connect() {
	    var socket = new SockJS('/test');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        stompClient.subscribe('/topic/tasks', function (greeting) {
	            showGreeting(JSON.parse(greeting.body).content);
	        });
	    
	    
	    });
	    
	    setInterval((sendName, 10000));
	}





	function showGreeting(message) {
		console.log(message);
	    $("#tasksNum").append("<tr><td>" + message + "</td></tr>");
	}

	function sendName() {
		stompClient.send("/app/hello", {}, JSON.stringify({'name': "start"}));
	}


*/