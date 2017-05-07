/**
 * 
 */
var userid;	//GET FROM SESSION
var eventid;	//GET FROM SESSION
var friendList;
function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	
	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;	//Set Profile Pic
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	getEventDetail();
	addComponentFriendList();
	fetchUpdates();
}

function addComponentFriendList(){
	userid = document.getElementById("topNavHiddenPK").value;
	eventid = window.location.search.substr(1).split("=")[1];
	$('#row1').empty();
//	alert("Inside addComponentFriendList");
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/eventList/getFriendsForInvites/'+eventid+'/'+userid,
			type:'POST',
			dataType: 'json',
			success: function(data){
				var no_of_object = parseInt(data.length);
				
				for( var i=0 ; i < no_of_object ;i++){
					
					var name = data[i]['friend_name'];
					var total_friends = data[i]['total_friends'];
					var friend_pk = data[i]['friend_pk'];
					var picfullurl = serverPicLocation + data[i]['friend_picture'];
					$('#row1').append('<li class="list-group-item">'+
			                            '<div class="col-md-5">'+
			                              '<a href="#">'+
			                              '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'+
			                                  '<a  class="name"  href="#" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                                  '<small> '+total_friends+' Friend </small>'+
			                            '</div>'+
			                            '<div class="col-md-2,checkbox" id = "temp" style="margin-left:-30px ; margin-top:20px">'+
			                            '<input type="checkbox" id='+name+' name="invite" value='+friend_pk+'></input>'+'&nbsp;'+
			                            '</div>'+

			                            '<div class="clearfix"></div>'+
			                          '</li>');
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
		
		function getEventDetail(){
			userid = document.getElementById("topNavHiddenPK").value;
			eventid = window.location.search.substr(1).split("=")[1];
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/eventList/getEventDetails/'+userid+'/'+eventid,
				type:'POST',
				dataType: 'json',
				success: function(data){
							
							$('#eventTitle').empty();
							var no_of_object = data.length;
							for( var j=0 ; j < no_of_object ;j++){
								var event_pk = data[j]['event_pk'];
								var event_name = data[j]['event_name'];
								var location = data[j]['event_location'];
								var start_date = data[j]['event_start_date'];
								var start_time = data[j]['event_start_time'];
								var description = data[j]['event_description'];
								var eventpicurl = "eventPictures/" + data[j]['event_photo'];
								var maybe = data[j]['event_maybe'];
								var going = data[j]['event_going'];
								var cant = data[j]['event_cant_go'];
								
								//pop hover for going count
								var popoverVariable;
								var status_info;
			//					alert(post_senderId);
								$.ajax({
									url:'http://localhost:8080/facebook_v01/webapi/EventMemberList/getEventMemberList/'+eventid+'/Going',
									type:'POST',
									dataType: 'json',
									async:false,
									success: function(data){
									//alert(data);
										var no_of_object = parseInt(data.length);
										status_info = '';
										for( var i=0 ; i < no_of_object ;i++){
											var postSenderFirstname = data[i]['userdetails_firstname'];
											var postSenderLastname= data[i]['userdetails_lastname'];
											status_info = status_info + postSenderFirstname + ' ' + postSenderLastname + '<br/>';
											
										}
									}
								});

								popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover" data-placement="top"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body"><strong>'+status_info+'</strong></div></div>\'>'+going+'</a>';
								
								//pop hover for may be count
								var popoverVariable1;
								var status_info1;

								$.ajax({
									url:'http://localhost:8080/facebook_v01/webapi/EventMemberList/getEventMemberList/'+eventid+'/Maybe',
									type:'POST',
									dataType: 'json',
									async:false,
									success: function(data){
									//alert(data);
										var no_of_object = parseInt(data.length);
										status_info1 = '';
										for( var i=0 ; i < no_of_object ;i++){
											var postSenderFirstname = data[i]['userdetails_firstname'];
											var postSenderLastname= data[i]['userdetails_lastname'];
											status_info1 = status_info1 + postSenderFirstname + ' ' + postSenderLastname + '<br/>';
											
										}
									}
								});
								
								
								popoverVariable1 = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover" data-placement="bottom" data-content=\'<div class="media"><div class="media-body"><strong>'+status_info1+'</strong></div></div>\'>'+maybe+'</a>';
								
								//pop hover not going count
								var popoverVariable2;
								var status_info2;
			//					alert(post_senderId);
								$.ajax({
									url:'http://localhost:8080/facebook_v01/webapi/EventMemberList/getEventMemberList/'+eventid+'/Cant_go',
									type:'POST',
									dataType: 'json',
									async:false,
									success: function(data){
									//alert(data);
										var no_of_object = parseInt(data.length);
										status_info2 = '';
										for( var i=0 ; i < no_of_object ;i++){
											var postSenderFirstname = data[i]['userdetails_firstname'];
											var postSenderLastname= data[i]['userdetails_lastname'];
											status_info2 = status_info2 + postSenderFirstname + ' ' + postSenderLastname + '<br/>';
											
										}
									}
								});

								popoverVariable2 = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover" data-placement="top" data-content=\'<div class="media"><div class="media-body"><strong>'+status_info2+'</strong></div></div>\'>'+cant+'</a>';
								//alert(popoverVariable);
					           // alert(popoverVariable1);
					           // alert(popoverVariable2);
								//pop hover ends here							

								$('#eventTitle').append('<li class="list-group-item">'+
														'<row><div class="col-md-4">'+
			                              '<a href="#">'+
			                              '<img src="'+eventpicurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=100 height=150/>'+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-8">'+
			                            'Name : '+event_name+
			                            '<br/>'+
			                                  ' <Strong> Location : </Strong>'+location+'<br/>'+
			                                  ' <Strong> Created At:  </Strong>'+start_date+' on '+start_time+'<br/>'+
			                                  ' <Strong> Description : </Strong>'+description+'<br/>'+
			                            '</div></row><br/>'+
			                            '<div class="col-md-10" style="margin-left:100px">'+
			                           
			                                  '<br/>Going :'+ popoverVariable +'&nbsp;&nbsp;'+
			                                  'May Be:'+ popoverVariable1 +' &nbsp;&nbsp;'+
			                                  'Not Going:'+ popoverVariable2 +'&nbsp;'+
			                            '</div>'+
			                            
			                            '<div class="clearfix"></div>'+
			                          '</li>');
							}
							$('[data-toggle="popover"]').popover();

						},
						//<Strong>'+maybe+'</Strong>
						error: function(xhr,status){
							if(xhr.status==202){
								//No friends
								$('#my_events').append('No events available');
							}
							else{
							//	window.location.href = 'errorpage.html';
								$('#my_events').append('No events available');//Something went wrong -> redirect to error page
							}		
						}
					
				});	
			
		}

		function sendInvite(){
			userid = document.getElementById("topNavHiddenPK").value;
			eventid = window.location.search.substr(1).split("=")[1];
			var checkboxValues = [];
			$('input:checkbox[name=invite]').each(function() 
					{    
					    if($(this).is(':checked'))
					    	checkboxValues.push($(this).val());
					});
			checkboxValues.toString();
			//alert(checkboxValues);
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/eventList/sendEventInvite/'+eventid+'/'+userid+'/'+checkboxValues,
				type:'POST',
				dataType: 'json',
				complete : function(request, textStatus, errorThrown){
		        	alert("Invite sent");
		        }
			});
			$('#invitemodal').modal('toggle');
		}
		
		
		

		
		
		
		
		
		
