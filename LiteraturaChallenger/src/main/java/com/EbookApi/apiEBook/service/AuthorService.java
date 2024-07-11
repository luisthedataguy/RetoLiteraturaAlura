package com.EbookApi.apiEBook.service;

import com.EbookApi.apiEBook.dto.AuthorDTO;
import com.EbookApi.apiEBook.dto.BookDto;
import com.EbookApi.apiEBook.menu.ColoursConsole;
import com.EbookApi.apiEBook.model.Author;
import com.EbookApi.apiEBook.model.Book;
import com.EbookApi.apiEBook.model.Gender;
import com.EbookApi.apiEBook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    GutendexService gutendexService = new GutendexService();


    public AuthorDTO searchAuthorByName(String nameAuthor) throws IOException, URISyntaxException {
        Optional<Author> authorDb = authorRepository.findByFullName(nameAuthor);
        AuthorDTO author = null;
        if (authorDb.isPresent()) {
            author = authorDb.map(a -> new AuthorDTO(a.getFullName(), a.getBirthDate(), a.getDeathDate()))
                    .orElse(null);
        } else {
            //buscar gutendex
            Author newAuthor = gutendexService.searchAuthorGutendex(nameAuthor);
            if (newAuthor != null) {
                authorRepository.save(newAuthor);
                return new AuthorDTO(newAuthor.getFullName(), newAuthor.getBirthDate(), newAuthor.getDeathDate());
            }
            System.out.println("Autor no encontrado");
        }

        return author;
    }


    //verifica si un autor existe en la base de datos
    private Author authorExist(String fullName) {
        Optional<Author> author = authorRepository.findByFullName(fullName);
        return author.orElse(null);

    }

    //obtiene todos los autoresdto
    public List<AuthorDTO> allAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorDTO(author.getFullName(), author.getBirthDate(), author.getDeathDate()))
                .collect(Collectors.toList());
    }

    //obtiene una lista de autoresdto vivos en x anio
    public List<AuthorDTO> listAuthorByYear(int year) {
        return allAuthors().stream()
                .filter(author -> author.deathDeath() >= year && author.birthDate() <= year)
                .map(author -> new AuthorDTO(author.fullName(), author.birthDate(), author.deathDeath()))
                .collect(Collectors.toList());

    }

    //obtiene todos los librosdto
    public List<BookDto> allBooks() {
        return ListBookToListBookDto(authorRepository.allBooks());
    }

    //obtiene una lista de librosdto mas descargados
    public List<BookDto> top5Books() {
        return ListBookToListBookDto(authorRepository.top5Books());
    }

    //obtiene una lista de libros por genero
    public List<BookDto> listByTopic(Gender gender) {
        return ListBookToListBookDto(authorRepository.listByTopic(gender));
    }

    //obtiene la lista de libros por lenguaje
    public List<BookDto> listByLanguage(String lan) {
        return ListBookToListBookDto(authorRepository.listbyLanguage(lan));
    }

    //Muestra las estadisticas de todos los libros
    public String booksStadistics() {
        LongSummaryStatistics sta = allBooks().stream()
                .mapToLong(BookDto::countdownload)
                .summaryStatistics();
        return String.format("""
                Media de descargas: %.2f\s
                Maximo de descargas: %d\s
                Minimo de descargas: %d\s
                Registros totales: %d\s
                """, sta.getAverage(), sta.getMax(), sta.getMin(), sta.getCount());

    }

    public BookDto searchBook(String title) throws IOException, URISyntaxException {
        BookDto book = getBookDb(title);
        if (book != null) {
            return book;
        } else {
            Book bookGutendex = gutendexService.searchBookGuntendex(title);
            if (bookGutendex == null) {
                System.out.println(ColoursConsole.paintFontBackground("BGYELLOW", "RED", "No se ha encontrado el libro solicitado"));
                return null;
            }
            Author author = authorExist(bookGutendex.getAuthor().getFullName());
            if (author != null) {
                List<Book> bookAux = new ArrayList<>();
                bookGutendex.setAuthor(author);
                bookAux.add(bookGutendex);
                author.getListaBook().addAll(bookAux);
                authorRepository.save(author);
            } else {
                authorRepository.save(bookGutendex.getAuthor());
            }

            return mapToBookDto(bookGutendex);

        }
    }

    //Obtiene un libro de la base de datos
    private BookDto getBookDb(String title) {
        return authorRepository.findByBook2(title)
                .map(this::mapToBookDto)
                .orElse(null);
    }

    //convierte una lista de libros a una lista de librosDto
    private List<BookDto> ListBookToListBookDto(List<Book> listBooks) {
        return listBooks.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    private BookDto mapToBookDto(Book book) {
        return new BookDto(book.getTitle(), book.getCountDownload(), book.getLanguage(),
                book.getCopyright(), Gender.getValue(book.getGender()),
                new AuthorDTO(book.getAuthor().getFullName(),
                        book.getAuthor().getBirthDate(), book.getAuthor().getDeathDate()));
    }


    //De
    //buscar un libro por titulo en la base de datos sino lo encuentra lo busca en gutendex
    @Deprecated
    public BookDto findByTitle(String title) throws IOException, URISyntaxException {
        Optional<Author> authorByTitle = authorRepository.findAuthorByTitle(title);
        //Buscar un libro en el autor si esta presente lo devuelve
        if (authorByTitle.isPresent()) {
            var bookAux = authorByTitle.get().getListaBook().stream()
                    .filter(books -> books.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .orElse(null);
            assert bookAux != null;
            return mapToBookDto(bookAux);

        } else {
            // sino Buscara en guntendex y lo guardar en la base datos ademas de retornalo
            Author newAuthor = gutendexService.findAuthorByTitle(title);
            if (newAuthor == null) {
                System.out.println(ColoursConsole.paintFontBackground("BGYELLOW", "RED", "No se ha encontrado el libro solicitado"));
                return null;
            }
            try {
                System.out.println(ColoursConsole.paintFontBackground("BGPURPLE", "WHITE", "Buscando de otra fuente aguarde por favor ..."));
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //Retorna un Author de la base de datos si existe
            Author authorDb = authorExist(newAuthor.getFullName());

            if (authorDb != null) {
                var res = authorDb.getListaBook().stream()
                        .anyMatch(books -> books.getTitle().equalsIgnoreCase(newAuthor.getListaBook().get(0).getTitle()));

                if (!res) {
                    newAuthor.setId(authorDb.getId());
                    authorDb.getListaBook().addAll(newAuthor.getListaBook());
                    authorRepository.save(authorDb);
                }
            } else {
                authorRepository.save(newAuthor);
            }
            Book bookDto1 = newAuthor.getListaBook().get(0);
            return new BookDto(bookDto1.getTitle(), bookDto1.getCountDownload(), bookDto1.getLanguage(), bookDto1.getCopyright(), Gender.getValue(bookDto1.getGender()),
                    new AuthorDTO(newAuthor.getFullName(), newAuthor.getBirthDate(), newAuthor.getDeathDate()));

        }

    }

}
