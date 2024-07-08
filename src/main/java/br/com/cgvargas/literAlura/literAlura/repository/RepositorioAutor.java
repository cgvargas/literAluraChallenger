package br.com.cgvargas.literAlura.literAlura.repository;

import br.com.cgvargas.literAlura.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioAutor extends JpaRepository<Autor, Long> {
    Boolean existsByNome(String nome);

    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.dataFalecimento >= :ano AND :ano >= a.dataNascimento")
    List<Autor> buscarAnoFalecimento(@Param("ano") int anoSelecionado);

    @Query("SELECT a FROM Autor a WHERE a.nome ILIKE %:pesquisa%")
    List<Autor> encontrarNome(@Param("pesquisa") String pesquisa);
}
