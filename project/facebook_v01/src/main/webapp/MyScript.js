//For Signup
function sendData() {
	var checkedValue = $('input[name=signup_gender]').filter(':checked').val();
    $.ajax({
        url: 'http://localhost:8080/facebook_v01/webapi/signup',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            userDetails_userName:"",
            userDetails_firstName:$('#firstName').val(),
            userDetails_lastName:$('#lastName').val(),
            userDetails_email:$('#email_mobile').val(),
            userDetails_password: $('#password').val(),
            userDetails_day: $('#day').val(),
            userDetails_month: $('#month').val(),
            userDetails_year: $('#year').val(),
            userDetails_gender: checkedValue
        }),
        dataType: 'text',
        
        success : function(data){
        	alert("Signup successful");
//        	window.location.href = "";
        },
        error : function(xhr,status){
//        	if(xhr.responseText == 'Invalid')
    			alert("EmailID or Mobile already registered");
 //   		else
 //   			alert("Something went wrong");
        }
    });
}
//For Login
function loginData() {
	var msg=JSON.stringify({     
    	login_Id: $('#login_Id').val(),
        login_Password: $('#login_Password').val()
    });
    $.ajax({
        url: 'LoginServlet',
        type: 'POST',
        data : {msg:msg},
        dataType: 'json',
        success : function(data) {
//        	alert(data);
    		console.log(data);
    		var dob = data['userDetails_day'] +"-"+ data['userDetails_month'] +"-"+ data['userDetails_year'];
    		
//    		$("#username").val(data.userDetails_userName);
//    		window.location.href = "GetDetailedInfo.html";
    		
    		window.location.href = "GetDetailedInfo.html"+"?unm="+data.userDetails_userName+"&dob="+dob+"&fname="+data.userDetails_firstName;
            },
    	error : function(xhr, status){
    		alert("Invalid Username or Password");
//    		if(xhr.responseText == 'Invalid')
//    			alert("Invalid Username or password");
//    		else
//    			alert("Something went wrong");
    	}
    });
}