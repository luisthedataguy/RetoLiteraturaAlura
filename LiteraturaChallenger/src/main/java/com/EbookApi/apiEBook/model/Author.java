package com.EbookApi.apiEBook.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private Integer birthDate;
    private Integer deathDate;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<Book> listaBook;

    public Author() {
    }

    public Author(String fullName, Integer birthDate, Integer deathDate, List<Book> listaBook) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.listaBook = listaBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Integer deathDate) {
        this.deathDate = deathDate;
    }

    public List<Book> getListaBook() {
        return listaBook;
    }

    public void setListaBook(List<Book> listaBook) {
        this.listaBook = listaBook;
    }



    @Override
    public String toString() {
        return "Autor " + "\n" +
                "id " + id + "\n" +
                "Nombre completo " + fullName + "\n" +
                "Anio nacimiento " + birthDate + "\n" +
                "Anio de Fallecimiento " + deathDate + "\n" +
                "Lista de libros " + listaBook + "\n";
    }
}
