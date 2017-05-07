var userid;	//GET FROM SESSION

function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	addComponentGroupYouManageList();
	addComponentYourGroupList();
}

function addComponentGroupYouManageList(){
	
	userid = document.getElementById("topNavHiddenPK").value;
//	alert(userid);
	
//	$(document).ready(function() {
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getAllGroupsDetailYouManage/'+userid,
			type:'GET',
			dataType: 'json',
			async: false,
			success: function(data){
				
				
				
				var no_of_object = parseInt(data.length);
				var half_no_of_object = parseInt((no_of_object-1)/2);
				var popoverVariable;
				
				for( var i=0 ; i <= half_no_of_object ;i++){
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body">';
					//	alert(name);
						$.ajax({
							url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getMemberList/'+groupid,
							type:'GET',
							dataType: 'json',
							async: false,
							success: function(data){
								var no = parseInt(data.length);
								//alert(data);
								//length=parseInt(ret.length);
								for(var j=0;j<no;j++){
									popoverVariable+=data[j]['userdetails_firstname']+'&nbsp;'+data[j]['userdetails_lastname']+'<br/>';
								}
							},
							error: function(xhr,status){
								alert("In error");
								alert(xhr.status);
								if(xhr.status==202){
									//No friends
									alert('error');
								}
								else{
									alert('error:210');
									//window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
								}	
						}
						});
						
						popoverVariable+='</div></div>\'>'+'<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+'</a>';
						
					$('#grouplist1').append('<li class="list-group-item">'+
			                            
							'<div class="col-md-6">'+
			                              
							popoverVariable+
			                            '</div>'+
			     
			                            
			                            '<div class="col-md-3" style="margin-top:20px">'+
			               	
			                            '<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                            
			                            '</div>'+
			                            
			                            '<div class="col-md-3" style="margin-top:20px">'+
			                            '<button class="btn btn-primary" type="button" id="'+groupid+'" onclick="DeleteGroup('+groupid+')">Delete</button>'+'&nbsp;'+
		
			                            
			                            '</div>'+
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					$('[data-toggle="popover"]').popover();
					}
				for( var i=half_no_of_object+1 ; i < no_of_object ;i++){
					
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body">';
					//	alert(name);
						$.ajax({
							url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getMemberList/'+groupid,
							type:'GET',
							dataType: 'json',
							async: false,
							success: function(data){
								var no = parseInt(data.length);
								//alert(data);
								//length=parseInt(ret.length);
								for(var j=0;j<no;j++){
									popoverVariable+=data[j]['userdetails_firstname']+'&nbsp;'+data[j]['userdetails_lastname']+'<br/>';
								}
							},
							error: function(xhr,status){
								alert("In error");
								alert(xhr.status);
								if(xhr.status==202){
									//No friends
									alert('error');
								}
								else{
									alert('error:210');
									//window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
								}	
						}
						});
						
						popoverVariable+='</div></div>\'>'+'<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+'</a>';
						
					
					$('#grouplist2').append('<li class="list-group-item">'+
			                            '<div class="col-md-6">'+
			                            popoverVariable+
			                            '</div>'+
			                            
			                            '<div class="col-md-3" style="margin-top:20px">'+
			                                  '<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                            '</div>'+
			                            
			                            '<div class="col-md-3" style="margin-top:20px">'+
			                            '<button class="btn btn-primary" type="button" id="'+groupid+'" onclick="DeleteGroup('+groupid+')">Delete</button>'+'&nbsp;'+
		
			                            
			                            '</div>'+
			                            
			                            
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					}
				$('[data-toggle="popover"]').popover();
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
//	});	
}


/* Function for Your Group */


function addComponentYourGroupList(){
	
	userid = document.getElementById("topNavHiddenPK").value;

//	alert(userid);
	
	$(document).ready(function() {
		$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getAllGroupsYourGroup/'+userid,
			type:'GET',
			dataType: 'json',
			success: function(data){
				
				
				
				var no_of_object = parseInt(data.length);
				var half_no_of_object = parseInt((no_of_object-1)/2);
				var popoverVariable;
				
				for( var i=0 ; i <= half_no_of_object ;i++){
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body">';
					//	alert(name);
						$.ajax({
							url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getMemberList/'+groupid,
							type:'GET',
							dataType: 'json',
							async: false,
							success: function(data){
								var no = parseInt(data.length);
								//alert('gunjan');
								//length=parseInt(ret.length);
								for(var j=0;j<no;j++){
									popoverVariable+=data[j]['userdetails_firstname']+'&nbsp;'+data[j]['userdetails_lastname']+'<br/>';
								}
							},
							error: function(xhr,status){
								alert("In error");
								alert(xhr.status);
								if(xhr.status==202){
									//No friends
									alert('error');
								}
								else{
									alert('error:210');
									//window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
								}	
						}
						});
						
						popoverVariable+='</div></div>\'>'+'<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+'</a>';
						
					$('#grouplist3').append('<li class="list-group-item">'+
			                            '<div class="col-md-6">'+
			                            popoverVariable+
			                            '</div>'+
			                            
			                            '<div class="col-md-6" style="margin-top:20px">'+
			                                  '<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                            '</div>'+
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					}
				for( var i=half_no_of_object+1 ; i < no_of_object ;i++){
					
					var name = data[i]['groupdetails_name'];
					var groupid = data[i]['groupdetails_id'];
					var picfullurl = serverPicLocation + data[i]['groupdetails_imageurl'];
					popoverVariable = '<a style="color:#3b5998" href="#" data-html="true" class="x" data-toggle="popover"  data-trigger="hover"  data-content=\'<div class="media"><div class="media-body">';
					//	alert(name);
						$.ajax({
							url:'http://localhost:8080/facebook_v01/webapi/groupdetails/getMemberList/'+groupid,
							type:'GET',
							dataType: 'json',
							async: false,
							success: function(data){
								//alert("In success");
								var no = parseInt(data.length);
								//alert('hello');
								//alert(data);
								//length=parseInt(ret.length);
								for(var j=0;j<no;j++){
									popoverVariable+=data[j]['userdetails_firstname']+'&nbsp;'+data[j]['userdetails_lastname']+'<br/>';
								}
							},
							error: function(xhr,status){
								alert("In error");
								alert(xhr.status);
								if(xhr.status==202){
									//No friends
									alert('error');
								}
								else{
									alert('error:210');
									//window.location.href = 'errorpage.html';	//Something went wrong -> redirect to error page
								}	
						}
						});
						
						popoverVariable+='</div></div>\'>'+'<img src="'+ picfullurl +'" style="width:50px;height:60px" class="media-object">'+'</a>';
						
					
					$('#grouplist4').append('<li class="list-group-item">'+
			                            '<div class="col-md-6">'+
			                             popoverVariable +
			                            '</div>'+
			                            
			                            '<div class="col-md-6" style="margin-top:20px">'+
			                                  '<a  class="name"  href="GroupMainPage.html?gpid='+ groupid +'" color="black"><Strong>'+name+'</Strong></a><br/>'+
			                            '</div>'+
			                            	'<div class="clearfix"></div>'+
			                          '</li>');
					$('[data-toggle="popover"]').popover();
					}
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
	});	
}

/*Function for Your Groups Ends here */


DeleteGroup

///

function DeleteGroup(grpid)
{	
	//alert("Hello ConfirmFriendRequest " + userpk + " : "+grpid);
	
	//userid = document.getElementById("topNavHiddenPK").value;
		
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/groupdetails/deleteGroup/'+grpid,
		type:'POST',
		success: function(data){
			
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



//* For Creating a new Group*/

function createGroupFunction() {
	
		var group_UserId = document.getElementById("topNavHiddenPK").value;
		var group_Name = $('#group_Name').val();
//		var group_People = $('#group_People').val();
		var group_People = "abc";
		var group_Privacy = $('#group_Privacy').val();
		
		//alert(group_UserId + "  "+group_Name +  "  " + group_People + " "+group_Privacy);
    
        $.ajax({
        	url: 'http://localhost:8080/facebook_v01/webapi/groupdetails/createNewGroup/'+group_UserId+'/'+group_Name+'/'+group_People+'/'+group_Privacy,
    		type:'POST',
    		complete : function(xhr,status){
    			if(xhr.status==201){
    				//alert("hello world second ");
    				window.location = "GroupList.html"
    			}
    			else{
    				window.location = "MiscellaneousPages/ErrorPage.html"	//Something went wrong -> redirect to error page
    			}		
    		}
    	
    	});
      
}



//* Function for Create Group Ends here *//

