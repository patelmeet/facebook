var userid;
var refId;
var loggedin_user_profile_pic_full_url;
var loggedin_user_cover_pic_full_url;

function doMyOtherOnloadStuff()
{	
//	$("#timeline").load("MyPosts.html");
	$("#about").load("FriendAbout.html");
	$("#friends").load("Friends.html");
//	alert("Inside doMyOtherOnloadStuff");
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	userid = document.getElementById("topNavHiddenPK").value;
	refId = window.location.search.substr(1).split("=")[1];
	if(refId == userid)
		window.location.href = "TimeLine.html";
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	loggedin_user_cover_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenCoverPicURL").value;

	//alert('refId = '+refId);
	
	populateAfterDatabaseCall(refId);
	fetchPreviousWorkData(refId);
	fetchPreviousCollegeData(refId);
	fetchPreviousSchoolData(refId);
	fetchPreviousPlacesData(refId);
	fetchPosts(refId);
//	setInterval("fetchPosts()",60000);
	addComponentFriendList(refId);
	//===========Rachana : 14/03/2017 22:16==========//
	fetchSuggestions(refId);
	//===========End Rachana : 14/03/2017 22:16==========//
}

function populateAfterDatabaseCall(refId){
	var friendName;
	var friendPicUrl;
	var friendcoverPicUrl;
	//alert('in populate');
	if(refId){
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/friends/getFriendDetails/'+refId,
			type:'GET',
			async:false,
			dataType: 'json',
			success: function(data){
					friendName = data[0]['userdetails_firstname']+" "+data[0]['userdetails_lastname'];
					friendPicUrl = serverPicLocation + data[0]['userdetails_picurl'];
					friendcoverPicUrl = serverPicLocation + data[0]['userdetails_coverpicurl'];
					
					//alert("Friend cover pic = " + friendcoverPicUrl + " " + "Friend Pic Url = " + friendPicUrl);
					document.getElementById("nameFooter").innerHTML = friendName;
					document.getElementById("profilePic").src = friendPicUrl;
					document.getElementById("coverpic").src = friendcoverPicUrl;
				}
		});
	}
	//alert("Friend Name = " + friendName + " " + "Friend Pic Url = " + friendPicUrl);
	//document.getElementById("nameFooter").innerHTML = friendName;
	//document.getElementById("profilePic").src = friendPicUrl;
}


function fetchPreviousWorkData(refId){
	userid = document.getElementById("topNavHiddenPK").value;
	if (refId){
		$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/profile/work/'+refId+'/get/',
				type:'GET',
				dataType: 'json',
				success: function(data){
					var no_of_object = data.length;
					//$('#newsFeed').empty();
					for( var i=0; i < no_of_object; i++){
						var work_company = data[i]['work_company'];
						var work_position = data[i]['work_position'];
						var work_city = data[i]['work_city'];
						var work_start_year = data[i]['work_start_year'];
						var work_end_year = data[i]['work_end_year'];	
						$('#workDynamicDiv').append(
				      			'<h3 style="color:#3b5998;font-size:16px">'+work_company+'</h3>'+
				      			'<p style="color:#808080">'+work_position+ ' in ' +  work_city +'</p>'+
				      			'<hr style="margin-top: 4px">');					
					}
				}
		})
	}
}

function fetchPreviousCollegeData(refId){
	userid = document.getElementById("topNavHiddenPK").value;
	if (refId){
		$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/profile/college/'+refId,
				type:'GET',
				dataType: 'json',
				success: function(data){
					var no_of_object = data.length;
					//$('#newsFeed').empty();
					for( var i=0; i < no_of_object; i++){
						var college_name = data[i]['college_name'];
						var college_start_year = data[i]['college_start_year'];
						var college_end_year = data[i]['college_end_year'];
						var college_concentration = data[i]['college_concentration'];	
						$('#collegeDynamicDiv').append(
				      			'<h3 style="color:#3b5998;font-size:16px">'+college_name+'</h3>'+
				      			'<p style="color:#808080">'+college_concentration+ ' From ' +  college_start_year + ' to ' + college_end_year +'</p>'+
				      			'<hr style="margin-top: 4px">');					
					}
				}
		})
	}
}


