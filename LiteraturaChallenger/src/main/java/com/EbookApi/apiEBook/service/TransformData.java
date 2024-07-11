package com.EbookApi.apiEBook.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class TransformData implements IDeserializer {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T deserializarEntity(URL url, Class<T> classType) throws URISyntaxException, IOException {
        return objectMapper.readValue(url, classType);
    }


}
