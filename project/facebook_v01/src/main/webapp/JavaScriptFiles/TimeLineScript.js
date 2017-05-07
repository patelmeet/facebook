var divs = [ "div1", "div3", "div4", "div5", "div7", "div8" ];
var visibleDivId = null;
var place_pk;

function toggleVisibility(divId) {

	if (visibleDivId === divId) {
		visibleDivId = null;
	} else {
		visibleDivId = divId;
	}
	hideNonVisibleDivs();
}

function hideNonVisibleDivs() {
	var i, divId, div;
	for (i = 0; i < divs.length; i++) {
		divId = divs[i];
		div = document.getElementById(divId);
		if (visibleDivId === divId) {
			div.style.display = "block";
		} else {
			div.style.display = "none";
		}
	}
}

function hideAllDivs() {
	var i, divId, div;
	for (i = 0; i < divs.length; i++) {
		divId = divs[i];
		div = document.getElementById(divId);
		div.style.display = "none";
	}
}

var userid;
var loggedin_user_profile_pic_full_url;
var loggedin_user_cover_pic_full_url;

function doMyOtherOnloadStuff() {
//	$("#timeline").load("MyPosts.html");
	$("#about").load("About.html");
	$("#friends").load("Friends.html");
	var userFullName = document.getElementById("topNavHiddenFirstname").value
			+ " " + document.getElementById("topNavHiddenLastname").value;
	loggedin_user_profile_pic_full_url = serverPicLocation
			+ document.getElementById("topNavHiddenProfilePicURL").value;
	document.getElementById("profilePic").src = loggedin_user_profile_pic_full_url;
	
	//shachi cover pic changes starts here 15 march
	
		loggedin_user_cover_pic_full_url = serverPicLocation
		+ document.getElementById("topNavHiddenCoverPicURL").value;
		//alert(loggedin_user_cover_pic_full_url);
	document.getElementById("coverPic").src = loggedin_user_cover_pic_full_url;

	//shachi changes end 15 march
	
	document.getElementById("nameFooter").innerHTML = userFullName;
	fetchPosts();
//	setInterval("fetchPosts()", 60000);
	fetchPreviousWorkData();
	fetchPreviousCollegeData();
	fetchPreviousSchoolData();
	fetchPreviousPlacesData();
	addComponentFriendList();
	fetchContactAndBasicInfo();
	fetchCurrentPassword();
//	fetchbook();
//	updatebook();
	
	//document.getElementById("ViewPersonalInfoUserFullNameFontTag").innerHTML = userFullName;
	//document.getElementById("ViewPersonalInfoUserProPic").src = serverPicLocation + document.getElementById("topNavHiddenProfilePicURL").value;
}
//Functions to fetch and update password - Chetan Sharma @14-04-2017-------------------------------------------------
function fetchCurrentPassword() {
	var userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/fetchOldPassword/'+ userid,
					type : 'GET',
					dataType : 'json',
					success : function(data) {
							var currentPassword = data[0]['userdetails_password'];
							document.getElementById("currentPassword").innerHTML = currentPassword;
					}
				})
	}
}

function updatePassword() {
	var userid = document.getElementById("topNavHiddenPK").value;
	var newPassword = $('#newPassword').val();
	if (userid) {
		$.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/setNewPassword/'+userid+'/'+newPassword,
					type : 'GET',
					contentType : 'application/json',
					data : function(data){
					},
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						$('#newPassword').val('');
						$('#editPassword').hide();
						fetchCurrentPassword();
					}
				});
	}
}
//Functions to fetch and update password - Chetan Sharma @14-04-2017 ends here-------------------------------------------------

function updatebook()
{
	userid = document.getElementById("topNavHiddenPK").value;
	var selectedbook = $('#selectbookid').val();
//	alert('http://localhost:8080/facebook_v01/webapi/profile/updatebook/'+userid+'/'+selectedbook);
	$.ajax({
		url : 'http://localhost:8080/facebook_v01/webapi/profile/updatebook/'+userid+'/'+selectedbook,
		type : 'POST',
		async : false,
		complete : function(request, textStatus, errorThrown) {
			
		}
	});
}

