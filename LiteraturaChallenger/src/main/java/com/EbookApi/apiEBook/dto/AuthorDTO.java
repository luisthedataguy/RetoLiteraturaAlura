package com.EbookApi.apiEBook.dto;

import java.time.LocalDate;

public record AuthorDTO(

        String fullName,
        Integer birthDate,
        Integer deathDeath


) {
    @Override
    public String toString() {
        return "----\nAuthor\n" +
                "Nombre completo: " + fullName + "\n"+
                "Año de nacimiento: " + birthDate + "\n"+
                "Año de fallecimiento: " + deathDeath +"\n";
    }
}