function fetchPreviousSchoolData(refId){
	userid = document.getElementById("topNavHiddenPK").value;
	if (refId){
		$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/profile/school/'+refId,
				type:'GET',
				dataType: 'json',
				success: function(data){
					var no_of_object = data.length;
					//$('#newsFeed').empty();
					for( var i=0; i < no_of_object; i++){
						var school_name = data[i]['school_name'];
						var school_start_year = data[i]['school_start_year'];
						var school_end_year = data[i]['school_end_year'];
						var school_description = data[i]['school_description'];	
						$('#schoolDynamicDiv').append(
				      			'<h3 style="color:#3b5998;font-size:16px">'+school_name+'</h3>'+
				      			'<p style="color:#808080">'+school_start_year + ' to ' + school_end_year + ', '+school_description+ '</p>'+
				      			'<hr style="margin-top: 4px">');					
					}
				}
		})
	}
}

function fetchPreviousPlacesData(refId){
	var userid = document.getElementById("topNavHiddenPK").value;
	if (refId){
		$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/profile/getPlaces/'+refId,
				type:'GET',
				dataType: 'json',
				success: function(data){
					var no_of_object = data.length;
					//$('#newsFeed').empty();
					for( var i=0; i < no_of_object; i++){
						places_current_city = data[i]['places_current_city'];
						places_hometown = data[i]['places_hometown'];	
						$('#placesDynamicDiv').append(
				      			'<p style="color:black">Current Place: '+places_current_city+'</h3>'+
				      			'<p style="color:black">Home Town: '+places_hometown+ '</p>'+
				      			'<hr style="margin-top: 4px">');					
					}
				}
		})
	}
}


//==================================Rachana============================================//

