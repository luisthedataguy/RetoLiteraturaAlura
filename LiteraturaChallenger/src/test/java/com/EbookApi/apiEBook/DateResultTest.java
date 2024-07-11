package com.EbookApi.apiEBook;

import com.EbookApi.apiEBook.model.DateBook;
import com.EbookApi.apiEBook.model.DateResult;
import com.EbookApi.apiEBook.service.TransformData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootTest
public class DateResultTest {
    @Test
    @DisplayName("verifica los datos de los libros de Gutendexpi")
    public void dateBookTest() throws IOException, URISyntaxException {
        TransformData transformData=new TransformData();
        var resultBook=transformData.deserializarEntity(new URL("https://gutendex.com/books?id=1"), DateResult.class).results();
        Assertions.assertNotNull(resultBook);
        for(DateBook db:resultBook){
            Assertions.assertNotNull(db.listaAutores());
            Assertions.assertNotNull(db.copyright());
            Assertions.assertNotNull(db.downloadCount());
            Assertions.assertNotNull(db.title());
            Assertions.assertNotNull(db.languages());
        }
        System.out.println(resultBook);




    }
}
