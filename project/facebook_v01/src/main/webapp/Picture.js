/**
 * 
 */
function pictureChange(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    //alert("I am here");
    reader.onload = function (e) {
      $('#profilepic')
        .attr('src', e.target.result);
        
    };
    reader.readAsDataURL(input.files[0]);
  }
}


//$('#urlText').keyup(function(){
//   $('#img').attr('src',$('#urlText').val());
//});

//function sendData(){
//	var imgElem = $('#profilepic').attr('src');
//	alert(imgElem);
//	imgElem.replace(/^data:image\/(png|jpg);base64,/, "");
//	alert(imgElem);
//    var imgData = JSON.stringify(imgElem);
//	alert(imgData);
//	console.log(imgData);
//	
//  $.ajax({
//  url: 'http://localhost:8080/facebook_v01/webapi/pic/loadpic',
//  dataType: 'json',
//  data: imgData,
//  type: 'POST',
//  success: function(data) {
//	  console.log("djfnkjvn");
//	  alert("img");
//    console.log(data);
//    }
// });
//}
//
//function getBase64Image(imgElem) {
//// imgElem must be on the same server otherwise a cross-origin error will be thrown "SECURITY_ERR: DOM Exception 18"
//	
//	
//}

function sendData()
{
var imgElem = document.getElementById('profilepic');
var imgData = JSON.stringify({data:getBase64Image(imgElem)});
//alert(imgElem);
//alert(imgData);
console.log(imgData);
	$.ajax({
		url: 'GetDetailedInfoServlet',
		type: 'POST',
		data: {msg:getBase64Image(imgElem)},
		dataType: 'text',
		success: function(data) {
		  console.log(data);
		  window.location.href = "showpic.html";
		  }
		});
}

function getBase64Image(imgElem) {
//imgElem must be on the same server otherwise a cross-origin error will be thrown "SECURITY_ERR: DOM Exception 18"
  var canvas = document.createElement("canvas");
  canvas.width = imgElem.clientWidth;
  canvas.height = imgElem.clientHeight;
  var ctx = canvas.getContext("2d");
  ctx.drawImage(imgElem, 0, 0);
  var dataURL = canvas.toDataURL("image/png");
  return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}


   