function fetchPosts(refId){
	userid = document.getElementById("topNavHiddenPK").value;
	
	var postSenderDob = "";
	var postSenderEmail = "";
//	$("#timeline").load("MyPosts.html");

	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/getTimelinePosts/'+refId,
		type:'GET',
		async : false,
		dataType: 'json',
		success: function(data){
			var no_of_object = data.length;
			$('#mytimelineposts').empty();
			for( var i=0; i < no_of_object; i++){
				var post_id = data[i]['post_pk'];
				var post_text = data[i]['post_statusText'];
				var post_senderId = data[i]['post_senderId'];
				var sender_name = data[i]['userdetails_firstname']+" "+data[i]['userdetails_lastname'];
				var sender_profile_pic_url = serverPicLocation + data[i]['userdetails_picurl'] ;
				var post_like_count = data[i]['post_like_count'];				//For Like
				var isliked = isPostLikedByUserFunction(userid,post_id);		//For Like
				var popoverVariable;
//					alert(post_senderId);
				$.ajax({
					url:'http://localhost:8080/facebook_v01/webapi/user/getDobEmail/'+post_senderId,
					type:'GET',
					dataType: 'json',
					async:false,
					success: function(data){
//							alert("In success");
						postSenderDob = data['userdetails_dob'];
						postSenderEmail = data['userdetails_email'];
					}
				});
				
				popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><a href="#" class="pull-left"><img src="'+ sender_profile_pic_url +'" style="width:50px;height:60px" class="media-object"></a><div class="media-body"><h4 class="media-heading"><strong>'+sender_name+'</strong></h4><p style="color:grey">'+postSenderEmail+'</br>Born on: '+postSenderDob+'</p></div></div>\'>'+sender_name+'</a>';
				
				var timestamp = data[i]['post_date'];
				var time_to_display = calculatePostTimeToDisplay(timestamp);
				
				var element = '<div class="panel panel-default">'+
					'<div class="panel-heading">'+
					'<span class="pull-left" style="margin-right:12px; margin-top:-5px;">'+
					'<img src="'+sender_profile_pic_url+'"  title="John Doe" alt="John Doe" width="40px" height="40px">'+
					'</span>'+
					'<h4>'+ popoverVariable +'</h4>'+
					'<h5>'+ time_to_display +'</h5>'+
					'</div>'+
					'<div class="panel-body">'+
					'<p>'+ post_text +'</p>'+
					'<hr style="margin-bottom:3px;">';
				
				
				//Code for Hover on Likes start.
				var likehovervariable;
				
				likehovervariable = '<a  style=" cursor:pointer; margin-right:10px; margin-top:-5px; color:#3b5998 " id="'+post_id+'-count" value="'+post_like_count+'"  href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body">';
				$.ajax({
					url:'http://localhost:8080/facebook_v01/webapi/post/getPostLikeList/'+post_id,
					type:'POST',
					dataType: 'json',
					async:false,
					success: function(data){
						var no_of_likes = data.length;
						for(var j =0 ; j < no_of_likes ; j++){
							likehovervariable = likehovervariable +data[j]['userdetails_firstname']+' '+data[j]['userdetails_lastname']+ '</br>';
						}
					}
				});
				if (post_like_count > 0)
					likehovervariable = likehovervariable + '</div></div>\'>'+post_like_count+' likes </a><br/>';
				else
					likehovervariable = likehovervariable + 'No one has liked this</div></div>\'>'+post_like_count+' likes </a><br/>';
					
				if(isliked=='true')
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:blue;margin-right:5px"></span><strong style="color:blue">Like</strong></a>';
				else
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:gray;margin-right:5px"></span><strong>Like</strong></a>';
				element = element + '<a style=" cursor:pointer;" ><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span>';
				
				
				//Code for Hover on Likes End.
								
				
				/*if(isliked=='true')
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:blue;margin-right:5px"></span><strong style="color:blue">Like</strong></a>';
				else
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:gray;margin-right:5px"></span><strong>Like</strong></a>';
				element = element + '<a style=" cursor:pointer;" ><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span><strong>Comment</strong></a>'+
			              '</div>'+
			              '<div class="panel-footer" style="margin-top:-8px">'+
			              '<span class="pull-left" style="margin-right:10px; margin-top:-5px;" id="'+post_id+'-count" value="'+post_like_count+'">'+ post_like_count +' likes </span><br/>';
//					alert('http://localhost:8080/facebook_v01/webapi/post/getCommentList/'+post_id);*/				
				$.ajax({
					url:'http://localhost:8080/facebook_v01/webapi/post/getCommentList/'+post_id,
					type:'POST',
					async : false,
					dataType: 'json',
					success : function(data){
						var no_of_comments = data.length;
						element =  element + '<span>'+no_of_comments+'&nbsp;</span><strong>  Comment</strong></a>'+
			              '</div>'+
			              '<div class="panel-footer" style="margin-top:-8px;">'+likehovervariable;
						if(no_of_comments != 0){
//								alert(data[0]['userdetails_username']);
							for(var j=0 ; j < no_of_comments ; j++){
							element = element +
				                  '<span class="pull-left" style="margin-right:10px; margin-top:-5px;">'+
				                  '<img src="'+serverPicLocation + data[j]['userdetails_picurl']+'"  title="John Doe" alt="John Doe" width="15px" height="15px">'+
				                  '</span>'+
				                  '<span><a style="color:#3b5998;font-size:12px">'+data[j]['userdetails_firstname']+' '+data[j]['userdetails_lastname']+'</a>      '+data[j]['comment_text']+'</span><br/><br/>';
				                  
							}
							
						}	
					}
				});
				element = element +'<br/>'+
					'<div>'+ 
					'<span class="pull-left" style="margin-right:10px; margin-top:-5px;">'+
					'<img src="'+loggedin_user_profile_pic_full_url+'"  title="John Doe" alt="John Doe" width="30px" height="30px">'+
					'</span>'+
					'<textarea placeholder="Write a Comment" id="'+post_id+'" onkeypress="commentonkeypressfunction(event);" ></textarea>'+
					'</div>'+
					'</div></div>';
//				alert(element);
				$('#mytimelineposts').append(element);
				$('[data-toggle="popover"]').popover();
			}
		}
	});
}

function isPostLikedByUserFunction(userid,post_id)
{
	var isliked;
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/isPostLiked/'+userid+'/'+post_id,
		type:'POST',
		async: false,
		complete : function(request, textStatus, errorThrown){
			if(request.status===202){
				isliked = 'false';
			}
			if(request.status===201){
				isliked = 'true';
			}
		}
	});
	return isliked;
}


function likeonclickfunction(event)
{
	var post_id = event.currentTarget.id;
	var namedata = event.currentTarget.name;
//	alert(post_id);
//	alert(namedata);
	
	if(namedata=='true'){
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/post/removePostLike/'+post_id+'/'+userid,
			type:'POST',
			complete : function(request, textStatus, errorThrown){
				$('#mytimelineposts').empty();
				fetchPosts(refId);
//				alert("completed");
			}
		});
	}
	else{
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/post/addPostLike/'+post_id+'/'+userid,
			type:'POST',
			complete : function(request, textStatus, errorThrown){
				$('#mytimelineposts').empty();
				fetchPosts(refId);
//				alert("completed");
			}
		});
	}	
}

