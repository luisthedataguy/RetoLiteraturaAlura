package com.EbookApi.apiEBook.menu;



import java.util.Arrays;
import java.util.stream.Collectors;

public enum ColoursConsole {
    BLACK("\u001B[30m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m"),
    PURPLE("\u001B[35m"),
    GREEN("\u001B[32m"),
    WHITE("\u001B[37m"),
    BGBLACK("\u001B[40m"),
    BGRED("\u001B[41m"),
    BGGREEN("\u001B[42m"),
    BGYELLOW("\u001B[43m"),
    BGBLUE("\u001B[44m"),
    BGPURPLE("\u001B[45m"),
    BGCYAN("\u001B[46m"),
    BGWHITE("\u001B[47m");

    private final String ansiValue;

    ColoursConsole(String ansiValue){
        this.ansiValue=ansiValue;
    }

    public static String paintFont(ColoursConsole fontColour,String text){
      return Arrays.stream(ColoursConsole.values())
                .filter(colours->colours.equals(fontColour) && !fontColour.name().startsWith("BG"))
                .map(ColoursConsole::getValue)
                .collect(Collectors.joining(""))+text+"\u001B[0m";

    }

    public static String paintFont(String fontColour,String text){
        for(ColoursConsole c:ColoursConsole.values()){
            if(c.name().equalsIgnoreCase(fontColour) && !fontColour.toUpperCase().startsWith("BG")){
                return c.getValue()+text+"\u001B[0m";
            }
        }
        return text;
    }

    public static String paintBackground(String colourBackground,String text){
        for(ColoursConsole c:ColoursConsole.values()){
            if(c.name().equalsIgnoreCase(colourBackground) && colourBackground.toUpperCase().startsWith("BG")){
                return c.getValue()+text+"\u001B[0m";
            }
        }
        return text;
    }
    public static String paintBackground(ColoursConsole backgroundColour,String text){
        for(ColoursConsole c:ColoursConsole.values()){
            if(backgroundColour.name().startsWith("BG")){
                return c.getValue()+text+"\u001B[0m";
            }
        }
        return text;
    }
    public static String paintFontBackground(String colourBackground,String fontColour,String text){
        StringBuilder textAux= new StringBuilder();
        for(ColoursConsole c:ColoursConsole.values()){
            if( (c.name().equalsIgnoreCase(colourBackground) && colourBackground.toUpperCase().startsWith("BG")) ||
                    c.name().equalsIgnoreCase(fontColour) && !fontColour.startsWith("BG")){
                textAux.append(c.getValue());
            }
        }
        textAux.append(text)
                .append("\u001B[0m");
        return textAux.toString();
    }
    private String getValue(){
        return this.ansiValue;
    }

}
