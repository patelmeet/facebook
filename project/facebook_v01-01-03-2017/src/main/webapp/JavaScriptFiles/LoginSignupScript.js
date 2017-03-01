window.onload = checkSession()

function checkSession()
{	
	$.ajax({
        url: 'CheckSessionServlet',
        type: 'POST',
        dataType: 'json',
    	complete : function(request, textStatus, errorThrown){
//    		alert(request.getResponseHeader('mystatus'))
//    		alert(request.responseText)
    		if(request.getResponseHeader('mystatus') == 205)
    			window.location = "ViewPersonalInfo.html"
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
        
        success : function(data){
        	alert("Signup successful");
        	window.location = "ViewPersonalInfo.html"
        },
        error : function(xhr,status){
        	if(xhr.status == 204)
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
    $.ajax({
        url: 'LoginServlet',
        type: 'POST',
        data : {msg:msg},
        dataType: 'json',
    	complete : function(request, textStatus, errorThrown){
//    		alert(request.getResponseHeader('mystatus'))
//    		alert(request.responseText)
    		if(request.getResponseHeader('mystatus') == 205)
    			window.location = "ViewPersonalInfo.html"
    		else if(request.getResponseHeader('mystatus') == 206)
    			alert("Invalid Username or Password")
    		else
    			alert("Something went wrong")
    	}
    });
}
