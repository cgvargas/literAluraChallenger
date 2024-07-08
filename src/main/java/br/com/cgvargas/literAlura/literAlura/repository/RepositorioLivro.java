package br.com.cgvargas.literAlura.literAlura.repository;

import br.com.cgvargas.literAlura.literAlura.model.Livros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioLivro extends JpaRepository<Livros, Long> {
    boolean existsByNome(String nome);

    @Query("SELECT DISTINCT b.idioma FROM Livros b ORDER BY b.idioma")
    List<String> bucaridiomas();

    @Query("SELECT b FROM Livros b WHERE idioma = :idiomaSelecionado")
    List<Livros> buscarPorIdioma(String idiomaSelecionado);

    @Query("SELECT b FROM Livros b WHERE LOWER(b.nome) = LOWER(:nome)")
    Livros findByNome(@Param("nome") String nome);

    List<Livros> findTop10ByOrderByQuantidadeDeDownloadsDesc();

    @Query("SELECT b FROM Livros b WHERE b.autor.nome ILIKE %:pesquisa%")
    List<Livros> encontrarLivrosPorAutor(String pesquisa);
}