function fetchbook()
{
	userid = document.getElementById("topNavHiddenPK").value;
	$.ajax({
		url : 'http://localhost:8080/facebook_v01/webapi/profile/getbook/'+userid,
		type : 'POST',
		async : false,
		success : function(data) {
			if(data[0]['userdetails_book'])
				document.getElementById('selectbookid').value=data[0]['userdetails_book'];
		},
		error : function(xhr,status){
//			alert("Inside Error");
		}
	});
}

function pictureChange(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		// alert("I am here");
		reader.onload = function(e) {
			$('#profile_picture').attr('src', e.target.result);
			
						
		};
		reader.readAsDataURL(input.files[0]);
	}
}

//shachi cover pic 15 march

function coverpictureChange(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		// alert("I am here");
		reader.onload = function(e) {
			
			$('#cover_picture').attr('src', e.target.result);
			
		};
		reader.readAsDataURL(input.files[0]);
	}
}
//ends

function changeProfilePic() {
	userid = document.getElementById("topNavHiddenPK").value;
	// alert("qwewrty0 event");
	var modalform2 = $('#pictureForm');
	var fd;
	fd = new FormData($('#pictureForm')[0]);
	console.info(fd);
	if (fd) {
		// alert(fd);
		$.ajax({
					type : 'POST',
					url : 'http://localhost:8080/facebook_v01/webapi/setprofilepicture/picture/'
							+ userid,
					data : fd,
					enctype : 'multipart/form-data',
					async : false,
					processData : false, // Important!
					contentType : false,
					cache : false,
					complete : function(request, textStatus, errorThrown) {
						// alert("completed");
						$.ajax({
							type : 'POST',
							url : 'UpdateSessionServlet',
							async : false,
							complete : function(request, textStatus,
									errorThrown) {
								window.location.href = "TimeLine.html";
							}
						});
					}
				}); // Ajax closes here
	} else
		alert("empty fd");
	wait(6000);
	$('#picturemodal').modal('toggle');
	

}


// Makes the system wait for specified milliseconds.
function wait(ms){
	alert("Please Wait. The Image is being uploaded. You will be automatically redirected.");
	var start = new Date().getTime();
	var end = start;
	while(end < start + ms) {
		end = new Date().getTime();
	}
}







// shachi cover pic ------------------------------------

function changeCoverPic() {
	userid = document.getElementById("topNavHiddenPK").value;
	// alert("qwewrty0 event");
	var modalform2 = $('#coverpictureForm');
	var fd;
	fd = new FormData($('#coverpictureForm')[0]);
	console.info(fd);
	if (fd) {
		// alert(fd);
		$.ajax({
					type : 'POST',
					url : 'http://localhost:8080/facebook_v01/webapi/setcoverpicture/picture/'
							+ userid,
					data : fd,
					enctype : 'multipart/form-data',
					async : false,
					processData : false, // Important!
					contentType : false,
					cache : false,
					complete : function(request, textStatus, errorThrown) {
						// alert("completed");
						$.ajax({
							type : 'POST',
							url : 'UpdateSessionServlet',
							async : false,
							complete : function(request, textStatus,
									errorThrown) {
								window.location.href = "TimeLine.html";
							}
						});
					}
				}); // Ajax closes here
	} else
		alert("empty fd");
	wait(6000);
	$('#coverpicturemodal').modal('toggle');
	

}
//shachi ends--------------------------------------------------------------------

function workPlaceFormSubmit() {
	var userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/work/'
					+ userid + '/set',
			type : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				work_company : $('#work_company').val(),
				work_position : $('#work_position').val(),
				work_city : $('#work_city').val(),
				work_description : $('#work_description').val(),
				work_start_year : 2000,
				work_end_year : 2015,
			}),
			dataType : 'json',
			complete : function(request, textStatus, errorThrown) {
				fetchPreviousWorkData();
			}
		});

	}

	toggleVisibility('div1');
}

