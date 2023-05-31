package com.infy.reducer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xerial.snappy.Snappy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.reducer.datacompressor.DataCompressor;
import com.infy.reducer.datacompressor.DataCompressorImpl;
import com.infy.reducer.dataconverter.DataConverter;
import com.infy.reducer.dataconverter.DataConverterImpl;

import com.infy.reducer.file.CompressedFile;

@SpringBootApplication
public class ReducerApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ReducerApplication.class, args);

		try {
			
			// Mapping it with application.properties file		
			Properties properties = new Properties();
			try (InputStream inputStream = ReducerApplication.class.getClassLoader()
					
					.getResourceAsStream("application.properties")) {
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Initializing Class from application.properties file and also path for files			
			String ENTITY_NAME = properties.getProperty("entity_name");
			Class<?> entityClass = Class.forName(ENTITY_NAME);
			DataConverter<?> dataConverter = new DataConverterImpl<>(entityClass);
			DataCompressor<?> dataCompressor = new DataCompressorImpl<>(dataConverter);
			
			//Reading JsonData from a file
			String path = properties.getProperty("jsonInputFile");
			String json = readFileAsString(path);
			
			// Mapping the path of compressed File
			String compressedPath = properties.getProperty("compressedFile") ;
			String output = properties.getProperty("jsonOutputFile") ;
					
			// Clearing the data in Compressed File
			Files.write(Paths.get(compressedPath), new byte[0]) ;
			
						
			// Converting JsonData to List of Object			
			ObjectMapper objectMapper = new ObjectMapper() ;
			List<Object> list = objectMapper.readValue(json, new TypeReference<List<Object>>() {});
			
			// Storing the DeCompressed Object
			List<Object> resultList = new ArrayList<>() ;
			
			
			// Iterating over each object
//			System.out.println(list.toString()) ;
			
			String jsonData = list.toString() ;
			System.out.println(jsonData) ;
			byte[] check = jsonData.getBytes() ;
			
			System.out.println(new String(check)) ;
			byte[] val = Snappy.compress(check);
			CompressedFile.bytetoFile(val) ;
			
			FileInputStream fis = new FileInputStream(compressedPath) ;
			
			byte[] valueCom = fis.readAllBytes() ;
	
			fis.close() ;
			
			byte[] decompress = Snappy.uncompress(valueCom) ;
			System.out.println(new String(decompress)) ;
			
			
			
			
//			resultList = objectMapper.readValue(s, new TypeReference<List<Object>>() {});
//			Map<String,>=objectMapper.convertValue(s, null)
			
		
//		System.out.println(resultList.toString()) ;
			
			
			
//			FileWriter f = new FileWriter(output) ;
//			f.write(s) ;
//			f.close() ;
			
//			for(Object obj : list)
//			{
//				//Compressing the object
//				byte[] compressedData = dataCompressor.compress(obj);	
//				
//				//Appending each compressed object in file			
//				CompressedFile.bytetoFile(compressedData);	
//				
//				//DeCompressing each Object and adding to a list
//				Object decompressedData = dataCompressor.decompress(compressedData);
//				resultList.add(decompressedData) ;
//			}			
			
			
			//Converting back to JsonFile
//			String result = dataConverter.javaObjectToJson(resultList) ;
//			System.out.println(result) ;	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readFileAsString(String path) throws Exception {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
