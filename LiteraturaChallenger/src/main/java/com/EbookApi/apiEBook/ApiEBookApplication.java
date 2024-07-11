package com.EbookApi.apiEBook;


import com.EbookApi.apiEBook.menu.Menu;
import com.EbookApi.apiEBook.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiEBookApplication implements CommandLineRunner {

	@Autowired
	AuthorService authorService;

	public static void main(String[] args) {

		SpringApplication.run(ApiEBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu=new Menu(authorService);
		menu.startAPIMenu();
	}
}