function collegeSubmit() {
	var userid = document.getElementById("topNavHiddenPK").value;
	var checkedValue = $('input[name=college_attended_for]').filter(':checked')
			.val();
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/college/new/'
							+ userid,
					type : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						college_name : $('#college_name').val(),
						college_start_year : $('#college_start_year').val(),
						college_end_year : $('#college_end_year').val(),
						college_graduated : 'Y',
						college_description : $('#college_description').val(),
						college_concentration : $('#college_concentration')
								.val(),
						college_attended_for : checkedValue
					}),
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						fetchPreviousCollegeData();
					}
				});
	}
	toggleVisibility('div3');
}






function schoolSubmit() {
	var userid = document.getElementById("topNavHiddenPK").value;
	var checkedValue = $('input[name=college_attended_for]').filter(':checked')
			.val();
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/school/new/'+ userid,
					type : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						school_name : $('#school_name').val(),
						school_start_year : $('#school_start_year').val(),
						school_end_year : $('#school_end_year').val(),
						school_description : $('#school_description').val(),
					}),
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						fetchPreviousSchoolData();
					}
				});
	}
	toggleVisibility('div4');
}

function placesSubmit() {
	var userid = document.getElementById("topNavHiddenPK").value;
	var checkedValue = $('input[name=college_attended_for]').filter(':checked')
			.val();
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/places/new/'
							+ userid,
					type : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						places_current_city : $('#places_current_city').val(),
						places_hometown : $('#places_hometown').val(),
					}),
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						fetchPreviousPlacesData();
					}
				});
	}
	toggleVisibility('div5');
}



function removePreviousWorkData(work_pk){
	if (work_pk) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/work/remove/'+work_pk,
			type : 'GET',
			async: false,
			dataType : 'json'
		});
		
	}
	fetchPreviousWorkData();
}




function fetchPreviousWorkData() {
	userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/work/'+ userid + '/get/',
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				var no_of_object = data.length;	
				$('#workDynamicDiv').empty();
				for (var i = 0; i < no_of_object; i++) {
					var work_company = data[i]['work_company'];
					var work_position = data[i]['work_position'];
					var work_city = data[i]['work_city'];
					var work_start_year = data[i]['work_start_year'];
					var work_end_year = data[i]['work_end_year'];
					var work_pk = data[i]['work_pk'];
					$('#workDynamicDiv').append(
							'<button class = "btn btn-default pull-right" onclick="removePreviousWorkData('+work_pk+')">Remove</button>'+
							'<h3 style="color:#3b5998;font-size:16px">'
									+ work_company + '</h3>'
									+ '<p style="color:#808080">'
									+ work_position + ' in ' + work_city
									+ '</p>' + '<hr style="margin-top: 4px">');
				}
			}
		})
	}
}


function removePreviousCollegeData(college_pk){
	if (college_pk) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/college/remove/'+college_pk,
			type : 'GET',
			async: false,
			dataType : 'json'
		});
		fetchPreviousCollegeData();
	}
}

function fetchPreviousCollegeData() {
	userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/college/'+ userid,
					type : 'GET',
					dataType : 'json',
					success : function(data) {
						var no_of_object = data.length;
						$('#collegeDynamicDiv').empty();
						for (var i = 0; i < no_of_object; i++) {
							var college_pk = data[i]['college_pk'];
							var college_name = data[i]['college_name'];
							var college_start_year = data[i]['college_start_year'];
							var college_end_year = data[i]['college_end_year'];
							var college_concentration = data[i]['college_concentration'];
							$('#collegeDynamicDiv').append(
									'<button class = "btn btn-default pull-right" onclick="removePreviousCollegeData('+college_pk+')">Remove</button>'+
									'<h3 style="color:#3b5998;font-size:16px">'
											+ college_name + '</h3>'
											+ '<p style="color:#808080">'
											+ college_concentration + ' From '
											+ college_start_year + ' to '
											+ college_end_year + '</p>'
											+ '<hr style="margin-top: 4px">');
						}
					}
				});
	}
}


