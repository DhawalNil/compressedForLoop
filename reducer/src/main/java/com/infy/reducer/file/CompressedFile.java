package com.infy.reducer.file;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;



@Service
public class CompressedFile {
	public static final Properties properties = new Properties() ;
	static {
		try(InputStream inputStream = CompressedFile.class.getClassLoader().getResourceAsStream("application.properties")){
			properties.load(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	public static void bytetoFile(byte[] jsonData ) {
		
			String FILEPATH = properties.getProperty("compressedFile") ;
			
			try(FileOutputStream fos = new FileOutputStream(FILEPATH ,false)){
				fos.write(jsonData) ;
			}catch(Exception e )
			{
				e.printStackTrace(); 
			}
	}
}
