
var groupid;
var loggedin_user_profile_pic_full_url;
var groupAdmin;
var userid;
function doMyOtherOnloadStuff()
{
	//alert("welcome");
	
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	
	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	loadUserGroup();
}

function loadUserGroup(){
		
	$("#Members").load("GroupMember.html");
	userid =document.getElementById("topNavHiddenPK").value;
	// For getting url parameter 
	var groupid = getUrlParameter("gpid");

	getGroupDetails(groupid , userid);
	addComponentGroupMembers(groupid);
	
	//for Fetching Group post
	//fetchPosts();
	fetchPosts();
	
}

function getGroupDetails(groupid,userid){
	
	//alert("Indside getGroupDetails " + userid + " " + groupid);

			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getGroupDetails/'+userid+'/'+groupid,
				type:'GET',
				dataType: 'json',
				success: function(data){
			
					var name = data['groupdetails_name'];
					var picfullurl = serverPicLocation + data['groupdetails_imageurl'];
					var privacy = data['groupdetails_privacy'];
					groupAdmin = data['groupdetails_createdby'];
					
					if (userid == groupAdmin){
						$("#Requests").load("GroupRequest.html");
						$("#AddMembers").load("AddMemberToGroup.html");
						
						addComponentPendingRequests(groupid);
						
						// Calling function : Admin can add members
						addComponentAddMembersToGroupList(groupid);
					}
					else{
						$("#Requests").append("<h3>Sorry! You do not have the privilege to view requests for this group.</h3>");
						$("#AddMembers").append("<h3>Sorry! You do not have the privilege to add members in this group.</h3>");
					}
					
					var setPrivacy="Other"
						
						
					if(privacy ===0 ){
						setPrivacy="Public Group"
					}
					else if(privacy==1){
						setPrivacy="Closed Group"
					}
					else{
						setPrivacy="Secret Group"
					}
	
					$('#GrpName').append(
							'<div>'+ name +'</div>'
						   );
					$('#GrpPrivacy').append(
							'<div>'+ setPrivacy +'</div>'
						   );
					
					// call function for getting pending requests
					
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


function addComponentPendingRequests(groupid){	
		
	//alert("addComponent  Pending   Requests " + groupid);
		
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getGroupRequestDetails/'+groupid,
			type:'GET',
			dataType: 'json',
			
			success: function(data){
				
				var no_of_object = data.length;
				
				//alert("Inside success");
				
				for( var i=0 ; i < no_of_object ;i++){
					
					var pk = data[i]['userDetails_pk'];
					var fname = data[i]['userDetails_firstName'];
					var lname = data[i]['userDetails_lastName'];
					//var name = fname + " "+lname;
					var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
					
					console.log(fname + " "+ lname);
					
					//alert(" PendingRequests success");
					
					$('#pendingJoinGroupRequests').append('<li class="list-group-item">'+
			                            '<div class="col-md-3">'+
			                              '<a href="#">'+
			                              '<img src="'+picfullurl+'"  class="circle img-thumbnail img-box"  width=80 height=80/>'+
			                              '</a>'+
			                            '</div>'+
			                            
			                            '<div class="col-md-3">'+
			                                  '<a  class="name"  href="#" color="black"><Strong>'+fname+'</Strong></a><br/>'+
			               
			                            '</div>'+
			                            
			                            '<div class="col-md-3" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" id="'+pk+'" onclick="ConfirmGroupRequest('+pk+','+groupid+')">Confirm</button>'+'&nbsp;'+
		
			                            '</div>'+
			                            
			                            '<div class="col-md-3" id = "temp">'+
			                            '<button class="btn btn-friends-primary" type="button" id="'+ pk+'" onclick="DeclineGroupRequest('+pk+','+groupid+')">Decline</button>'+
			                            '</div>'+
			                            
			                            
			                            '<div class="clearfix"></div>'+
			                          '</li>'); 
					}
			},
			error: function(xhr,status){
			
				if(xhr.status==202){
					//location.reload();
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
//    });
}


//========================= 1 April By Nitish ==========================
//// For Adding members to Group

function AddMemberToGroup(event) {
			
		var grp=event.target.name;
		
		//alert("This is Group id " + event.target.name + " : " + event.target.id);
		
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/groupdetails/addMemberToGroup/'
					+ event.target.name + '/' + event.target.id,
			type : 'POST',
			async:false,
			complete : function(request, textStatus, errorThrown){
				if(request.status===201){
					
					//location.reload();
				
					$('#AddMemberToGrouplist1').empty();
					$('#AddMemberToGrouplist2').empty();
					addComponentAddMembersToGroupList(grp);
				}
				else{
					window.location.href = 'errorpage.html';
				}
			}
		});
	}

	// For Getting Users who could be added by Admin of Group

	function addComponentAddMembersToGroupList(groupid){	
		
	//alert("addComponent  Group  Members " + groupid);
			
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getMembersToAddDetails/'+groupid+'/'+userid,
				type:'GET',
				dataType: 'json',
				
				success: function(data){
					
					var no_of_object = data.length;
					
					var half_no_of_object = parseInt((no_of_object-1)/2);
					
					
					var no_of_object = parseInt(data.length);
					var half_no_of_object = parseInt((no_of_object - 1) / 2);
					
						for (var i = 0; i <= half_no_of_object; i++) {
							var pk = data[i]['userDetails_pk'];
							var fname = data[i]['userDetails_firstName'];
							var lname = data[i]['userDetails_lastName'];
							var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
							
							$('#AddMemberToGrouplist1')
									.append(
											'<li class="list-group-item">'
													+ '<div class="col-md-5">'
													+ '<a href="TimeLineFriend.html?refId='
													+ pk
													+ '">'
													+ '<img src="'
													+ picfullurl
													+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
													+ '</a>'
													+ '</div>'
													
													+ '<div class="col-md-5" style="margin-left:-30p ; margin-top:10px">'
													+ '<a  class="name"  href="TimeLineFriend.html?refId='
													+ pk
													+ '" color="black"><Strong>'
													+ fname
													+ '</Strong></a><br/>'
													+ '</div>'
													+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
													+ '<button class="btn btn-primary" type="button" id="'
													+ pk
													+ '" name="'+groupid+'" onclick="AddMemberToGroup(event)">Add</button>'
													+ '&nbsp;'
													+ '</div>'
													+
		
													'<div class="clearfix"></div>'
													+ '</li>');
						}
						for (var i = half_no_of_object + 1; i < no_of_object; i++) {
							var pk = data[i]['userDetails_pk'];
							var fname = data[i]['userDetails_firstName'];
							var lname = data[i]['userDetails_lastName'];
							var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
							
							$('#AddMemberToGrouplist2')
									.append(
											'<li class="list-group-item">'
													+ '<div class="col-md-5">'
													+ '<a href="TimeLineFriend.html?refId='
													+ pk
													+ '">'
													+ '<img src="'
													+ picfullurl
													+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
													+ '</a>'
													+ '</div>'
													+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
													+ '<a  class="name"  href="TimeLineFriend.html?refId='
													+ pk
													+ '" color="black"><Strong>'
													+ fname
													+ '</Strong></a><br/>'
													+ '</div>'
													+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
													+ '<button class="btn btn-primary" type="button" id="'
													+ pk
													+ '" name="'+groupid+'" onclick="AddMemberToGroup(event)">Add</button>'
													+ '&nbsp;'
													+ '</div>'
													+
		
													'<div class="clearfix"></div>'
													+ '</li>');
						}
					
				},
				error: function(xhr,status){
				
					if(xhr.status==202){
						//location.reload();
					}
					else{
						window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
					}		
				}
			});
//	    });
	}


