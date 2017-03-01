var userid;
var loggedin_user_profile_pic_full_url;

function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	
	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value + '.jpg';	//Set Profile Pic
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value + '.jpg';
	
	fetchPosts();
}


function fetchPosts(){
	userid = document.getElementById("topNavHiddenPK").value;
	$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/post/getNewsfeedPosts/'+userid,
			type:'GET',
			dataType: 'json',
			success: function(data){
				var no_of_object = data.length;
				for( var i=0; i < no_of_object; i++){
					var key = data[i]['post_pk'];
					var text = data[i]['post_statusText'];
					var sender_name = data[i]['userdetails_firstname']+" "+data[i]['userdetails_lastname'];
					var sender_profile_pic_url = serverPicLocation + data[i]['userdetails_picurl'] + ".jpg";
					var timestamp = data[i]['post_date'].split(" ");
					
					var time_to_display = timestamp[0]+" at "+timestamp[1].substring(0,5);
					$('#newsFeed').append('<div class="panel panel-default">'+
						'<div class="panel-heading">'+
		                	'<span class="pull-left" style="margin-right:12px; margin-top:-5px;">'+
		                		'<img src="'+sender_profile_pic_url+'"  title="John Doe" alt="John Doe" width="40px" height="40px">'+
		                     '</span>'+
		              '<h4>'+ sender_name +'</h4>'+
		              '<h5>'+ time_to_display +'</h5>'+
		              '</div>'+
		              '<div class="panel-body">'+
		                '<p>'+ text +'</p>'+
		                '<hr style="margin-bottom:3px;">'+
		                	'<a href="#"><span class="glyphicon glyphicon-thumbs-up" style="color:grey;margin-right:5px"></span><strong>Like</strong></a>'+
		                	'<a href="#"><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span><strong>Comment</strong></a>'+
		              '</div>'+
		              '<div class="panel-footer" style="margin-top:-8px">'+
		                  '<span class="pull-left" style="margin-right:10px; margin-top:-5px;">'+
		                    '<img src="'+loggedin_user_profile_pic_full_url+'"  title="John Doe" alt="John Doe" width="30px" height="30px">'+
		                  '</span>'+
		                  '<input type="textarea" placeholder="Write a Comment">'+
		              '</div>'+
		            '</div>');					
				}
			}
	})
}




function addPostStatus(){
//	alert('addPOst Called');
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/postStatus/'+userid,
		type:'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			postText:$('#whatsOnYourMind').val(),
			senderId: userid,
			receiverId: userid
        }),
        success : function(data){
        	$('#newsFeed').empty();
        	fetchPosts();
        	$('#whatsOnYourMind').val('');
        }
	})
	
}