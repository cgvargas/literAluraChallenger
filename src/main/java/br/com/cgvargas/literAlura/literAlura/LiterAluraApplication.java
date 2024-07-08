package br.com.cgvargas.literAlura.literAlura;

import br.com.cgvargas.literAlura.literAlura.principal.Main;
import br.com.cgvargas.literAlura.literAlura.repository.RepositorioAutor;
import br.com.cgvargas.literAlura.literAlura.repository.RepositorioLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	RepositorioAutor repositorioAutor;
	@Autowired
	RepositorioLivro repositorioLivro;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorioAutor, repositorioLivro);
		main.principal();
	}
}
