package com.EbookApi.apiEBook;

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
public class ResultsTest {
    @Test
    @DisplayName("check data result from")
    public void test() throws IOException, URISyntaxException {
        TransformData transformData=new TransformData();
        var entidadResultado=transformData.deserializarEntity(new URL("https://gutendex.com/books?id=1"), DateResult.class);
        Assertions.assertNotNull(entidadResultado);
        Assertions.assertNotNull(entidadResultado.count(),"error cantidad no debe ser null");
        Assertions.assertNotNull(entidadResultado.results(),"los resultados del json son null");
        System.out.println(entidadResultado);

    }
}