function removePreviousSchoolData(school_pk){
	if (school_pk) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/school/remove/'+school_pk,
			type : 'GET',
			dataType : 'json',
			async:false
		})
	}
	fetchPreviousSchoolData();
}

function fetchPreviousSchoolData() {
	userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$.ajax({
			url : 'http://localhost:8080/facebook_v01/webapi/profile/school/'
					+ userid,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				var no_of_object = data.length;
				$('#schoolDynamicDiv').empty();
				for (var i = 0; i < no_of_object; i++) {
					var school_pk = data[i]['school_pk'];
					var school_name = data[i]['school_name'];
					var school_start_year = data[i]['school_start_year'];
					var school_end_year = data[i]['school_end_year'];
					var school_description = data[i]['school_description'];
					$('#schoolDynamicDiv').append(
							'<button class = "btn btn-default pull-right" onclick="removePreviousSchoolData('+school_pk+')">Remove</button>'+
							'<h3 style="color:#3b5998;font-size:16px">'
									+ school_name + '</h3>'
									+ '<p style="color:#808080">'
									+ school_start_year + ' to '
									+ school_end_year + ', '
									+ school_description + '</p>'
									+ '<hr style="margin-top: 4px">');
				}
			}
		})
	}
}


function editPlaces() {
	var userid = document.getElementById("topNavHiddenPK").value;
	var checkedValue = $('input[name=college_attended_for]').filter(':checked').val();
	//alert(place_pk);
	if (place_pk) {
		$.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/editPlaces/'+place_pk,
					type : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						places_current_city : $('#places_current_city_modal').val(),
						places_hometown : $('#places_hometown_modal').val(),
					}),
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						fetchPreviousPlacesData();
					}
				});
	}
	toggleVisibility('div5');
}

function fetchPreviousPlacesData() {
	var userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/getPlaces/'
							+ userid,
					type : 'GET',
					dataType : 'json',
					success : function(data) {
						var no_of_object = data.length;
						if(no_of_object > 0){
							$("#addPlacediv").hide();
						}
						for (var i = 0; i < no_of_object; i++) {
							places_current_city = data[i]['places_current_city'];
							places_hometown = data[i]['places_hometown'];
							place_pk = data[i]['places_pk'];
							$('#placesDynamicDiv')
									.append(
											'<div class="pull-right" style="color:#1C283F;position:relative;padding-left:-1px;padding-top:0px;">'+
							            	'<font size="2"><a href="#placemodal" data-toggle="modal">Edit</a></font>'+
							        		'</div>'+
											'<p style="color:black">Current Place: '
													+ places_current_city
													+ '</h3>'
													+ '<p style="color:black">Home Town: '
													+ places_hometown
													+ '</p>'
													+ '<hr style="margin-top: 4px">');
							$('#places_current_city_modal').attr('value',places_current_city);
							$('#places_hometown_modal').attr('value',places_hometown);
						
						}
					
						
					}
				})
	}
}
// Profile Picture End

// ==========================Rachana================================================//

function contactAndBasicInfoSubmit() {
	var userid = document.getElementById("topNavHiddenPK").value;
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/contactandbasicinfo/'+ userid,
					type : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						userdetails_mobile :  $('#userdetails_mobile').val(),
						userdetails_email :  $('#userdetails_email').val(),
						userdetails_dob :  $('#userdetails_dob').val(),
						userdetails_gender :  $('#userdetails_gender').val(),
						contact_and_basic_info_user_address :  $('#contact_and_basic_info_user_address').val(),
						contact_and_basic_info_interested_in :  $('#contact_and_basic_info_interested_in').val(),
						religions_religion_name :  $('#religions_religion_name').val(),
						contact_and_basic_info_religious_view :  $('#contact_and_basic_info_religious_view').val(),
					}),
					dataType : 'json',
					complete : function(request, textStatus, errorThrown) {
						fetchContactAndBasicInfo();
					}
				});
	}
	toggleVisibility('div7');
}


