package com.EbookApi.apiEBook.service;



import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public interface IDeserializer {
    <T> T deserializarEntity(URL url, Class<T> classType) throws URISyntaxException, IOException;


}
