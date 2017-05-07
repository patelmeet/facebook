//window.onload = addComponentPendingRequests();
//window.onload = addComponentPeopleYouMayKnow();
//window.onload = onLoadFunction()
var userid;	//GET FROM SESSION
function doMyOtherOnloadStuff(){
//	alert("qwerty");
//	$("#topNavigationBar").load("TopNavigationBar.html");
//    topNavigationBarCheckSession();
//	alert("Inside doMyOtherOnloadStuff");
	addComponentPendingRequests();
	addComponentPeopleYouMayKnow();
	addComponentSuggestionsByYourFriends();			// Chetan Sharma - 14/03
}

function addComponentSuggestionsByYourFriends(){
	userid = document.getElementById("topNavHiddenPK").value;
	
		$(document).ready(function() {
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/friends/fetchSuggestions/'+userid,
				type:'POST',
				dataType: 'json',
				async:false,
				success: function(data){
					
					var no_of_object = data.length;
					if(no_of_object == 0){
						$('#suggestions_by_your_friends').append('No suggestions available');
					}	
					var element;
					for( var i=0 ; i < no_of_object ;i++){
						
						var suggested_whom = data[i]['suggestion_suggested_whom'];
						var suggestion_pk = data[i]['suggestion_pk'];
						if(suggested_whom){
							$.ajax({
								url:'http://localhost:8080/facebook_v01/webapi/friends/getFriendDetails/'+suggested_whom,
								type:'GET',
								async:false,
								dataType: 'json',
								success: function(data){
										var name = data[0]['userdetails_firstname']+" "+data[0]['userdetails_lastname'];
										var picfullurl = serverPicLocation + data[0]['userdetails_picurl'];
										var total_friends = data[0]['userdetails_friend_count'];
										//alert("Friend Name = " + friendName + " " + "Friend Pic Url = " + friendPicUrl);
										
										element = '<li class="list-group-item">'+
													'<div class="col-md-2">'+
														'<a href="TimeLineFriend.html?refId='+suggested_whom+'">'+
														'<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
														'</a>'+
					                            '</div>'+
					                            '<div class="col-md-5">'+
					                                  '<a  class="name"  href="TimeLineFriend.html?refId='+suggested_whom+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
					                                  '<small> '+total_friends+' Friend </small> <br/>';
									}
							});
						}
						
						var suggested_by = data[i]['suggestion_suggested_by'];
						if(suggested_by){
							$.ajax({
								url:'http://localhost:8080/facebook_v01/webapi/friends/getFriendDetails/'+suggested_by,
								type:'GET',
								async:false,
								dataType: 'json',
								success: function(data){
										var name = data[0]['userdetails_firstname']+" "+data[0]['userdetails_lastname'];
										
										element += '<small> '+name+' suggested you. </small>'+
											'</div>'+
			                            '<div class="col-md-5" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" name="'+suggested_whom+'" id="'+suggestion_pk+'" onclick="SendFriendRequestFromSuggestion(event)">Add Friend</button>'+'&nbsp;'+
			                            '<button class="btn btn-friends-primary" type="button"id="'+suggestion_pk+'" onclick="ignoreSuggestion(event)">Ignore</button>'+
			                            '</div>'+

			                            '<div class="clearfix"></div>'+
			                          '</li>'; 
									}
							});
						}
						$('#suggestions_by_your_friends').append(element);

					} // for closes
				}
				
			});
	    });
}

function ignoreSuggestion(event){
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/removeSuggestion/'+event.target.id,
		async:false,
		type:'POST',
		success: function(data){
			$('#suggestions_by_your_friends').empty();
			addComponentSuggestionsByYourFriends();
		}
	});
}

function SendFriendRequestFromSuggestion(event){
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/sendFriendRequest/'+userid+'/'+event.target.name,
		type:'POST',
		async:false,
		success: function(data){
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/friends/removeSuggestion/'+event.target.id,
				async:false,
				type:'POST',
				success: function(data){
					$('#suggestions_by_your_friends').empty();
					addComponentSuggestionsByYourFriends();
				}
			});		
		}
	});	
} 


