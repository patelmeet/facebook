var userid;
var loggedin_user_profile_pic_full_url;
var post_img;

//============<!-- 2-Apr to add photo to post -->===========================//
function postPicture(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		// alert("I am here");
		reader.onload = function(e) {
			post_img=e.target.result;
			$('#post_picture').attr('src', e.target.result);				
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function uploadPostPhoto(){
	//$('#picturemodal').modal('toggle');
	$('#uploadPhotoModal').modal('toggle');
	$('#postedPhoto').append('<img src="'+post_img+'" width="80px" height="80px"/>');
	//data-dismiss="modal"
}

//Makes the system wait for specified milliseconds.
function wait(ms){
	alert("Please Wait. The Image is being uploaded. You will be automatically redirected.");
	var start = new Date().getTime();
	var end = start;
	while(end < start + ms) {
		end = new Date().getTime();
	}
}


function addPostStatus(){
//	alert('addPOst Called');
	userid = document.getElementById("topNavHiddenPK").value;
	var currentSystemTime = new Date();
	var filename;
	if(document.getElementById("uploadFile").value==''){
		filename='';
		//alert(filename);
	}
	else{
		filename = document.getElementById("uploadFile").value.replace(/^.*\\/, "");
		//alert(filename);
		var createdfilename = (userid+currentSystemTime+filename).split(':').join("-");
//		alert(createdfilename);
		var fd = new FormData($('#postForm')[0]);
		   console.info(fd);
		   
		   if(fd)
			{
				$.ajax({
				type : 'POST',
				url : 'http://localhost:8080/facebook_v01/webapi/events/setEventPicture/'+createdfilename,
				data : fd,
				enctype: 'multipart/form-data',
				       processData: false,  // Important!
				       contentType: false,
				       cache: false,
				
				});
			//create_Event(filename);
			}
	}
	//alert(filename);
	
	
	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/postStatus/'+userid,
		type:'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			postText:$('#whatsOnYourMind').val(),
			senderId: userid,
			receiverId: userid,
			post_img_url:createdfilename
        }),
        success : function(data){
        	$('#newsFeed').empty();
        	$('#postedPhoto').empty();
        	wait(8000);
        	fetchPosts();
        	$('#whatsOnYourMind').val('');
        }
	})
	
}

//============<!-- end 2-Apr to add photo to post -->===========================//

function doMyOtherOnloadStuff()
{
//	alert("Inside doMyOtherOnloadStuff");
	var userFullName = document.getElementById("topNavHiddenFirstname").value + " " + document.getElementById("topNavHiddenLastname").value;
	
	document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;		//Set Name
	document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;	//Set Profile Pic
	loggedin_user_profile_pic_full_url = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
	
	fetchPosts();
	fetchSportsNews();
//	setInterval("fetchPosts()",60000);
}

function fetchPosts(){
	userid = document.getElementById("topNavHiddenPK").value;
	
	var postSenderDob = "";
	var postSenderEmail = "";

	$.ajax({
			url:'http://localhost:8080/facebook_v01/webapi/post/getNewsfeedPosts/'+userid,
			type:'GET',
			async : false,
			dataType: 'json',
			success: function(data){
				var no_of_object = data.length;
				$('#newsFeed').empty();
				for( var i=0; i < no_of_object; i++){
					
					var post_id = data[i]['post_pk'];
					var post_text = data[i]['post_statusText'];
					var post_senderId = data[i]['post_senderId'];
					var sender_name = data[i]['userdetails_firstname']+" "+data[i]['userdetails_lastname'];
					var sender_profile_pic_url = serverPicLocation + data[i]['userdetails_picurl'] ;
					var post_like_count = data[i]['post_like_count'];				//For Like
					var isliked = isPostLikedByUserFunction(userid,post_id);		//For Like
					var post_img_url=data[i]['post_img_url'];
					//alert(post_img_url);
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
                		'<img src="'+sender_profile_pic_url+'"  title="'+sender_name+'" alt="'+sender_name+'" width="40px" height="40px">'+
                     '</span>'+
              '<h4>'+ popoverVariable +'</h4>'+
              '<h5>'+ time_to_display +'</h5>'+
              '</div>';
					if(post_img_url==='null'){
						element+='<div class="panel-body">'+
		                '<p>'+ post_text +'</p>'+
		                '<hr style="margin-bottom:3px;">';
					}
					else{
						//alert('before: '+post_img_url);
						post_img_url='eventPictures/' + post_img_url;
						//alert('after: '+post_img_url);
						element+='<div class="panel-body">'+
		                '<p>'+ post_text +'<br/>'+'</p>'
		                +'<img src="'+post_img_url+'" width="150px" height="150px" />'+
		                '<hr style="margin-bottom:3px;">';
					}
					
					
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
					
					
					//Code for Hover on Likes End
					
					
					
					
					/*
					if(isliked=='true')
						element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:blue;margin-right:5px"></span><strong style="color:blue">Like</strong></a>';
					else
						element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:gray;margin-right:5px"></span><strong>Like</strong></a>';
					element = element + '<a style=" cursor:pointer;"><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span><strong>Comment</strong></a>'+
				              '</div>'+
				              '<div class="panel-footer" style="margin-top:-8px">'+
				              '<span class="pull-left" style="margin-right:10px; margin-top:-5px;" id="'+post_id+'-count" value="'+post_like_count+'">'+ post_like_count +' likes </span><br/>';
//					alert('http://localhost:8080/facebook_v01/webapi/post/getCommentList/'+post_id);
*/					
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
					                    '<img src="'+serverPicLocation + data[j]['userdetails_picurl']+'"  title="'+sender_name+'" alt="John Doe" width="15px" height="15px">'+
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
				                  '<textarea style="width:425px;height:30px;margin-top:-5px" placeholder="Write a Comment..." id="'+post_id+'" onkeypress="commentonkeypressfunction(event);" ></textarea>'+
				              '</div>'+
				            '</div>';
					
					$('#newsFeed').append(element);
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
				$('#newsFeed').empty();
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
				$('#newsFeed').empty();
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
					$('#newsFeed').empty();
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



/****** Function Used for News **********/
function fetchSportsNews()
{
	$.ajax({
        url: "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=89b5eb1576114533bb81f5a4307cc15d",
        type: 'GET',
        dataType: 'json',
        success: function(resp) {
          console.log(resp);
          $('#mainPageContent').hide();
//          alert(resp.articles.length);
          var element='';
          for(var i=0;i<resp.articles.length;i++){
            element += '<div>';
            element += '<img src="'+resp.articles[i].urlToImage+'" alt="" height="60" width="60" ALIGN="left">';
            element += '<font size="-1">'+resp.articles[i].title+'</font><br>'+
                       '<font size="-2">'+resp.articles[i].description+'</font><br>'+
                       '<a href="'+resp.articles[i].url+'">Click for More..</a><br><br><br>';
            element+='</div>';
          }
          $('#external_service').empty();
          $('#external_service').append(element);
         },
        error: function(err) {
          console.log(err);
        }
      });
}