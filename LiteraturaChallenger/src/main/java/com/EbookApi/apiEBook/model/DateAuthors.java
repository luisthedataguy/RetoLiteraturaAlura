package com.EbookApi.apiEBook.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DateAuthors(
        @JsonAlias("name")
        String name,
        @JsonAlias("birth_year")
        String birthDate,
        @JsonAlias("death_year")
        String deathYear




) {


        @Override
        public String toString() {
                return "DateAuthors{" +
                        "name='" + name + '\'' +
                        ", birthDate='" + birthDate + '\'' +
                        ", deathYear='" + deathYear + '\'' +
                        '}';
        }
}
