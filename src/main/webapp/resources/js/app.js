var stompClient = null;


function connect() {
    var socket = new SockJS('/test');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/tasks', function (refresh) {
        	showRefresh(JSON.parse(refresh.body).content);
        });
    });
}

function showRefresh(message) {
    $("#tasksNum").text("" + message + "");
}

function sendName() {
	stompClient.send("/app/hello", {}, JSON.stringify({'name': "start"}));
}

window.onload = function() {
	connect();
    setTimeout(sendName, 500);

    setInterval(() => {
    	stompClient.send("/app/hello", {}, JSON.stringify({'name': "start"}));
		
	}, 30000);

	};
	
