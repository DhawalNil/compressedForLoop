package com.infy.reducer.dataconverter;

import java.util.List;

public interface DataConverter<T> {
	
	T jsonToJavaObject(String jsonData ) throws Exception;
	String javaObjectToJson(List<Object> t1) throws Exception ;
	String javaObjectToJsonFile(Object o) throws Exception;
}
