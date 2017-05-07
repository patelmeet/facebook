package org.ooad.facebook_v01.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ooad.facebook_v01.database.DatabaseConnection;

public class CoverPictureService {
	

	
	public int storeImageService(InputStream inputStream,String user_id,String filename) throws IOException {
//		String filepath = "/home/gunjan/Documents/OOAD/WorkSpace/facebook_v01/src/main/webapp/UserProfilePictures/";
//		String filepath = "/home/meet/Sem-2/OOAD/workspace/facebook_v01/src/main/webapp/UserProfilePictures/";
//		String filepath = "/home/chetan/workspace/facebook_v01/src/main/webapp/UserProfilePictures/";
		String filepath = "E:\\apache-tomcat-7.0.67-windows-i64\\apache-tomcat-7.0.67\\webapps\\facebook_v01\\UserProfilePictures\\";
//		String filepath = "/home/awais/Documents/M.Tech/II - Sem/JaxRS/facebook_v01/src/main/webapp/UserProfilePictures/";
	//	String filepath = "/home/shachi/workspace/facebook_v01/src/main/webapp/UserProfilePictures/";
		
		OutputStream outputStream = null;
		DateFormat df = new SimpleDateFormat("dd-MM-yyHH-mm-ss");
		Date dateobj = new Date();
		String qualifiedUploadFilePath =  user_id+df.format(dateobj)+filename;
//		System.out.println(qualifiedUploadFilePath);
		try {
			outputStream = new FileOutputStream(new File(filepath+qualifiedUploadFilePath));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally{
			//release resource, if any
			outputStream.close();
		}
		
		int status = insertToDatabase(qualifiedUploadFilePath,user_id);
		return status;
	}

	private int insertToDatabase(String qualifiedUploadFilePath,String user_id) {
		// TODO Auto-generated method stub
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		String query = "update USERDETAILS set userdetails_coverpicurl = '"+qualifiedUploadFilePath+"' where userdetails_pk="+user_id;
		
		
		try{
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		}
		catch(Exception e){
			System.out.print(e.getMessage());
			return 1;
		}
		
		return 0;
		
		
		
	}

}
