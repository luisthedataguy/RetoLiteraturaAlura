package com.EbookApi.apiEBook.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DateResult(
        @JsonAlias("count")
        Integer count,
        @JsonAlias("results")
        List<DateBook> results


) {

    @Override
    public String toString() {
        return "DateResult{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}


