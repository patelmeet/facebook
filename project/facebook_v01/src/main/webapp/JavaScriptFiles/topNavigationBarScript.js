
var serverPicLocation = "UserProfilePictures/";
var statuscheck = true;
function topNavBarOnLoadFunction()
{
//	alert("Inside topNavBarOnLoadFunction");
	console.log("Inside topNavBarOnLoadFunction");
	loadNavigationBarInDiv();
    topNavigationBarCheckSession();
}

function loadNavigationBarInDiv()
{
//	alert("Inside loadNavigationBarInDiv");
	console.log("Inside loadNavigationBarInDiv");
	$("#topNavigationBar").load("TopNavigationBar.html");
	statuscheck = false;
}
//window.onload = topNavBarOnLoadFunction();
function storeSearchValue()
{
	var query = document.getElementById("search").value;
	//alert("njknkjnquery");
	window.location = "Search.html?"+query;
	
}
function topNavigationBarCheckSession()
{
//	alert("Inside topNavigationBarCheckSession");
	console.log("Inside topNavigationBarCheckSession");
	while(statuscheck);
//	alert("Inside topNavigationBarCheckSession");
	$.ajax({
        url: 'CheckSessionServlet',
        type: 'POST',
        dataType: 'json',
    	complete : function(request, textStatus, errorThrown){
//    		alert(request.getResponseHeader('mystatus'))
//    		alert("Request completed")
    		if(request.getResponseHeader('mystatus') == 205){
    			document.getElementById("topNavHiddenPK").value = request.getResponseHeader('userdetails_pk')
    			document.getElementById("topNavHiddenUsername").value = request.getResponseHeader('userdetails_username')
    			document.getElementById("topNavHiddenFirstname").value = request.getResponseHeader('userdetails_firstname')
    			document.getElementById("topNavHiddenLastname").value = request.getResponseHeader('userdetails_lastname')
    			document.getElementById("topNavHiddenProfilePicURL").value = request.getResponseHeader('userdetails_profile_pic_url')
    			document.getElementById("topNavHiddenCoverPicURL").value = request.getResponseHeader('userdetails_cover_pic_url')
    			document.getElementsByClassName('user-name')[0].innerHTML = request.getResponseHeader('userdetails_firstname');
    			document.getElementById("topNavBarProPic").src = serverPicLocation + request.getResponseHeader('userdetails_profile_pic_url')
    			//document.getElementById("topNavBarCoverPic").src = serverPicLocation + request.getResponseHeader('userdetails_cover_pic_url')
    		//	alert(document.getElementById("topNavHiddenPK").value)
//    			alert("Before doMyOtherOnloadStuff");
    			doMyOtherOnloadStuff();
    			loadChatColumn(); // Change on 14-03-17
    		}
    		else if(request.getResponseHeader('mystatus') == 206){
    			window.location = "index.html"
    		}
    		else{
    			window.location = "MiscellaneousPages/ErrorPage.html"
			}
    	}
    });
	
}

function logoutfunction()
{
//	alert("In logout function");
	$.ajax({
        url: 'LogoutServlet',
        type: 'POST'
    });
}