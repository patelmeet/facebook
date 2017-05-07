import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.model.Login;
import org.ooad.facebook_v01.model.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestClass {

	public static void main(String[] args) throws JsonProcessingException
	{
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("login");

        Login login_details = new Login();
        login_details.setLogin_Id("13");
        login_details.setLogin_Password("nnssd");
        
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(login_details);

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(str, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 201){
        	//Login successful
        	User usr = response.readEntity(User.class);		//Get User Details
        	System.out.println(usr.getUserDetails_pk());
        }
        else if(response.getStatus() == 404){
        	//Login is NOT successful
        	
        }
        else{
        	//OTHER ERROR
        }
        
	}
	
}