//========================= 1 April By Nitish==========================
//// Adding Members to Group ends =====================================


function ConfirmGroupRequest(userpk,grpid)
{	
	//alert("Hello ConfirmFriendRequest " + userpk + " : "+grpid);
	
	//userid = document.getElementById("topNavHiddenPK").value;
		
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/groupdetails/confirmJoinGroupRequest/'+grpid+'/'+userpk,
		type:'POST',
		success: function(data){
			//alert("Join Request accepted");
			location.reload();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				//window.location.href = 'errorpage.html';
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}
		}
	});

}

function DeclineGroupRequest(userpk,grpid)
{
	//alert("Hello Decline FriendRequest " + userpk + " : "+grpid);
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/groupdetails/declineJoinGroupRequest/'+grpid+'/'+userpk,
		type:'POST',
		success: function(data){
			//alert("Request Declined");
			location.reload();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				//location.reload();
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}
		}
	});
		
	//location.reload();
	//window.location.href = "http://localhost:8080/facebook_v01/GroupRequest.html?gpid=" + grpid;

}

function RemoveMember(event) {
	//alert(event.target.id);
	var grp=event.target.name;
	$.ajax({
		url : 'http://localhost:8080/facebook_v01/webapi/groupdetails/removeGroupMember/'
				+ event.target.name + '/' + event.target.id,
		type : 'DELETE',
		async:false,
		complete : function(request, textStatus, errorThrown){
			if(request.status===201){
				//alert(data);
				$('#groupMemberList1').empty();
				$('#groupMemberList2').empty();
				addComponentGroupMembers(grp);
			}
			else if(request.status===204){
				alert('can not remove admin');
			}
			else{
				window.location.href = 'errorpage.html';
			}
		}
	});
}


