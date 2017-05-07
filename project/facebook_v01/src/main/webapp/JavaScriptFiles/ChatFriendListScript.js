/****** This file is for the right hand side column of friends on any page *****/

var userid;

function loadChatColumn()
{
//	alert("Inside loadchat");
	fetchChatFriendList();
}

function fetchChatFriendList(){
	
	userid = document.getElementById("topNavHiddenPK").value;
	//alert(userid);
	
	
	$(document).ready(function() {
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
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover" data-placement="left" data-content=\'<div class="media"><a href="#" class="pull-left"><img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object"></a><div class="media-body"><h4 class="media-heading"><strong>'+name+'</strong></h4><p style="color:grey">'+total_friends+' Friends </p></div></div>\'>'+name+'</a>';
					
					var element = '<br/><div class="sidebar-name">'+ '<span class="pull-left" style="margin-right:12px; margin-top:-2px;">'+
            		'<img src="'+picfullurl+'"  title="'+name+'" alt="John Doe" width="40px" height="30px">'+
                    '</span>'+popoverVariable+'<div/>';
					
					$('#friendlist').append(element);
					
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
	})
}