/***** START: Updates for posts in events 18th April 2017 *****/
		
		function fetchUpdates(){
			eventid = window.location.search.substr(1).split("=")[1];	
			$('#discussion').empty();
			if (eventid){
				$.ajax({
					url:'http://localhost:8080/facebook_v01/webapi/eventList/fetchEventUpdates/'+eventid,
					type:'GET',
					dataType: 'json',
					success: function(data){
						var no_of_object = parseInt(data.length);
						for( var i=0 ; i < no_of_object ;i++){
							var name = data[i]['userdetails_firstname'] + " " + data[i]['userdetails_lastname'];
							var post_statusText = data[i]['post_statusText'];
							var sender_profile_pic_url = serverPicLocation + data[i]['userdetails_picurl'] ;
							var timestamp = data[i]['post_date'];
							var time_to_display = calculatePostTimeToDisplay(timestamp);
							$('#discussion').append('<div class="panel panel-default">'+
														'<div class="panel-heading">'+
				                						'<span class="pull-left" style="margin-right:12px; margin-top:-5px;">'+
				                							'<img src="'+sender_profile_pic_url+'"  title="'+name+'" width="40px" height="40px">'+
				                						'</span>'+
				                						'<h4>' + name + '</h4>'+
				                						'<h5>' + time_to_display + '</h5>'+
				                						'</div>' +
				                						'<div class = "panel-body">'+
				                							'<p>'+ post_statusText +'</p>'+
				                						'</div>'+
			                     						'<div class="panel-footer"></div></div>');
							}
						
					},
					error: function(xhr,status){
								
					}
				});
			}
		}
		
		
		function addUpdatePost(){
			eventid = window.location.search.substr(1).split("=")[1];
			userid = document.getElementById("topNavHiddenPK").value;
			if (userid){
				$.ajax({
					url:'http://localhost:8080/facebook_v01/webapi/eventList/addEventUpdates/'+eventid+'/'+userid,
					type:'POST',
					contentType: 'application/json',
					data: JSON.stringify({
						postText:$('#whatsOnYourMind').val(),
						senderId: userid,
						receiverId: userid,
						eventId: eventid,
						post_img_url: 'NULL'
			        }),
			        success: function(data){
			        	fetchUpdates();
			        	$('#whatsOnYourMind').val('');
			        }
				});
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
		
		
		
		
		
		
/***** END: Updates for posts in events 18th April 2017 *****/		