//===========================Rachana modification 17/03/2017 23:00==========================//

function addComponentGroupMembers(groupid){	
	
//alert("addComponent  Group  Members " + groupid);
		
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getGroupMembersDetails/'+groupid,
			type:'GET',
			dataType: 'json',
			
			success: function(data){
				
				var no_of_object = data.length;
				
				var half_no_of_object = parseInt((no_of_object-1)/2);
				
				//alert("Inside success Group Members : march 13");
				
				var no_of_object = parseInt(data.length);
				var half_no_of_object = parseInt((no_of_object - 1) / 2);
				if(groupAdmin == userid){
					
					for (var i = 0; i <= half_no_of_object; i++) {
						var pk = data[i]['userDetails_pk'];
						var fname = data[i]['userDetails_firstName'];
						var lname = data[i]['userDetails_lastName'];
						var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
						
						$('#groupMemberList1')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ pk
												+ '" color="black"><Strong>'
												+ fname
												+ '</Strong></a><br/>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												+ '<button class="btn btn-primary" type="button" id="'
												+ pk
												+ '" name="'+groupid+'" onclick="RemoveMember(event)">Remove</button>'
												+ '&nbsp;'
												+ '</div>'
												+
	
												'<div class="clearfix"></div>'
												+ '</li>');
					}
					for (var i = half_no_of_object + 1; i < no_of_object; i++) {
						var pk = data[i]['userDetails_pk'];
						var fname = data[i]['userDetails_firstName'];
						var lname = data[i]['userDetails_lastName'];
						var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
						
						$('#groupMemberList2')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ pk
												+ '" color="black"><Strong>'
												+ fname
												+ '</Strong></a><br/>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												+ '<button class="btn btn-primary" type="button" id="'
												+ pk
												+ '" name="'+groupid+'" onclick="RemoveMember(event)">Remove</button>'
												+ '&nbsp;'
												+ '</div>'
												+
	
												'<div class="clearfix"></div>'
												+ '</li>');
					}
				}
				else{
					for (var i = 0; i <= half_no_of_object; i++) {
						var pk = data[i]['userDetails_pk'];
						var fname = data[i]['userDetails_firstName'];
						var lname = data[i]['userDetails_lastName'];
						var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
						
						$('#groupMemberList1')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ pk
												+ '" color="black"><Strong>'
												+ fname
												+ '</Strong></a><br/>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												
												+ '</div>'
												+
	
												'<div class="clearfix"></div>'
												+ '</li>');
					}
					for (var i = half_no_of_object + 1; i < no_of_object; i++) {
						var pk = data[i]['userDetails_pk'];
						var fname = data[i]['userDetails_firstName'];
						var lname = data[i]['userDetails_lastName'];
						var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
						
						$('#groupMemberList2')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ pk
												+ '" color="black"><Strong>'
												+ fname
												+ '</Strong></a><br/>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												
												+ '</div>'
												+
	
												'<div class="clearfix"></div>'
												+ '</li>');
					}
				}
				
			},
			error: function(xhr,status){
			
				if(xhr.status==202){
					//location.reload();
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
//    });
}

//===========================End Rachana modification 17/03/2017 23:00==========================//




//================== For Group Post ==================================================
//====================================================================================
// Copied from Home Page Script

/// For Fetching post
function fetchPosts(){
	
userid = document.getElementById("topNavHiddenPK").value;
	
	var postSenderDob = "";
	var postSenderEmail = "";
	var element;
	var groupid = getUrlParameter("gpid");

	$.ajax({/// Here url has been changed for group post
			url:'http://localhost:8080/facebook_v01/webapi/post/getGroupPosts/'+groupid,
			type:'GET',
			async : false,
			dataType: 'json',
			success: function(data){
				var no_of_object = data.length;
				$('#groupFeed').empty();
				for( var i=0; i < no_of_object; i++){
					//alert("For i = " + i);
					
					var post_id = data[i]['post_pk'];
					var post_text = data[i]['post_statusText'];
					var post_senderId = data[i]['post_senderId'];
					
					//alert("Inside for loop " + post_senderId );
					
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
//							//alert("In success");
							postSenderDob = data['userdetails_dob'];
							postSenderEmail = data['userdetails_email'];
						}
					});
					
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><a href="#" class="pull-left"><img src="'+ sender_profile_pic_url +'" style="width:50px;height:60px" class="media-object"></a><div class="media-body"><h4 class="media-heading"><strong>'+sender_name+'</strong></h4><p style="color:grey">'+postSenderEmail+'</br>Born on: '+postSenderDob+'</p></div></div>\'>'+sender_name+'</a>';
					
					var timestamp = data[i]['post_date'];
					var time_to_display = calculatePostTimeToDisplay(timestamp);
					
					element = '<div class="panel panel-default">'+
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
					
					if(isliked=='true')
						element = element + '<a id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:blue;margin-right:5px"></span><strong style="color:blue">Like</strong></a>';
					else
						element = element + '<a id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:gray;margin-right:5px"></span><strong>Like</strong></a>';
					element = element + '<a ><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span><strong>Comment</strong></a>'+
				              '</div>'+
				              '<div class="panel-footer" style="margin-top:-8px">'+
				              '<span class="pull-left" style="margin-right:10px; margin-top:-5px;" id="'+post_id+'-count" value="'+post_like_count+'">'+ post_like_count +' likes </span><br/>';
//					alert('http://localhost:8080/facebook_v01/webapi/post/getCommentList/'+post_id);
					
					$.ajax({
						url:'http://localhost:8080/facebook_v01/webapi/post/getCommentList/'+post_id,
						type:'POST',
						async : false,
						dataType: 'json',
						success : function(data){
							var no_of_comments = data.length;
							
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
							else{}
//								alert("No comments");
								
						}
					});
					
					element = element +'<br/>'+
				              '<div>'+ 
				                  '<span class="pull-left" style="margin-right:10px; margin-top:-5px;">'+
				                    '<img src="'+loggedin_user_profile_pic_full_url+'"  title="John Doe" alt="John Doe" width="30px" height="30px">'+
				                  '</span>'+
				                  '<textarea placeholder="Write a Comment" id="'+post_id+'" onkeypress="commentonkeypressfunction(event);" ></textarea>'+
				              '</div>'+
				            '</div>';
					
					
					//alert("For i = " + i);
					//var element = '<h3>Hello world!!</h3>';
					$('#groupFeed').append(element);
					
					$('[data-toggle="popover"]').popover();
					
					
					
				}
			}
	});  
}




