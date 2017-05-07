var userid;	//GET FROM SESSION

function doMyOtherOnloadStuff()
{
	addComponentGroupYouManageList();
}

function addComponentGroupYouManageList(){
	
	userid = document.getElementById("topNavHiddenPK").value;
//	alert(userid);
	
//	$(document).ready(function() {
		
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/groupdetails/discoverGroups/'+userid,
			type:'GET',
			dataType: 'json',
			success: function(data){
							
				var no_of_object = parseInt(data.length);
				var half_no_of_object = parseInt((no_of_object-1)/2);
				
				for( var i=0 ; i <= half_no_of_object ;i++){
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					
					$('#grouplist1').append('<li class="list-group-item">'+
			                            
							'<div class="col-md-4">'+
			                              
			                            //'<a href="http://localhost:8080/facebook_v01/grouppage.html?gpid='+ groupid +'">'+
			                             // '<img src="'+picfullurl+'" alt="GroupPIc" class="img-circle img-thumbnail" onclick="LoadGroup(10);" width=80 height=80/>'+
			                             '<img src="'+picfullurl+'" alt="GroupPIc" class="img-circle img-thumbnail" width=80 height=80/>'+
			                             // '</a>'+
			                            '</div>'+
			                            
			                            '<div class="col-md-4" style="margin-top:20px">'+
			                            
			                            	'<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			               
			                            '</div>'+
			                            
			                            '<div class="col-md-4" style="margin-top:20px">'+
			                            			                            
			                            '<button onclick="joinGroup('+groupid+','+userid+')">+Join</button>'+
			                            '</div>'+
			                            
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					}
				for( var i=half_no_of_object+1 ; i < no_of_object ;i++){
					
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					
					$('#grouplist2').append('<li class="list-group-item">'+
			                            '<div class="col-md-4">'+
			                           // '<a href="http://localhost:8080/facebook_v01/grouppage.html?gpid='+ groupid +'">'+
			                              '<img src="'+picfullurl+'" alt="GroupPIc" class="img-circle img-thumbnail"  width=80 height=80/>'+
			                             // '</a>'+
			                            '</div>'+
			                            
			                            '<div class="col-md-4" style="margin-top:20px">'+
			                                  '<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                            '</div>'+
			                            
			                            '<div class="col-md-4" style="margin-top:20px">'+
			                            
			                            '<button onclick="joinGroup('+groupid+','+userid+')">+Join</button>'+
			                            
			                            '</div>'+
			                            
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					}
			},
			error: function(xhr,status){
				if(xhr.status==202){
					alert("Error");
				}
				else{
					window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
				}		
			}
		});
//	});	
}


/// * FUnction for Join Group *///

function joinGroup(grpid,usrid){
	
	//alert("JOin request for Group "+grpid + " "+usrid);

	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/groupdetails/sendJoinGroupRequest/'+grpid+'/'+usrid,
		type:'POST',
		success: function(data){
//			alert("Join Request Sent");
			$('#grouplist1').empty();
			$('#grouplist2').empty();
			addComponentGroupYouManageList();
		},
		error: function(xhr,status){
			if(xhr.status==202){
				window.location.href = 'errorpage.html';
			}
			else{
				window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
			}
		}
	});
	
	
}

//* For Creating a new Group*/

function createGroupFunction() {
	
		var group_UserId = document.getElementById("topNavHiddenPK").value;
		var group_Name = $('#group_Name').val();
//		var group_People = $('#group_People').val();
		var group_People = 'abc';
		var group_Privacy = $('#group_Privacy').val();
		
		//alert(group_UserId + "  "+group_Name +  "  " + group_People + " "+group_Privacy);
    
        $.ajax({
        	url: 'http://localhost:8080/facebook_v01/webapi/groupdetails/createNewGroup/'+group_UserId+'/'+group_Name+'/'+group_People+'/'+group_Privacy,
    		type:'POST',
    		complete : function(xhr,status){
    			if(xhr.status==201){
    				//alert("Group Created");
    				window.location = "GroupList.html"
    			}
    			else{
    				window.location = "MiscellaneousPages/ErrorPage.html"	//Something went wrong -> redirect to error page
    			}		
    		}
    	
    	});
      
}

//* Function for Create Group Starts here *//