function addComponentPendingRequests(){	
//	alert("Inside addComponentPendingRequests");
	userid = document.getElementById("topNavHiddenPK").value;
	$(document).ready(function() {
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/friends/getPendingRequests/'+userid,
			type:'POST',
			dataType: 'json',
			success: function(data){
//				alert("Inside success");
				var no_of_object = data.length;
				if (no_of_object > 0)
					document.getElementById("friendRequestCount").innerHTML = "Respond To Your " + no_of_object + " Friend Requests";
				else 
					document.getElementById("friendRequestCount").innerHTML = "Respond To Your Friend Requests";
				for( var i=0 ; i < no_of_object ;i++){
					var name = data[i]['friend_name'];
					var total_friends = data[i]['total_friends'];
					var friend_pk = data[i]['friend_pk'];
					var picfullurl = serverPicLocation + data[i]['friend_picture'];
					$('#pending_requests').append('<li class="list-group-item">'+
			                            '<div class="col-md-2">'+
			                              '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
			                              '<img src="'+picfullurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-5">'+
			                                  '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                                  '<small> '+total_friends+' Friend </small>'+
			                            '</div>'+
			                            '<div class="col-md-5" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="ConfirmFriendRequest(event)">Confirm</button>'+'&nbsp;'+
			                            '<button class="btn btn-friends-primary" type="button"id="'+friend_pk+'" onclick="DeleteFriendRequest(event)">Delete Request</button>'+
			                            '</div>'+

			                            '<div class="clearfix"></div>'+
			                          '</li>');
					}
			},
			error: function(xhr,status){
				
				if(xhr.status==202){
					$('#pending_requests').append('No pending requests');
					document.getElementById("friendRequestCount").innerHTML = "Respond To Your Friend Requests";
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
    });
}

function ConfirmFriendRequest(event)
{
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/acceptFriendRequest/'+userid+'/'+event.target.id,
		type:'POST',
		success: function(data){
			$('#pending_requests').empty();
			addComponentPendingRequests();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}		
		}
	});
}

function DeleteFriendRequest(event)
{
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/deleteFriendRequest/'+userid+'/'+event.target.id,
		type:'POST',
		success: function(data){
			$('#pending_requests').empty();
			addComponentPendingRequests();
			$('#peple_you_may_know').empty();
			addComponentPeopleYouMayKnow();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}		
		}
	});
}




function addComponentPeopleYouMayKnow(){
	userid = document.getElementById("topNavHiddenPK").value;
	$('#peple_you_may_know').empty();
	var selectedbook = $('#selectbookid').val();
	$(document).ready(function() {
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/friends/getFriendSuggestion/'+userid,
			type:'POST',
			async:false,
			dataType: 'json',
			success: function(data){
				var no_of_object = data.length;
				var popoverVariable;
				var continueflow=0;
				for( var i=0 ; i < no_of_object ;i++){
					continueflow=0;
					var name = data[i]['friend_name'];
					var total_friends = data[i]['total_friends'];
					var friend_pk = data[i]['friend_pk'];
					var picfullurl = serverPicLocation + data[i]['friend_picture'];
					$.ajax({
						url:'http://localhost:8080/facebook_v01/webapi/friends/getFriendDetails/'+friend_pk,
						type:'POST',
						async:false,
						dataType: 'json',
						success: function(subdata){
//							alert("inside success");
							var book=subdata[0]['userdetails_book'];
							var dob=subdata[0]['userdetails_dob'];
							popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body"><a href="#" class="pull-left">';
							popoverVariable += 'Name : '+name+'<br> DOB : '+dob+'<br>';
							
							if(book){
								popoverVariable+='BookRead : '+book;
								if(book==selectedbook)
									continueflow=1;
							}
							else{
								continueflow=0;
							}
							if(selectedbook=='All')
								continueflow=1;
						}
					});
					popoverVariable+='</div></div>\'>'+'<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+'</a>';
//					alert(popoverVariable)
					if(continueflow==0)
						continue;
					$('#peple_you_may_know').append('<li class="list-group-item">'+
			                            '<div class="col-md-2">'+
			                              '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
			                              popoverVariable+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-5">'+
			                                  '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                                  '<small> '+total_friends+' Friend </small>'+
			                            '</div>'+
			                            '<div class="col-md-5" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="SendFriendRequest(event)">Add Friend</button>'+'&nbsp;'+
//			                            '<button class="btn btn-friends-primary" type="button"id="'+friend_pk+'" onclick="DeleteFriendRequest(event)">Delete Request</button>'+
			                            '</div>'+

			                            '<div class="clearfix"></div>'+
			                          '</li>');
					$('[data-toggle="popover"]').popover();
					}
			},
			error: function(xhr,status){
				if(xhr.status==202){
					//No friends
					$('#peple_you_may_know').append('No suggestions available');
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
    });
}

function SendFriendRequest(event)
{
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/sendFriendRequest/'+userid+'/'+event.target.id,
		type:'POST',
		success: function(data){
			$('#peple_you_may_know').empty();
			addComponentPeopleYouMayKnow();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}		
		}
	});
}