function addPostStatus(){
	
	var groupid = getUrlParameter("gpid");
		
	//alert("first");
			
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/post/postToGroup/'+userid,
				//url:'http://localhost:8080/facebook_v01/webapi/groupdetails/postingroup/'+userid,
				type:'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					postText:$('#whatsOnYourMind').val(),
					senderId: userid,
					receiverId: groupid
		        }),
		        success : function(data){
		        	//alert("second");
		        	$('#groupFeed').empty();
		        	fetchPosts();
		        	$('#whatsOnYourMind').val('');
		        }
			})
		
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
				$('#groupPost').empty();
				fetchPosts();
//				alert("completed");
			}
		});
	}
	else{
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/post/addPostLike/'+post_id+'/'+userid,
			type:'POST',
			complete : function(request, textStatus, errorThrown){
				$('#groupPost').empty();
				fetchPosts();
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
					$('#groupPost').empty();
					fetchPosts();
//					alert("comment added");
				}
			});
		}
        return false;
    }
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


////////////  Copied from Home Page Script ends
// =========================================================================
// =========================================================================


//This is for reading url gpid parameter
//For Exmaple
//http://localhost:8080/facebook_v01/grouppage.html?gpid=10
function getUrlParameter(sParam) {
	   var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	       sURLVariables = sPageURL.split('&'),
	       sParameterName,
	       i;

	   for (i = 0; i < sURLVariables.length; i++) {
	       sParameterName = sURLVariables[i].split('=');

	       if (sParameterName[0] === sParam) {
	           return sParameterName[1] === undefined ? true : sParameterName[1];
	       }
	   }
	 }