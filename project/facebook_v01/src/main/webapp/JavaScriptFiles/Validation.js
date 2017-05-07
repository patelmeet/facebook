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
			        if(!re.test(email_mob) && (!filter.test(email_mob)) ) {
			          $("#email_mobile").focus();
			          $("#email_mobile").tooltip({title: "Enter valid Email/Mobile number!", trigger: "focus"}); 
			        }
				}
			});
		
		$('#confirm_email_mobile').blur(function(){
		    var confirm = $('#confirm_email_mobile').val();
		    var email_mob = $('#email_mobile').val();
		    	if ( confirm != email_mob )
		    	{
		    		$('#confirm_email_mobile').tooltip({title: "Enter Same Email or Mobile number As Above!", trigger: "focus"}); 
		    	}
		    
		});
		
		
});



