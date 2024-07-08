package br.com.cgvargas.literAlura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int dataNascimento;
    private int dataFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livros> livros = new ArrayList<>();

    public Autor(){}
    public Autor(DadosAutor dados) {
        this.nome = dados.nomeDoAutor();
        this.dataNascimento = dados.anoNascimento();
        this.dataFalecimento = dados.anoFalecimento();
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

    public int getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(int dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(int dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }

    public List<Livros> getLivros() {
        return livros;
    }

    public void setLivros(List<Livros> livros) {
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "---------------------------------------"+
                "\nNome: " + nome +
                "\nNascimento: " + dataNascimento +
                "\nFalecimento: " + dataFalecimento+
                "\n---------------------------------------";
    }
}
