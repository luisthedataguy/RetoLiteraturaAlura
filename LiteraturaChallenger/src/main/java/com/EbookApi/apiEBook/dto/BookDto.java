package com.EbookApi.apiEBook.dto;

public record BookDto(
        String title,
        Long countdownload,
        String language,
        Boolean copyright,
        String genero,
        AuthorDTO author

) {
    private String hasCopyright(){
        return this.copyright?"Si":"No";
    }

    @Override
    public String toString() {
        return "Libro" + "\n" +
                "-----\n"+
                "Titulo: " + title + "\n" +
                "Numero descargas: " + countdownload + "\n" +
                "Lenguajes: " + language  + "\n" +
                "Gender: " + genero + "\n" +
                "Copyright: " + hasCopyright() +"\n" +
                "Nombre del autor: " +author.fullName();
    }
}
