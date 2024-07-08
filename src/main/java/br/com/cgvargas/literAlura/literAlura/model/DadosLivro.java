package br.com.cgvargas.literAlura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title")String nomeDoLivro,
                         @JsonAlias("download_count") Integer quantidadeDeDownloads,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("authors") List<DadosAutor> autores) {
    public String json() {
        return "";
    }
}
