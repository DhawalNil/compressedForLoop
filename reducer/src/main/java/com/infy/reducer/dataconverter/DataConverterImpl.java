package com.infy.reducer.dataconverter;

import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataConverterImpl<T> implements DataConverter<T> {
	
	public static final Properties properties = new Properties() ;
	static {
		try(InputStream inputStream = DataConverterImpl.class.getClassLoader().getResourceAsStream("application.properties")){
			properties.load(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}

	private static ObjectMapper objectMapper = new ObjectMapper();

	private final Class<T> entityClass;

	public DataConverterImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public DataConverterImpl() {
		this.entityClass = null;
	}

	@Override
	public T jsonToJavaObject(String jsonData) throws Exception {		
		return objectMapper.readValue(jsonData, entityClass);
	}

	@Override
	public String javaObjectToJson(List<Object> object) throws Exception {
		
			//Converting in file
			String routes = properties.getProperty("jsonOutputFile");
			FileWriter fileWriter = new FileWriter(routes) ;
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, object);
            
            // Just to print at console
            return objectMapper.writeValueAsString(object);
	}
	@Override
	public String javaObjectToJsonFile(Object object) throws Exception{
		
		String routes = properties.getProperty("jsonOutputFile");
		FileWriter fileWriter = new FileWriter(routes) ;
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, object);
		return objectMapper.writeValueAsString(object) ;
	}
}