function fetchContactAndBasicInfo() {
	
	userid = document.getElementById("topNavHiddenPK").value;
	//alert('fetching info');
	if (userid) {
		$
				.ajax({
					url : 'http://localhost:8080/facebook_v01/webapi/profile/contactandbasicinfo/'+ userid,
					type : 'GET',
					dataType : 'json',
					success : function(data) {
						var no_of_object = data.length;
						//alert('info fetched.. data:' + data);

						var userdetails_mobile = data['userdetails_mobile'];
						//alert(userdetails_mobile);
						var userdetails_email = data['userdetails_email'];
						var userdetails_dob = data['userdetails_dob'];
						var userdetails_gender = data['userdetails_gender'];
						var contact_and_basic_info_user_address = data['contact_and_basic_info_user_address'];
						var contact_and_basic_info_interested_in = data['contact_and_basic_info_interested_in'];
						var religions_religion_name = data['religions_religion_name'];
						var contact_and_basic_info_religious_view = data['contact_and_basic_info_religious_view'];
						$('#mobilenumberDynamicDiv').append(
								'<p style="color:#808080">'
										+ userdetails_mobile + '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#addressDynamicDiv').append(
								'<p style="color:#808080">'
										+ contact_and_basic_info_user_address
										+ '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#emailDynamicDiv').append(
								'<p style="color:#808080">' + userdetails_email
										+ '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#dobDynamicDiv').append(
								'<p style="color:#808080">' + userdetails_dob
										+ '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#genderDynamicDiv').append(
								'<p style="color:#808080">'
										+ userdetails_gender + '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#interestedinDynamicDiv').append(
								'<p style="color:#808080">'
										+ contact_and_basic_info_interested_in
										+ '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#religionnameDynamicDiv').append(
								'<p style="color:#808080">'
										+ religions_religion_name + '</p>'
										+ '<hr style="margin-top: 4px">');
						$('#religiousviewDynamicDiv').append(
								'<p style="color:#808080">'
										+ contact_and_basic_info_religious_view
										+ '</p>'
										+ '<hr style="margin-top: 4px">');

						// $('#mytimelineposts').empty();
						/*
						 * for( var i=0; i < no_of_object; i++){ var
						 * userdetails_mobile = data[i]['userdetails_mobile'];
						 * alert(userdetails_mobile); var userdetails_email =
						 * data[i]['userdetails_email']; var userdetails_dob =
						 * data[i]['userdetails_dob']; var userdetails_gender =
						 * data[i]['userdetails_gender']; var
						 * contact_and_basic_info_user_address =
						 * data[i]['contact_and_basic_info_user_address']; var
						 * contact_and_basic_info_interested_in =
						 * data[i]['contact_and_basic_info_interested_in']; var
						 * religions_religion_name =
						 * data[i]['religions_religion_name']; var
						 * contact_and_basic_info_religious_view =
						 * data[i]['contact_and_basic_info_religious_view'];
						 * $('#mobilenumberDynamicDiv').append( '<p style="color:#808080">'+userdetails_mobile+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#addressDynamicDiv').append( '<p style="color:#808080">'+contact_and_basic_info_user_address+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#emailDynamicDiv').append( '<p style="color:#808080">'+userdetails_email+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#dobDynamicDiv').append( '<p style="color:#808080">'+userdetails_dob+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#genderDynamicDiv').append( '<p style="color:#808080">'+userdetails_gender+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#interestedinDynamicDiv').append( '<p style="color:#808080">'+contact_and_basic_info_interested_in+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#religionnameDynamicDiv').append( '<p style="color:#808080">'+religions_religion_name+'</p>'+ '<hr style="margin-top: 4px">');
						 * $('#religiousviewDynamicDiv').append( '<p style="color:#808080">'+contact_and_basic_info_religious_view+'</p>'+ '<hr style="margin-top: 4px">'); }
						 */
					}
				})
	}
}

