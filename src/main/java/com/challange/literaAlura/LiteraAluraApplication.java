package com.challange.literaAlura;

import com.challange.literaAlura.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}