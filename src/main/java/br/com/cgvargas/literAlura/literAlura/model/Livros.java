package br.com.cgvargas.literAlura.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name= "livros")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private String idioma;
    private Integer quantidadeDeDownloads;
    @ManyToOne
    private Autor autor;

    public Livros() {}

    public Livros(DadosLivro data) {
        this.nome = data.nomeDoLivro();
        this.idioma = String.join(",", data.idiomas());
        this.quantidadeDeDownloads = data.quantidadeDeDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getQuantidadeDeDownloads() {
        return quantidadeDeDownloads;
    }

    public void setQuantidadeDeDownloads(Integer quantidadeDeDownloads) {
        this.quantidadeDeDownloads = quantidadeDeDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        String autorNome = (this.autor != null) ? this.autor.getNome() : "Autor Desconhecido";
        return "Livro [titulo=" + nome + ", autor=" + autorNome + ", ...]";
    }
}
