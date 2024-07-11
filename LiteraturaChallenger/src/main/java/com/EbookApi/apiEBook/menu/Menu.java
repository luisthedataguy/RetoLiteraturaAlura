package com.EbookApi.apiEBook.menu;

import com.EbookApi.apiEBook.model.Gender;
import com.EbookApi.apiEBook.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    AuthorService authorService;

    public Menu(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void startAPIMenu() throws IOException, URISyntaxException {
        int op = 0;
        System.out.println("Bienvenidos al menu de API ebook");
        do {
            try {
                System.out.println("""
                        *****Menu*****
                        1.Listar todos libros disponibles
                        2.Buscar libro por titulo
                        3.Listar autores registrados
                        4.Buscar libro por topico
                        5.Listar top 5 libros
                        6.Listar autor por anio
                        7.Buscar libros por idiomas
                        8.Estadisticas de los libros
                        9.Buscar autor por nombre
                        10.Buscada mejorada de libros
                        0.Salir""");
                switch (op = scanner.nextInt()) {
                    case 0:
                        System.out.println("Hilo bloqueado");
                        op=0;
                        //Thread.sleep(2000);
                        break;
                    case 1:
                        scanner.nextLine();
                        var allBooks = authorService.allBooks();
                        if (allBooks == null || allBooks.isEmpty()) {
                            System.out.println("No hay libros cargados");
                            break;
                        }
                        scanner.nextLine();
                        allBooks.forEach(System.out::println);
                        break;
                    case 2:
                        scanner.nextLine();
                        String nameAuthors;
                        System.out.println("Ingrese el nombre del libro");
                        nameAuthors=scanner.nextLine();
                        var result=authorService.searchBook(nameAuthors);
                        if(result!=null){
                            System.out.println(result);
                            break;
                        }
                        break;

                    case 3:
                        scanner.nextLine();
                        System.out.println(ColoursConsole.paintFont("yellow", "NOTA los autores se registran buscando libro no existentes"));
                        var allAuthors = authorService.allAuthors();
                        System.out.println(allAuthors);
                        break;
                    case 4:
                        scanner.nextLine();
                        int ops;
                        System.out.println("Ingrese por que genero desea buscar el genero que desea buscar");
                        System.out.println("""
                                1.Ficcion
                                2.Historias de ninios
                                3.Historias de amor
                                4.Fantasia Ficcion
                                5.Desconocido""");
                        ops = scanner.nextInt();
                        scanner.nextLine();
                        if (ops > 0 && ops < 6) {
                            var booksByGender = authorService.listByTopic(Gender.getGender(ops));
                            if (!booksByGender.isEmpty()) {
                                booksByGender.forEach(System.out::println);
                            }
                        } else {
                            System.out.println(ColoursConsole.paintFont(ColoursConsole.YELLOW, "No ha ingresado un genero valido intentelo nuevamente"));

                        }
                        //Thread.sleep(3000);
                        break;
                    case 5:
                        scanner.nextLine();
                        var top5Books = authorService.top5Books();
                        if (top5Books.isEmpty()) {
                            System.out.println(ColoursConsole.paintFont("yellow", "no se encontraron libros"));
                            break;
                        }
                        top5Books.forEach(System.out::println);
                        break;
                    case 6:
                        scanner.nextLine();
                        int year;
                        System.out.println("Ingrese el anio que desea buscar");
                        year = scanner.nextInt();
                        scanner.nextLine();
                        if (year <= 0) {
                            System.out.println("no ha ingresado un valor valido");
                            break;
                        }
                        var authorsByYear = authorService.listAuthorByYear(year);
                        authorsByYear.forEach(System.out::println);
                        break;
                    case 7:
                        scanner.nextLine();
                        subMenuLanguages();
                        break;
                    case 8:
                        scanner.nextLine();
                        String stadistic=authorService.booksStadistics();
                        System.out.println(stadistic);
                        break;
                    case 9:
                        scanner.nextLine();
                        String authorName;
                        System.out.println("Ingrese el nombre del autor");
                        authorName=scanner.nextLine();
                        var author=authorService.searchAuthorByName(authorName);
                        if(author!=null){
                            System.out.println(author);
                        }else{
                            System.out.println(ColoursConsole.paintFont("YELLOW","Lo sentimos,autor no encontrado"));
                        }
                        break;
                    case 10:

                    default:
                        System.out.println("No ha seleccionado una opcion correcta");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("no ha ingresado una opcion valida intentelo nuevamente");
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("hilo bloqueado 4");
                System.out.println(e.getMessage());
                System.out.println("err");
            }finally {
                scanner.nextLine();
            }

        } while (op != 0);
        scanner.close();
    }

    private void subMenuLanguages() {
        scanner.nextLine();
        String codeLan = "";
        System.out.println("""
                Selecciones el idioma del libro que desea buscar
                1.English
                2.Español
                3.Français
                4.Deutsch""");
        switch (scanner.nextInt()) {
            case 1 -> codeLan = "en";
            case 2 -> codeLan = "es";
            case 3 -> codeLan = "fr";
            case 4 -> codeLan = "de";
            default ->{ System.out.println("opcion incorrecta intentelo nuevamente");
                scanner.nextLine();
            }
        }
        if (!codeLan.isEmpty()) {
            var listByLang = authorService.listByLanguage(codeLan);
            listByLang.forEach(System.out::println);

        }

    }
}
