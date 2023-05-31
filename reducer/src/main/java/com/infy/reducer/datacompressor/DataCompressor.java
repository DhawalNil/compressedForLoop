package com.infy.reducer.datacompressor;


public interface DataCompressor<T> {
	byte[] compress(Object t) throws Exception ;
	T decompress(byte[] compressedData) throws Exception ;
}
