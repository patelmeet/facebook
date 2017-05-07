package org.ooad.facebook_v01.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class EventPictureService {
	
	//public String UPLOAD_FILE_SERVER =  "/home/gunjan/Documents/OOAD/WorkSpace/facebook_v01/src/main/webapp/eventPictures/";
//	public String UPLOAD_FILE_SERVER = "/home/meet/Sem-2/OOAD/workspace/facebook_v01/src/main/webapp/eventPictures/";
	public String UPLOAD_FILE_SERVER = "E:\\apache-tomcat-7.0.67-windows-i64\\apache-tomcat-7.0.67\\webapps\\facebook_v01\\eventPictures\\";
	//public String UPLOAD_FILE_SERVER = "/home/shachi/workspace/facebook_v01/src/main/webapp/eventPictures/";
//	String UPLOAD_FILE_SERVER = "/home/awais/Documents/M.Tech/II - Sem/JaxRS/facebook_v01/src/main/webapp/eventPictures/";
//	String UPLOAD_FILE_SERVER = "/home/chetan/workspace/facebook_v01/src/main/webapp/eventPictures/";
	public int storeImageService(InputStream inputStream,String fileName) throws IOException {

		OutputStream outputStream = null;
		String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;
//       System.out.println(qualifiedUploadFilePath);
		try {
			outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
			int read = 0;
			byte[] bytes = new byte[1024];
//			System.out.println("jbsjcbais"+inputStream.available());
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			
			//String query = "update USERDETAIS set userdetails_picurl = '"+fileName+"' where userdetails_pk="+
			//dbconn.getStmt().executeUpdate(query);
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println(ioe.getMessage());
		}
		finally{
			//release resource, if any
			outputStream.close();
		}
		
		return 0;
	}

}