function commentonkeypressfunction(event)
{
	if (event.keyCode == 13 && !event.shiftKey) 
	{
		var post_id = event.currentTarget.id;
		var commentvalue = event.currentTarget.value;
		if(commentvalue){
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/post/addPostComment/'+post_id+'/'+userid,
				type:'POST',
				contentType: 'application/json',
				data : JSON.stringify({comment:commentvalue}),
				complete : function(request, textStatus, errorThrown){
					$('#mytimelineposts').empty();
					fetchPosts();
//					alert("comment added");
				}
			});
		}
        return false;
    }
}


function addPostStatus(){
//	alert('addPOst Called');
	userid = document.getElementById("topNavHiddenPK").value;
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/postStatus/'+refId,
		type:'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			postText:$('#whatsOnYourMind').val(),
			senderId: userid,
			receiverId: refId
        }),
        success : function(data){
        	$('#newsFeed').empty();
        	fetchPosts(refId);
        	$('#whatsOnYourMind').val('');
        }
	})
	
}

function calculatePostTimeToDisplay(postTimestamp){    // Format of postTimestamp::2017-03-21 13:06:22:2

	var ymd = postTimestamp.split(" ")[0].split("-");  // ymd :: yyyy-mm-dd
	var monthName;
	
	// Month Number to Month Name mapping
	if(ymd[1] === '01') monthName = 'January'; else if(ymd[1] === '02') monthName = 'February'; else if(ymd[1] === '03') monthName = 'March';
	else if(ymd[1] === '04') monthName = 'April';else if(ymd[1] === '05') monthName = 'May';else if(ymd[1] === '06') monthName = 'June';
	else if(ymd[1] === '07') monthName = 'July';else if(ymd[1] === '08') monthName = 'August';else if(ymd[1] === '09') monthName = 'September';
	else if(ymd[1] === '10') monthName = 'October';else if(ymd[1] === '11') monthName = 'November';else if(ymd[1] === '12') monthName = 'December';
	
	
	var postTimestampDateObject = new Date(postTimestamp); //SQL Date to Javascript Date
	var currentSystemTime = new Date();					   
	
	//Calculate time difference b/w post and current time
	var diffInMin = (currentSystemTime.getTime() - postTimestampDateObject.getTime()) / 60000;
	var diffInHrs = diffInMin/60;
	var diffInDay = currentSystemTime.getDate()-postTimestampDateObject.getDate();
	var timeToDisplay;
	
	if(diffInMin <= 1)
		timeToDisplay = "Just Now";
	
	else if(diffInMin < 60)
		timeToDisplay = Math.round(diffInMin) + " min";
	
	else if(diffInMin < 1440){
			if(Math.round(diffInHrs) == 1)
				timeToDisplay = Math.round(diffInHrs) + " hr";
			else
				timeToDisplay = Math.round(diffInHrs) + " hrs";
	}
		
	else if(diffInMin >= 1440 && (diffInDay==1 || diffInDay==-30 || diffInDay==-29 || diffInDay==-27 || diffInDay==-28))
		timeToDisplay = "Yesterday at "+postTimestamp.split(" ")[1].substring(0,5);
	
	else
		timeToDisplay = monthName + " " + ymd[2] + " at " + postTimestamp.split(" ")[1].substring(0,5);
	
	return  timeToDisplay;
}