/*
webapi/post/getTimelinePosts
$('#mytimelineposts').empty();
*/
function fetchPosts() {
	userid = document.getElementById("topNavHiddenPK").value;
	var postSenderDob = "";
	var postSenderEmail = "";
//	$("#timeline").load("MyPosts.html");

	$.ajax({
		url:'http://localhost:8080/facebook_v01/webapi/post/getTimelinePosts/'+userid,
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
				
				
				//Code for Hover on Likes End
				
				
				
				
				/*if(isliked=='true')
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:blue;margin-right:5px"></span><strong style="color:blue">Like</strong></a>';
				else
					element = element + '<a style=" cursor:pointer;" id="'+post_id+'" name="'+isliked+'" onclick="likeonclickfunction(event);"><span class="glyphicon glyphicon-thumbs-up" style="color:gray;margin-right:5px"></span><strong>Like</strong></a>';
				element = element + '<a style=" cursor:pointer;" ><span class="glyphicon glyphicon-comment" style="color:grey;margin-right:5px;margin-left:25px"></span><strong>Comment</strong></a>'+
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
				$('#mytimelineposts').empty();
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
					$('#mytimelineposts').empty();
					fetchPosts();
//					alert("comment added");
				}
			});
		}
        return false;
    }
}



function addPostStatus() {
	// alert('addPOst Called');
	$.ajax({
		url : 'http://localhost:8080/facebook_v01/webapi/post/postStatus/'
				+ userid,
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify({
			postText : $('#whatsOnYourMind').val(),
			senderId : userid,
			receiverId : userid
		}),
		success : function(data) {
			$('#mytimelineposts').empty();
			fetchPosts();
			$('#whatsOnYourMind').val('');
		}
	})

}


function calculatePostTimeToDisplay(postTimestamp) { // Format of
														// postTimestamp::2017-03-21
														// 13:06:22:2

	var ymd = postTimestamp.split(" ")[0].split("-"); // ymd :: yyyy-mm-dd
	var monthName;

	// Month Number to Month Name mapping
	if (ymd[1] === '01')
		monthName = 'January';
	else if (ymd[1] === '02')
		monthName = 'February';
	else if (ymd[1] === '03')
		monthName = 'March';
	else if (ymd[1] === '04')
		monthName = 'April';
	else if (ymd[1] === '05')
		monthName = 'May';
	else if (ymd[1] === '06')
		monthName = 'June';
	else if (ymd[1] === '07')
		monthName = 'July';
	else if (ymd[1] === '08')
		monthName = 'August';
	else if (ymd[1] === '09')
		monthName = 'September';
	else if (ymd[1] === '10')
		monthName = 'October';
	else if (ymd[1] === '11')
		monthName = 'November';
	else if (ymd[1] === '12')
		monthName = 'December';

	var postTimestampDateObject = new Date(postTimestamp); // SQL Date to
															// Javascript Date
	var currentSystemTime = new Date();

	// Calculate time difference b/w post and current time
	var diffInMin = (currentSystemTime.getTime() - postTimestampDateObject
			.getTime()) / 60000;
	var diffInHrs = diffInMin / 60;
	var diffInDay = currentSystemTime.getDate()
			- postTimestampDateObject.getDate();
	var timeToDisplay;

	if (diffInMin <= 1)
		timeToDisplay = "Just Now";

	else if (diffInMin < 60)
		timeToDisplay = Math.round(diffInMin) + " min";

	else if (diffInMin < 1440) {
		if (Math.round(diffInHrs) == 1)
			timeToDisplay = Math.round(diffInHrs) + " hr";
		else
			timeToDisplay = Math.round(diffInHrs) + " hrs";
	}

	else if (diffInMin >= 1440
			&& (diffInDay == 1 || diffInDay == -30 || diffInDay == -29
					|| diffInDay == -27 || diffInDay == -28))
		timeToDisplay = "Yesterday at "
				+ postTimestamp.split(" ")[1].substring(0, 5);

	else
		timeToDisplay = monthName + " " + ymd[2] + " at "
				+ postTimestamp.split(" ")[1].substring(0, 5);

	return timeToDisplay;
}

function addComponentFriendList() {
	userid = document.getElementById("topNavHiddenPK").value;
	$('#suggestFriends').hide();
	// alert("Inside addComponentFriendList");
	$
			.ajax({
				url : 'http://localhost:8080/facebook_v01/webapi/friends/getAllFriendsDetail/'
						+ userid,
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					var no_of_object = parseInt(data.length);
					var half_no_of_object = parseInt((no_of_object - 1) / 2);
					for (var i = 0; i <= half_no_of_object; i++) {
						var name = data[i]['friend_name'];
						var total_friends = data[i]['total_friends'];
						var friend_pk = data[i]['friend_pk'];
						var picfullurl = serverPicLocation
								+ data[i]['friend_picture'];
						$('#row1')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ friend_pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ friend_pk
												+ '" color="black"><Strong>'
												+ name
												+ '</Strong></a><br/>'
												+ '<small> '
												+ total_friends
												+ ' Friend </small>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												+ '<button class="btn btn-primary" type="button" id="'
												+ friend_pk
												+ '" onclick="RemoveFriend(event)">UnFriend</button>'
												+ '&nbsp;'
												+ '</div>'
												+

												'<div class="clearfix"></div>'
												+ '</li>');
					}
					for (var i = half_no_of_object + 1; i < no_of_object; i++) {
						var name = data[i]['friend_name'];
						var total_friends = data[i]['total_friends'];
						var friend_pk = data[i]['friend_pk'];
						var picfullurl = serverPicLocation
								+ data[i]['friend_picture'];
						$('#row2')
								.append(
										'<li class="list-group-item">'
												+ '<div class="col-md-5">'
												+ '<a href="TimeLineFriend.html?refId='
												+ friend_pk
												+ '">'
												+ '<img src="'
												+ picfullurl
												+ '" alt="Scott Stevens" class="circle img-thumbnail img-box"  width=80 height=80/>'
												+ '</a>'
												+ '</div>'
												+ '<div class="col-md-5" style="margin-left:-30px ; margin-top:10px">'
												+ '<a  class="name"  href="TimeLineFriend.html?refId='
												+ friend_pk
												+ '" color="black"><Strong>'
												+ name
												+ '</Strong></a><br/>'
												+ '<small> '
												+ total_friends
												+ ' Friend </small>'
												+ '</div>'
												+ '<div class="col-md-2" id = "temp" style="margin-left:-30px ; margin-top:20px">'
												+ '<button class="btn btn-primary" type="button" id="'
												+ friend_pk
												+ '" onclick="RemoveFriend(event)">UnFriend</button>'
												+ '&nbsp;'
												+ '</div>'
												+

												'<div class="clearfix"></div>'
												+ '</li>');
					}
					
				},
				error : function(xhr, status) {
					if (xhr.status == 202) {
						$('#row1').append('No Friends to show');
					} else {
						window.location.href = 'errorpage.html'; // Something
																	// went
																	// wrong ->
																	// redirect
																	// to error
																	// page
					}
				}
			});
}

function RemoveFriend(event) {
	$.ajax({
		url : 'http://localhost:8080/facebook_v01/webapi/friends/deleteFriend/'
				+ userid + '/' + event.target.id,
		type : 'POST',
		success : function(data) {
			$('#row1').empty();
			$('#row2').empty();
			addComponentFriendList();
		},
		error : function(xhr, status) {
			if (xhr.status == 202) {

			} else {
				window.location.href = 'errorpage.html'; // Something went
															// wrong -> redirect
															// to error page
			}
		}
	});
}

// ==========================End
// Rachana================================================//
