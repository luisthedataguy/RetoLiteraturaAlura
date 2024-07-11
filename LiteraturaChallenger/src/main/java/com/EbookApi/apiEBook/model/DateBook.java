package com.EbookApi.apiEBook.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DateBook(

        @JsonAlias("title")
        String title,
        @JsonAlias("authors")
        List<DateAuthors> listaAutores,
        @JsonAlias("languages")
        String[]languages,
        @JsonAlias("download_count")
        Long downloadCount,
        @JsonAlias("copyright")
        Boolean copyright,
        @JsonAlias("subjects")
        String[] gender

) {

        @Override
        public String toString() {
                return "DateBook{" +
                        "title='" + title + '\'' +
                        ", listaAutores=" + listaAutores +
                        ", languages=" + Arrays.toString(languages) +
                        ", downloadCount=" + downloadCount +
                        ", copyright=" + copyright +
                        '}';
        }
}
