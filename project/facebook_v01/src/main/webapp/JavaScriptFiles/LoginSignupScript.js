window.onload = checkSession()

$(document).ready(function(){
    $('[data-toggle="popover"]').popover();
});

function checkSession()
{	
	$.ajax({
        url: 'CheckSessionServlet',
        type: 'POST',
        async:false,
        dataType: 'json',
    	complete : function(request, textStatus, errorThrown){
//    		alert(request.getResponseHeader('mystatus'))
//    		alert(request.responseText)
    		if(request.getResponseHeader('mystatus') == 205)
    			window.location = "HomePage.html"
    		else if(request.getResponseHeader('mystatus') == 206){}
    		else{
    			window.location = "MiscellaneousPages/ErrorPage.html"
			}
    	}
    });
}

//For Signup
function signupFunction() {
	var checkedValue = $('input[name=signup_gender]').filter(':checked').val();
    $.ajax({
        url: 'SignupServlet',
        type: 'POST',
        async:false,
        data: {msg:JSON.stringify({
            userDetails_userName:"",
            userDetails_firstName:$('#firstName').val(),
            userDetails_lastName:$('#lastName').val(),
            userDetails_email:$('#email_mobile').val(),
            userDetails_password: $('#password').val(),
            userDetails_day: $('#day').val(),
            userDetails_month: $('#month').val(),
            userDetails_year: $('#year').val(),
            userDetails_gender: checkedValue
        })},
        dataType: 'text',
        complete : function(request, textStatus, errorThrown){
//        	alert(request.getResponseHeader('mystatus'))
        	if(request.getResponseHeader('mystatus') == 201)
        		alert("Signup successful");
        	else if(request.getResponseHeader('mystatus') == 205)
        		alert("EmailID or Mobile already registered");
        	else
        		alert("Something went wrong");
        }
    });
}
//For Login
function loginFunction() {
	var msg=JSON.stringify({     
    	login_Id: $('#login_Id').val(),
        login_Password: $('#login_Password').val()
    });
//	alert("Login");
	/*
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'LoginServlet', true);
    xhr.send(msg);
    xhr.onreadystatechange = function() {
    	alert(xhr.getResponseHeader('mystatus'));
        if (xhr.readyState == 4) {
            var data = xhr.responseText;
            alert(data);
        }
    }
	*/
	
    $.ajax({
        url: 'LoginServlet',
        type: 'POST',
        async : false,
        data : {msg:msg},
        dataType: 'json',
        
    	complete : function(request, textStatus, errorThrown){
    		
//    		alert(request.getResponseHeader('mystatus'))
//    		alert(request.responseText)
    		if(request.getResponseHeader('mystatus') == 205)
    			window.location = "HomePage.html";
    		else if(request.getResponseHeader('mystatus') == 206)
    			alert("Invalid Username or Password");
    		else
    			alert("Something went wrong");
    	}
    });
}

//Function Added For Users List Button.
function usersList(){
    //alert("Users List!");
    var ele = document.getElementById("usersList");
    var attr = ele.getAttribute("data-content");
    var dataContent = "<p><strong>UserName &nbsp;FirstName&nbsp;&nbsp; Password</strong></p>";   
    var userName;
    var password;
    var firstName;
    $.ajax({
            url:'http://localhost:8080/facebook_v01/webapi/getAllUsers',
            type:'GET',
            dataType: 'json',
            async: false,
            success: function(data){
                var no_of_object = data.length;
                for( var i=0 ; i < no_of_object ;i++){
                    userName = data[i]['userName'];
                    password = data[i]['password'];
                    firstName = data[i]['firstName'];
                    dataContent += '<p>&nbsp;&nbsp;&nbsp;&nbsp;' + userName +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+firstName+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ password + '</p>';
                    }
                }
            });
    ele.setAttribute("data-content", dataContent);
}
