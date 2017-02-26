window.onload = checksession;

var userid=3;

function checksession(){
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/getAllFriendsDetail/'+userid,
		type:'POST',
		dataType: 'json',
		success: function(data){
			var no_of_object = data.length;
			alert(no_of_object)
		},
		error: function(xhr,status){
		
			if(xhr.status==202){
				//No friends
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}		
		}
	});	
}