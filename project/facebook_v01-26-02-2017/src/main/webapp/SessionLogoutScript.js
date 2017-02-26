window.onload = checksession;

function checksession(){
	$.ajax({
		url:'CheckSessionServlet',
		type:'POST',
		success: function(data,xhr,ststus){
			alert(xhr.status)
//			if(data == 'AlreadyLogedIn')
//				window.location.href='showpic.html';	//Already Loged in -> redirect to main page
		},
	});
}

function logout()
{
	
}