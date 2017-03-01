/**
 * 
 */


	$(document).ready(function(){
		$('#email_mobile').blur(function(){
			
			    var email_mob = $('#email_mobile').val();
			    console.log(email_mob);
			    if ( email_mob != "")
			    {
			    	var re = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
			    	var filter = /^[0-9-+]+$/;
			        // validation fails if the input doesn't match our regular expression
			        if(!re.test(email_mob) && (filter.test(email_mob) && email_mob.length!=10)) {
			          alert("Error: Input contains invalid characters!");
			        }
				}
			});
		
		$('#confirm_email_mobile').blur(function(){
		    var confirm = $('#confirm_email_mobile').val();
		    var email_mob = $('#email_mobile').val();
		    if ( confirm != email_mob )
		    	alert("Error: confirm email/mobile is not same !!");
		    
		});
		
//		$('#password').blur(function(){
//		    var name = $('#password').val()
//		    if ( name == "")
//		    	$("#password").css({"border-color":"#FF0000" , "border-style":"solid"});
//		    else if( name != "")
//		    	$("#password").addClass("form-group");
//		});

		
});



