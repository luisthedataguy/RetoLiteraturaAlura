package com.EbookApi.apiEBook.service;
import com.EbookApi.apiEBook.dto.AuthorDTO;
import com.EbookApi.apiEBook.model.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class
GutendexService {

    final String urlBase = "https://gutendex.com/books?";
    TransformData transformData = new TransformData();


    public Author findAuthorByTitle(String title) throws IOException, URISyntaxException {
        String url = urlBase + "search=" + title.replace(" ", "%20");
        DateResult result = transformData.deserializarEntity(new URL(url), DateResult.class);
        System.out.println(result.results());
        var resultado = result.results().stream()
                .map(this::mapAttributesNulls)
                //.peek(System.out::println)
                .collect(Collectors.toMap(a -> getAuthor(a.listaAutores())
                        , this::getBook));
        var entryAuthor = resultado.entrySet().stream()
                .max(Map.Entry.comparingByValue(Comparator.comparing(Book::getCountDownload)))
                .map(entry -> {
                    Author author = entry.getKey();
                    Book book = entry.getValue();
                    List<Book> listBook = new ArrayList<>();
                    listBook.add(book);
                    author.setListaBook(listBook);
                    book.setAuthor(author);
                    return entry;
                });
        return entryAuthor.map(Map.Entry::getKey)
                .orElse(null);

    }

    public Book searchBookGuntendex(String title) throws IOException, URISyntaxException {
        String url = urlBase + "search=" + title.replace(" ", "%20");
        DateResult result = transformData.deserializarEntity(new URL(url), DateResult.class);
       return result.results().stream()
                .filter(dateBook -> dateBook.title().equalsIgnoreCase(title))
                .map(this::getBook2)
                .findFirst().orElse(null);

    }

    public Author searchAuthorGutendex(String fullName) throws IOException, URISyntaxException {
        String url = urlBase + "search=" + fullName.replace(" ", "%20");
        DateResult result = transformData.deserializarEntity(new URL(url), DateResult.class);
      return result.results().stream()
                .flatMap(dataBook->dataBook.listaAutores().stream().parallel()
                        .filter(dataAuthor->dataAuthor.name().equalsIgnoreCase(fullName))
                        .map(dateAuthors ->new Author(dateAuthors.name(),Integer.parseInt(dateAuthors.birthDate()),Integer.parseInt(dateAuthors.deathYear()),new ArrayList<>())))
               .findAny().orElse(null);


    }

    //transforma un author
    private Author getAuthor(List<DateAuthors> listAuthors) {
        return listAuthors.stream()
                .findFirst()
                .map(author -> new Author(author.name(),
                        Integer.parseInt(author.birthDate()),
                        Integer.parseInt(author.deathYear()),
                        null)).orElse(null);

    }

    //transforma un dateBook a un Book
    private Book getBook(DateBook dateBook) {
        return new Book(null, dateBook.copyright()
                , getGender(dateBook.gender()),
                Arrays.toString(dateBook.languages()),
                dateBook.downloadCount(), dateBook.title());
    }

    //Obtiene un genero aleatorio de gutendex
    private Gender getGender(String[] genders) {
        var genderBook = Arrays.stream(genders)
                .flatMap(gender -> Arrays.stream(Gender.values())
                        .filter(genAux -> gender.contains(Gender.getValue(genAux))))
                .distinct()
                .collect(Collectors.toList());
        if(genderBook.isEmpty()){
            genderBook.add(Gender.UNKNOWN);
        }
        int ramdomIndex = new Random().nextInt(0, genderBook.size());
        return genderBook.get(ramdomIndex);

    }
    private DateBook mapAttributesNulls(DateBook dateBook){
            DateBook auxDateBook;
            DateAuthors auxDateAuthor;
            List<DateAuthors>listAuthor=new ArrayList<>();
            for(DateAuthors dateAuthors:dateBook.listaAutores()){
                if(dateAuthors.deathYear()==null && dateAuthors.birthDate()!=null){
                    String dateDead=(Integer.parseInt(dateAuthors.birthDate())+1)+"";
                    auxDateAuthor=new DateAuthors(dateAuthors.name(),dateAuthors.birthDate(),dateDead);
                    listAuthor.add(auxDateAuthor);
                    auxDateBook=new DateBook(dateBook.title(),
                            listAuthor,dateBook.languages(),
                            dateBook.downloadCount(),dateBook.copyright(),
                            dateBook.gender());
                    return auxDateBook;
                } else if (dateAuthors.birthDate()==null && dateAuthors.deathYear()!=null) {
                    String dateBirth=(Integer.parseInt(dateAuthors.deathYear())+1)+"";
                    auxDateAuthor=new DateAuthors(dateAuthors.name(),dateBirth,dateAuthors.deathYear());
                    listAuthor.add(auxDateAuthor);
                    auxDateBook=new DateBook(dateBook.title(),
                            listAuthor,dateBook.languages(),
                            dateBook.downloadCount(),dateBook.copyright(),
                            dateBook.gender());
                    return auxDateBook;
                } else if (dateAuthors.birthDate()==null && dateAuthors.deathYear()==null) {
                    auxDateAuthor=new DateAuthors(dateAuthors.name(),LocalDate.now().getYear()+"",LocalDate.now().getYear()+"");
                    listAuthor.add(auxDateAuthor);
                    auxDateBook=new DateBook(dateBook.title(),
                            listAuthor,dateBook.languages(),
                            dateBook.downloadCount(),dateBook.copyright(),
                            dateBook.gender());
                    return auxDateBook;
                }else{
                    return dateBook;
                }
            }
        return dateBook;
    }

    private Book getBook2(DateBook dateBook) {
       var autorAux= dateBook.listaAutores().stream()
                .map(author->new Author(author.name(),Integer.parseInt(author.birthDate()),Integer.parseInt(author.deathYear()),null))
                .findFirst().orElse(null);
        return new Book(autorAux, dateBook.copyright()
                , getGender(dateBook.gender()),
                Arrays.toString(dateBook.languages()),
                dateBook.downloadCount(), dateBook.title());
    }


}