/*//--------------CHAT FUNCTIONS-------------

Array.remove = function(array, from, to) {
    var rest = array.slice((to || from) + 1 || array.length);
    array.length = from < 0 ? array.length + from : from;
    return array.push.apply(array, rest);
};
       
//this variable represents the total number of popups can be displayed according to the viewport width
var total_popups = 0;

//arrays of popups ids
var popups = [];

//this is used to close a popup
function close_popup(id)
{
    for(var iii = 0; iii < popups.length; iii++)
    {
        if(id == popups[iii])
        {
            Array.remove(popups, iii);
           
            document.getElementById(id).style.display = "none";
           
            calculate_popups();
           
            return;
        }
    }  
}

//displays the popups. Displays based on the maximum number of popups that can be displayed on the current viewport width
function display_popups()
{
    var right = 220;
   
    var iii = 0;
    for(iii; iii < total_popups; iii++)
    {
        if(popups[iii] != undefined)
        {
            var element = document.getElementById(popups[iii]);
            element.style.right = right + "px";
            right = right + 320;
            element.style.display = "block";
        }
    }
   
    for(var jjj = iii; jjj < popups.length; jjj++)
    {
        var element = document.getElementById(popups[jjj]);
        element.style.display = "none";
    }
}

//creates markup for a new popup. Adds the id to popups array.
function register_popup(id, name)
{
   
    for(var iii = 0; iii < popups.length; iii++)
    {  
        //already registered. Bring it to front.
        if(id == popups[iii])
        {
            Array.remove(popups, iii);
       
            popups.unshift(id);
           
            calculate_popups();
           
           
            return;
        }
    }              
   
    var element = '<div class="popup-box chat-popup" id="'+ id +'">';
    element = element + '<div class="popup-head">';
    element = element + '<div class="popup-head-left">'+ name +'</div>';
    element = element + '<div class="popup-head-right"><a href="javascript:close_popup(\''+ id +'\');">&#10005;</a></div>';
    element = element + '<div style="clear: both"></div></div><div class="popup-messages"></div></div>';
   
    document.getElementsByTagName("body")[0].innerHTML = document.getElementsByTagName("body")[0].innerHTML + element; 

    popups.unshift(id);
           
    calculate_popups();
   
}

//calculate the total number of popups suitable and then populate the toatal_popups variable.
function calculate_popups()
{
    var width = window.innerWidth;
    if(width < 540)
    {
        total_popups = 0;
    }
    else
    {
        width = width - 200;
        //320 is width of a single popup box
        total_popups = parseInt(width/320);
    }
   
    display_popups();
   
}*/

//shachi - 17 april starts
function addComponentsearchFriends()
{
	userid = document.getElementById("topNavHiddenPK").value;
	var searchData =JSON.stringify({
        
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        homeTown: document.getElementById("homeTown").value,
        currentCity: document.getElementById("currentCity").value,
        school: document.getElementById("school").value,
        college: document.getElementById("college").value,
        company: document.getElementById("company").value,
        
    }); 
	//alert(searchData);
	
      //  url: 'http://localhost:8080/facebook_v01/webapi/friends/searchFriends/getSearchFriends/'+userid,
	$(document).ready(function() {
		$.ajax({
			url: 'http://localhost:8080/facebook_v01/webapi/searchFriends/getSearchFriends/'+userid,
			type:'POST',
			async:false,
			contentType: 'text/plain',
			dataType: 'json',
			
			data: searchData,
			success: function(data){
				var no_of_object = data.length;
				
				for( var i=0 ; i < no_of_object ;i++){
					
					var name = data[i]['userdetails_firstname'];
					name = name +" "+ data[i]['userdetails_lastname'];
					var total_friends = data[i]['userdetails_friend_count'];
					var friend_pk = data[i]['userdetails_pk'];
					var picfullurl = serverPicLocation + data[i]['userdetails_picurl'];
				
					$('#searchFriends').empty();
					$('#search_friends').append('<li class="list-group-item">'+
			                            '<div class="col-md-2">'+
			                              '<a href="TimeLineFriend.html?refId='+friend_pk+'">'+
			                             '<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-5">'+
			                                  '<a  class="name"  href="TimeLineFriend.html?refId='+friend_pk+'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                                  '<small> '+total_friends+' Friend </small>'+
			                            '</div>'+
			                            '<div class="col-md-5" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="SendFriendRequest(event)">Add Friend</button>'+'&nbsp;'+
//			                            '<button class="btn btn-friends-primary" type="button"id="'+friend_pk+'" onclick="DeleteFriendRequest(event)">Delete Request</button>'+
			                            '</div>'+

			                            '<div class="clearfix"></div>'+
			                          '</li>');
					//$('[data-toggle="popover"]').popover();
					}
			},
			error: function(xhr,status){
				if(xhr.status==202){
					//No friends
					$('#searchFriends').empty();
					$('#search_friends').append('No suggestions available');
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
    });

}


// ends 17 april



//recalculate when window is loaded and also when window is resized.
window.addEventListener("resize", calculate_popups);
window.addEventListener("load", calculate_popups);