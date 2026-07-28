package com.challange.literaAlura;

import com.challange.literaAlura.Principal;
import com.challange.literaAlura.repository.AutorRepository;
import com.challange.literaAlura.repository.LivroRepository;
import com.challange.literaAlura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AutorRepository autorRepository;
    @Autowired
    private LivroService livroService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livroRepository, autorRepository, livroService);
		principal.exibeMenu();
	}
}