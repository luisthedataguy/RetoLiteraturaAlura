package com.EbookApi.apiEBook;

import com.EbookApi.apiEBook.service.AuthorService;
import com.EbookApi.apiEBook.service.GutendexService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    AuthorService service=new AuthorService();
    @Test
    @DisplayName("search book into database")
    public void findBookByTitle() throws IOException, URISyntaxException {
        String title="Great Expectations";
        //Author author=gutendexService.findBookByTitleGutendex(title);
        //System.out.println(author);
        var re=service.findByTitle(title);


    }
}
