package com.EbookApi.apiEBook;

import com.EbookApi.apiEBook.menu.ColoursConsole;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColoursConsoleTest {
    @Test
    @DisplayName("check is colour is valid")
    public void colourValid(){
        var re=ColoursConsole.paintFont("bgCYAN","Hello World");
        System.out.println(re);
        var re2=ColoursConsole.paintFont(ColoursConsole.GREEN,"message");
        System.out.println(re2);
        var re3=ColoursConsole.paintBackground("BGCYAN","algo");
        System.out.println(re3);
        var re4=ColoursConsole.paintFontBackground("BGGREEN","RED","ARCHIVO ACTUALIZADO");
        System.out.println(re4);
    }
}
