var userid;
var loggedin_user_profile_pic_full_url;
var serverPicLocation = "UserProfilePictures/";
function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	
	$( "#eventstartdate" ).datepicker({dateFormat: "yy-mm-dd"});
    $( "#eventenddate" ).datepicker({dateFormat: "yy-mm-dd" ,  });
    
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	
	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;	//Set Profile Pic
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	getPendingInvites();
	displayEvent();
	displayPastEvent();
}

function pictureChange(input) {
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    //alert("I am here");
	    reader.onload = function (e) {
	      $('#event_picture')
	        .attr('src', e.target.result);
	        
	    };
	    reader.readAsDataURL(input.files[0]);
	  }
	}


function create_Event(filename){
	//alert("in event");
	console.log("in event");
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/eventList/new/'+userid,
		type:'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			event_name:$('#eventname').val(),
			event_location:$('#eventlocation').val(),
			event_start_date:$('#eventstartdate').val(),
			event_start_time:$('#eventstarttime').val(),
			event_end_date:$('#eventenddate').val(),
			event_end_time:$('#eventendtime').val(),
			event_description:$('#eventdescription').val(),
			event_photo:filename
			
        }),
        complete : function(request, textStatus, errorThrown){
        	//alert("xxxxxxxxxxx");
        }
	});
        	
}
	
	function addEventPhoto(){
		userid = document.getElementById("topNavHiddenPK").value;
		var currentSystemTime = new Date();
		var filename = document.getElementById("uploadFile").value.replace(/^.*\\/, "");
		//alert(filename);
		var createdfilename = (userid+currentSystemTime+filename).split(':').join("-");
		//alert(createdfilename);
		var fd = new FormData($('#eventform')[0]);
		   console.info(fd);
		if(fd)
		{
			$.ajax({
			type : 'POST',
			url : 'http://localhost:8080/facebook_v01/webapi/events/setEventPicture/'+filename,
			data : fd,
			enctype: 'multipart/form-data',
			       processData: false,  // Important!
			       contentType: false,
			       cache: false,
			
			});
		create_Event(filename);
		}
}
	
	function displayEvent(){
		userid = document.getElementById("topNavHiddenPK").value;
		$(document).ready(function() {
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/eventList/getAllEventDetail/'+userid,
				type:'POST',
				dataType: 'json',
				success: function(data){
					
					var no_of_object = data.length;
					for( var i=0 ; i < no_of_object ;i++){
						var event_pk = data[i]['event_pk'];
						var event_name = data[i]['event_name'];
						var location = data[i]['event_location'];
						var start_date = data[i]['event_start_date'];
						var start_time = data[i]['event_start_time'];
						var description = data[i]['event_description'];
						var eventpicurl = "eventPictures/" + data[i]['event_photo'];
						//alert(event_name);
						//var user = data[i]['userdetails_name'];
						$('#my_events').append('<li class="list-group-item">'+
								'<div class="col-md-4">'+
	                              '<a href="#">'+
	                              '<img src="'+eventpicurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=100/>'+
	                              '</a>'+
	                            '</div>'+
	                            '<div class="col-md-8">'+
	                            'Name : <a href="EventParticular.html?refId='+event_pk+'" style="color:#3B5998">'+event_name+
	                            '</a><br/>'+
	                                  'Location :<Strong>'+location+'</Strong><br/>'+
	                                  'Created At:<Strong> '+start_date+'  '+start_time+' </Strong><br/>'+
	                                  '<Strong>'+description+'</Strong><br/>'+
	                            '</div>'+

	                            '<div class="clearfix"></div>'+
	                          '</li>');
					}
				},
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
		});	
			
	}
	
	function displayPastEvent(){
		userid = document.getElementById("topNavHiddenPK").value;
		$(document).ready(function() {
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/eventList/getPastEventDetail/'+userid,
				type:'POST',
				dataType: 'json',
				success: function(data){
					
					var no_of_object = data.length;
					for( var i=0 ; i < no_of_object ;i++){
						var event_pk = data[i]['event_pk'];
						var event_name = data[i]['event_name'];
						var location = data[i]['event_location'];
						var start_date = data[i]['event_start_date'];
						var start_time = data[i]['event_start_time'];
						var description = data[i]['event_description'];
						var eventpicurl = "eventPictures/" + data[i]['event_photo'];
						//alert(event_name);
						//var user = data[i]['userdetails_name'];
						$('#past_events').append('<li class="list-group-item">'+
								'<div class="col-md-4">'+
	                              '<a href="#">'+
	                              '<img src="'+eventpicurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=100/>'+
	                              '</a>'+
	                            '</div>'+
	                            '<div class="col-md-8">'+
	                            'Name : <a href="PastEvent.html?refId='+event_pk+'" style="color:#3B5998">'+event_name+
	                            '</a><br/>'+
	                                  'Location :<Strong>'+location+'</Strong><br/>'+
	                                  'Created At:<Strong> '+start_date+'  '+start_time+' </Strong><br/>'+
	                                  '<Strong>'+description+'</Strong><br/>'+
	                            '</div>'+

	                            '<div class="clearfix"></div>'+
	                          '</li>');
					}
				},
				error: function(xhr,status){
					if(xhr.status==202){
						//No friends
						$('#past_events').append('No events available');
					}
					else{
					//	window.location.href = 'errorpage.html';
						$('#past_events').append('No events available');//Something went wrong -> redirect to error page
					}		
				}
			});
		});	
			
	}
	
	function getPendingInvites(){
		userid = document.getElementById("topNavHiddenPK").value;
		//alert(userid);
		$(document).ready(function() {
			$.ajax({
				url:'http://localhost:8080/facebook_v01/webapi/eventList/getPendingInvites/'+userid,
				type:'POST',
				dataType: 'json',
				success: function(data){				
				  
					
					var no_of_object = data.length;
				//	alert(no_of_object);
					if(no_of_object>0){
						$('#eventInvite').empty();
						for( var i=0 ; i < no_of_object ;i++){
							var event_pk = data[i]['event_pk'];
							var event_name = data[i]['event_name'];
							var location = data[i]['event_location'];
							var name = data[i]['userdetails_firstname']+" "+data[i]['userdetails_lastname'];
							var eventpicurl = "eventPictures/" + data[i]['event_photo'];
							var eventlistpk = data[i]['eventlist_pk'];
							var event_status = data[i]['eventlist_status'];
							//alert(eventlistpk);
							//alert(event_status);
							var radio='';
							if(event_status == "Maybe"){
                        		radio = radio + '<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Maybe" onchange="radiochange(event,'+eventlistpk+');" checked>May Be</label>'
                        	}
                        	else
                        	{
                        		radio = radio +'<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Maybe" onchange="radiochange(event,'+eventlistpk+');">May Be</label>';
                        	}
							if(event_status == "Going"){
								radio = radio +'<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Going" onchange="radiochange(event,'+eventlistpk+');" checked>Going</label>';
                        	}
                        	else
                        	{
                        		radio = radio +'<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Going" onchange="radiochange(event,'+eventlistpk+');">Going</label>';
                        	}
							if(event_status == "Cant_go"){
								radio = radio +'<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Cant_go" onchange="radiochange(event,'+eventlistpk+');" checked>Can\'t go</label>';
                        	}
                        	else
                        	{
                        		radio = radio + '<label class="radio-inline"><input type="radio" name="optradio_'+eventlistpk+'" value="Cant_go" onchange="radiochange(event,'+eventlistpk+');">Can\'t go</label>';
                        	}
							//alert(radio);
							
							$('#eventInvite').append('<li class="list-group-item">'+
									'<div class="col-md-4">'+
		                              '<a href="#">'+
		                              '<img src="'+eventpicurl+'" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=100/>'+
		                              '</a>'+
		                            '</div>'+
		                            '<div class="col-md-8">'+
		                            'Name : <a href="EventNonAdminView.html?refId='+event_pk+'" style="color:#3B5998">'+event_name+'</a><br/>'+		                            
		                                  'Location :<Strong>'+location+'</Strong><br/>'+
		                                  'Hosted By:<Strong> '+name+' </Strong><br/>'+
		                            '<div id="optradio_'+eventlistpk+'">'+ 
			                        radio +
			                        '</div>'+
			                        '</div>'+
		                            '<div class="clearfix"></div>'+
		                          '</li>');
						}
					}
					else{
						$('#eventInvite').append( '<li class="list-group-item">'+
						'<div class="col-md-12">'+
		               ' <center><img src="icons/invites.png" ></center>'+
		               ' <br/><br/>'+
									  
		               ' <div class="col-md- col-md-offset-4 text-left" >'+
		                 ' <div class="row" >'+                    
					
		                          '<span class="media-middle pull-left" style="margin-left:10px"><font size="3" color="grey" face="arial">'+"You are all caught up."+'</font></span>'+
		
		                 ' </div>'+
		                '</div>'+
		             ' </div>'+
		             '<div class="clearfix"></div>'+
                     '</li>');
					}
					
				},
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
		});
	}
	
function radiochange(event,eventlistpk)
{
	alert(event.target.value);
//	alert(eventlistpk);
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/eventList/acceptEventInvite/'+eventlistpk+'/'+event.target.value,
		type:'POST',
		dataType: 'json',
		success : function(data){
			alert("call completed");
		}
	});
	
}

