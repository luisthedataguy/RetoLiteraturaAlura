package com.EbookApi.apiEBook;

import com.EbookApi.apiEBook.model.DateAuthors;
import com.EbookApi.apiEBook.model.DateBook;
import com.EbookApi.apiEBook.model.DateResult;
import com.EbookApi.apiEBook.service.TransformData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootTest
public class DateAuthorsTest {
    @Test
    @DisplayName("verifica si se obtienen los autores")
    public void testDateAuthors() throws IOException, URISyntaxException {
        TransformData transformData=new TransformData();
        var resultBooks=transformData.deserializarEntity(new URL("https://gutendex.com/books?id=1"), DateResult.class).results();
        if(resultBooks.isEmpty()){
            Assertions.fail("la lista de libros esta vacia");
        }
        for(DateBook book:resultBooks){
            book.listaAutores().forEach(da->{
                Assertions.assertNotNull(da);
                Assertions.assertNotNull(da.name());
                Assertions.assertNotNull(da.birthDate());
                Assertions.assertNotNull(da.deathYear());
            });
        }
        resultBooks.forEach(System.out::println);
        
        
    }
}
