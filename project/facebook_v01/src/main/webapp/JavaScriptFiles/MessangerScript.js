/***** This File is for Messenger ******/


var userid;
var loggedin_user_profile_pic_full_url;
var receiverId;
var rname;
function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	userid = document.getElementById("topNavHiddenPK").value;
//	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
//	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;	//Set Profile Pic
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	
//	fetchPosts();
	setInterval("displayChat(receiverId,rname)",10000);
	fetchFriendListMessanger();
}


function fetchFriendListMessanger()
{
	userid = document.getElementById("topNavHiddenPK").value;
	$('#friend_list_in_messanger').empty();
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/getAllFriendsDetail/'+userid,
		type:'POST',
		dataType: 'json',
		success: function(data)
		{
			var no_of_object = parseInt(data.length);
			for( var i=0 ; i < no_of_object ;i++){
				var name = data[i]['friend_name'];
				var total_friends = data[i]['total_friends'];
				var friend_pk = data[i]['friend_pk'];
				var picfullurl = serverPicLocation + data[i]['friend_picture'];
				var popoverVariable;
				popoverVariable = '<a style="color:#3b5998" href="#" onclick="displayChat('+friend_pk+',\''+name+'\')" data-html="true" class="x" data-toggle="popover"  data-trigger="hover" data-placement="bottom" data-content=\'<div class="media"><a href="#" class="pull-left"><img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object"></a><div class="media-body"><h4 class="media-heading"><strong>'+name+'</strong></h4><p style="color:grey">'+total_friends+' Friends </p></div></div>\'>'+name+'</a>';
				
				var element = '<br/><div class="sidebar-name">'+ '<span class="pull-left" style="margin-right:12px; margin-top:-2px;">'+
        		'<img src="'+picfullurl+'"  title="'+name+'" alt="John Doe" width="40px" height="30px">'+
                '</span>'+popoverVariable+'<div/>';
				
				$('#friend_list_in_messanger').append(element);
				
				$('[data-toggle="popover"]').popover();
				//alert("show data");
			}
			
		},
		error: function(xhr,status){
			
			if(xhr.status==202){
				$('#friendlist').append('');
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}		
		}
		
	});
}



// To Display Friend's Chat.
function displayChat(friend_pk,name){
	if(friend_pk){
		userid = document.getElementById("topNavHiddenPK").value;
		receiverId = friend_pk;
		rname = name;
		$('#receivername').empty();
		$('#messageData').empty();

		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/messages/fetchMessage/'+userid+'/'+friend_pk,
			type:'POST',
			dataType: 'json',
			success: function(data)
			{
				$("#receivername").append(rname);
				var no_of_object = parseInt(data.length);
				for( var i=0 ; i < no_of_object ;i++){
					var sender = data[i]['messages_sender_id'];
					var receiver = data[i]['messages_receiver_id'];
					var message = data[i]['messages_message'];
					var element = '';
					
					if(sender==userid){
						element = element + '<div class="pull-right" style="margin-right:10px; margin-left:30px; margin-top:10px;  background-color:#add8e6;"><b>'+message+'</b></div><br></br>';
					}
					else{
						element = element + '<div class="pull-left" style="margin-right:20px; margin-left:10px; margin-top:10px;  background-color:#cccbdc; "><b>'+message+'</b></div><br></br>';
					}
					
					$('#messageData').append(element);
				}
				$("#messageData").scrollTop($("#messageData")[0].scrollHeight);
			},
			error: function(xhr,status){
				
				if(xhr.status==202){
					$('#friendlist').append('');
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
			
		});
	}
	
}
function sendMessage(){
	userid = document.getElementById("topNavHiddenPK").value;
	var currMesaage = document.getElementById("messageBox").value;
	if(receiverId){
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/messages/addMessage/'+userid+'/'+receiverId,
			type:'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: currMesaage,
			success:function(data)
			{
				displayChat(receiverId,rname);
			},
			complete:function(xhr,request,status){
				$('#messageBox').val('');
				displayChat(receiverId,rname);
			},
			error: function(xhr,status){
					
			}
			
		});
		
	}
	
}


