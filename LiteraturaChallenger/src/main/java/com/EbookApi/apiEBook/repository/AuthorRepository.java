package com.EbookApi.apiEBook.repository;
import com.EbookApi.apiEBook.model.Author;
import com.EbookApi.apiEBook.model.Book;
import com.EbookApi.apiEBook.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM Author a JOIN a.listaBook lb WHERE lb.title LIKE :bookTitle")
    Optional<Author> findAuthorByTitle(@Param("bookTitle")String bookTitle);

    Optional<Author>findByFullName(String name);

    @Query("SELECT b FROM Book b")
    List<Book>allBooks();

    @Query("SELECT b FROM Book b ORDER BY b.countDownload DESC LIMIT 5 ")
    List<Book>top5Books();

    @Query("SELECT b FROM Book b WHERE b.gender =:topic")
    List<Book>listByTopic(@Param("topic") Gender topic);

    @Query("SELECT b FROM Book b WHERE b.language ILIKE %:lan%")
    List<Book>listbyLanguage(@Param("lan")String lan);

    @Query("SELECT b FROM Book b JOIN b.author a WHERE b.title LIKE :bookTitle")
    Optional<Book> findByBook2(@Param("bookTitle") String bookTitle);


}
