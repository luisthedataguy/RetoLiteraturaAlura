package com.EbookApi.apiEBook.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long countDownload;
    private String language;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean copyright;
    @ManyToOne
    private Author author;

    public Book() {
    }

    public Book(Author author, Boolean copyright, Gender gender, String language, Long countDownload, String title) {
        this.author = author;
        this.copyright = copyright;
        this.gender = gender;
        this.language = language;
        this.countDownload = countDownload;
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCountDownload() {
        return countDownload;
    }

    public void setCountDownload(Long countDownload) {
        this.countDownload = countDownload;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Libro: " + "\n" +
                "id " + id + "\n" +
                "Titulo " + title + "\n" +
                "Numero descargas " + countDownload + "\n" +
                "Lenguajes " + language  + "\n" +
                "Gender " + gender + "\n" +
                "Copyright " + copyright +"\n";
    }
}
