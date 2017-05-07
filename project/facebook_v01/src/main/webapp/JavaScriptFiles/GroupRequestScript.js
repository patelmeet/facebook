
var userid;	//GET FROM SESSION
function doMyOtherOnloadStuff(){
	//alert("JOin REquest");
	addComponentPendingRequests();
}



function addComponentPendingRequests(){	
	userid = document.getElementById("topNavHiddenPK").value;
	
//	$(document).ready(function() {
		
		groupid = getUrlParameter("gpid");
		
		//alert("Group Id is " + groupid);
		
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
					
					
					$('#pendingJoinGroupRequests').append('<li class="list-group-item">'+
			                            '<div class="col-md-2">'+
			                              '<a href="#">'+
			                              '<img src="'+picfullurl+'"  class="circle img-thumbnail img-box"  width=80 height=80/>'+
			                              '</a>'+
			                            '</div>'+
			                            '<div class="col-md-3">'+
			                                  '<a  class="name"  href="#" color="black"><Strong>'+fname+'</Strong></a><br/>'+
			               
			                            '</div>'+
			                            '<div class="col-md-7" id = "temp">'+
			                            '<button class="btn btn-primary" type="button" id="'+pk+'" onclick="ConfirmGroupRequest('+pk+','+groupid+')">Confirm</button>'+'&nbsp;'+
			                            '<button class="btn btn-friends-primary" type="button"id="'+ pk+'" onclick="DeclineGroupRequest('+pk+','+groupid+')">Decline Request</button>'+
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

function ConfirmGroupRequest(userpk,grpid)
{
	
	
	//alert("Hello ConfirmFriendRequest " + userpk + " : "+grpid);
	
	
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