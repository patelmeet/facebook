var userid;	//GET FROM SESSION
var query;
function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	addComponentSearch();
}

function addComponentSearch(){
	userid = document.getElementById("topNavHiddenPK").value;
	searchstring = window.location.search.substr(1);
	var splittedsearchstring = searchstring.split("%20");
	//alert(splittedsearchstring + "in search");
    $(document).ready(function() {
        $.ajax({
                url:'http://localhost:8080/facebook_v01/webapi/people/getsearchresults/'+userid+'/'+splittedsearchstring,
                type:'GET',
                dataType: 'json',
                success: function(data){
    //              alert("Inside success");
                    var no_of_object = data.length;
                    for( var i=0 ; i < no_of_object ;i++){
                        var name = data[i]['friend_name'];
                        var total_friends = data[i]['total_friends'];
                        var friend_pk = data[i]['friend_pk'];
                        var picfullurl = serverPicLocation + data[i]['friend_picture'];
                        var friend_status = data[i]['friend_status'];
                        //alert(typeof(friend_status));
                        if(friend_status==='Requested')
                        {
                            $('#peopleSearch').append('<li class="list-group-item">'+
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
                        else if(friend_status==='Pending')
                        {
                            $('#peopleSearch').append('<li class="list-group-item">'+
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
                                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="DeleteFriendRequest(event)">Cancel Request</button>'+'&nbsp;'+
                                            '</div>'+

                                            '<div class="clearfix"></div>'+
                                          '</li>');
                        }
                        else if(friend_status==='Friends')
                        {
                            $('#peopleSearch').append('<li class="list-group-item">'+
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
                                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="RemoveFriend(event)">Unfriend</button>'+'&nbsp;'+
                                            '</div>'+

                                            '<div class="clearfix"></div>'+
                                          '</li>');   
                        }
                        else
                        {
                            $('#peopleSearch').append('<li class="list-group-item">'+
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
                                            '<button class="btn btn-primary" type="button" id="'+friend_pk+'" onclick="SendFriendRequest(event)">Add Friend</button>'+'&nbsp;'+
                                            '</div>'+

                                            '<div class="clearfix"></div>'+
                                          '</li>');
                        }
                    } 
                },
                error: function(xhr,status){
                    
                    if(xhr.status==202){
                        $('#peopleSearch').append('No Search Found');
                    }
                    else{
                    	$('#peopleSearch').append('Something Went wrong');   //Something went wrong -> redirect to error page
                    }       
                }
        });
    });
}


function RemoveFriend(event)
{
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/friends/deleteFriend/'+userid+'/'+event.target.id,
		type:'POST',
		success: function(data){
			$('#peopleSearch').empty();
			addComponentSearch();
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

function ConfirmFriendRequest(event)
{
    $.ajax({
        url:'http://localhost:8080/facebook_v01/webapi/friends/acceptFriendRequest/'+userid+'/'+event.target.id,
        type:'POST',
        success: function(data){
            $('#peopleSearch').empty();
            addComponentSearch();
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

function DeleteFriendRequest(event)
{
    $.ajax({
        url:'http://localhost:8080/facebook_v01/webapi/friends/deleteFriendRequest/'+userid+'/'+event.target.id,
        type:'POST',
        success: function(data){
        	$('#peopleSearch').empty();
            addComponentSearch();
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
function SendFriendRequest(event)
{
    $.ajax({
        url:'http://localhost:8080/facebook_v01/webapi/friends/sendFriendRequest/'+userid+'/'+event.target.id,
        type:'POST',
        success: function(data){
            $('#peopleSearch').empty();
            addComponentSearch();
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

//--------------CHAT FUNCTIONS------------

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
               
            }
           
            //recalculate when window is loaded and also when window is resized.
            window.addEventListener("resize", calculate_popups);
            window.addEventListener("load", calculate_popups);
           