function addComponentFriendList(refId){
	userid = document.getElementById("topNavHiddenPK").value;
//	alert("Inside addComponentFriendList");
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/friends/getOtherFriendsDetail/'+refId+'/'+userid,
			type:'POST',
			dataType: 'json',
			success: function(data){
				var no_of_object = parseInt(data.length);
				var half_no_of_object = parseInt((no_of_object-1)/2);
				for( var i=0 ; i <= half_no_of_object ;i++){
					var name = data[i]['friend_name'];
					var total_friends = data[i]['total_friends'];
					var friend_pk = data[i]['friend_pk'];
					var picfullurl = serverPicLocation + data[i]['friend_picture'];
					var status = data[i]['friend_status'];
					//alert(status);
					if(status == 'other'){
						$('#row1').append('<li class="list-group-item">'+
				                            '<div class="col-md-5">'+
				                              '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
				                              '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
				                              '</a>'+
				                            '</div>'+
				                            '<div class="col-md-3" style="margin-left:-30px ; margin-top:5px">'+
				                                  '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
				                                  '<small> '+total_friends+' Friend </small>'+
				                            '</div>'+
				                            '<div class="col-md-4" id = "temp" style="margin-top:20px">'+ 
                                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="SendFriendRequest(event)">Add Friend</button>'+'&nbsp;'+
                                            '</div>'+
				                            '<div class="clearfix"></div>'+
				                          '</li>');
					}
					else{
						$('#row1').append('<li class="list-group-item">'+
	                            '<div class="col-md-5">'+
	                              '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
	                              '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
	                              '</a>'+
	                            '</div>'+
	                            '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'+
	                                  '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
	                                  '<small> '+total_friends+' Friend </small>'+
	                            '</div>'+
	                            '<div class="clearfix"></div>'+
	                          '</li>');
						
						
					}
					
					
					
					}
				for( var i=half_no_of_object+1 ; i < no_of_object ;i++){
					var name = data[i]['friend_name'];
					var total_friends = data[i]['total_friends'];
					var friend_pk = data[i]['friend_pk'];
					var picfullurl = serverPicLocation + data[i]['friend_picture'];
					var status = data[i]['friend_status'];
					//alert(status);
					if(status == 'other'){
					$('#row2').append('<li class="list-group-item">'+
                            '<div class="col-md-5">'+
                            '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
                            '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
                            '</a>'+
                          '</div>'+
                          '<div class="col-md-3" style="margin-left:-30px ; margin-top:5px">'+
                                '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
                                '<small> '+total_friends+' Friend </small>'+
                          '</div>'+
                          '<div class="col-md-4" id = "temp" style="margin-top:20px">'+ 
                          '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="SendFriendRequest(event)">Add Friend</button>'+'&nbsp;'+
                          '</div>'+
                          '<div class="clearfix"></div>'+
                        '</li>');
					}
					else
					{
						$('#row2').append('<li class="list-group-item">'+
	                            '<div class="col-md-5">'+
	                            '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
	                            '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
	                            '</a>'+
	                          '</div>'+
	                          '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'+
	                                '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
	                                '<small> '+total_friends+' Friend </small>'+
	                          '</div>'+
	                          '<div class="clearfix"></div>'+
	                        '</li>');
						
						
					}
				}
			},
			error: function(xhr,status){
				if(xhr.status==202){
					$('#row1').append('No Friends to show');
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
}

//==================================End Rachana========================================//
//====================Rachana 14/03/2017 22:18======================//
function fetchSuggestions(refId){
	var userid = document.getElementById("topNavHiddenPK").value;
	if (refId){
		$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/friends/getUncommonFriends/'+userid+'/'+refId,
				type:'GET',
				dataType: 'json',
				success: function(data){
					var no_of_object = data.length;
					for( var i=0; i < no_of_object; i++){
						friend_pk = data[i]['friend_pk'];
						friend_name = data[i]['friend_name'];
						picfullurl = serverPicLocation + data[i]['friend_picture'];
						$('#suggestionsList').append('<li class="list-group-item">'+
								'<div class="col-md-2">'+
								'<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
								'<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
								'</a>'+
								'</div>'+
								'<div class="col-md-5">'+
                                '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+friend_name+'</Strong></a><br/>'+
                                '</div>'+
								'<div class="col-md-5" id="'+friend_pk+'div">'+
								'<button class="btn btn-primary" type="button" id="'+refId+'" name="'+friend_pk+'" onclick="sendSuggestion(event)">Suggest Friend</button>'+
								'</div>'+
								'<div class="clearfix"></div>'+
								'</li>');					
					}
				},
				error: function(xhr,status){
		            if(xhr.status==202){
		            	$('#suggestionsList').append('<li class="list-group-item">'+
		            			'<div class="col-md-5"><Strong>You both have same friends!<br/>No Suggestions to display.</Strong></div>');
		            }
		            else{
		            	$('#suggestionsList').append('<li class="list-group-item">'+
            			'<div class="col-md-5">Something went wrong</div>');
		            }
				}
		})
	}
}

function sendSuggestion(event)
{
	userid = document.getElementById("topNavHiddenPK").value;
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/addSuggestion/'+userid+'/'+event.target.id+'/'+event.target.name,
		async:false,
		type:'POST',
		success: function(data){
			$('#'+event.target.name+'div').text('Suggestion sent');
			//fetchSuggestions(event.target.id);
		}
	});
}



//==================================End Rachana 14/03/2017 22:18========================================//

function SendFriendRequest(event)
{
    $.ajax({
        url:'http://localhost:8080/facebook_v01/webapi/friends/sendFriendRequest/'+userid+'/'+event.target.id,
        type:'POST',
        success: function(data){
        	$('#row2').empty();
            addComponentFriendList(refId);
        },
        error: function(xhr,status){
            if(xhr.status==202){
                
            }
            else{
                window.location.href = 'errorpage.html';    //Something went wrong -> redirect to error page
            }       
        }
    });
}