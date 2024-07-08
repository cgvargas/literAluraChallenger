package br.com.cgvargas.literAlura.literAlura.principal;

import br.com.cgvargas.literAlura.literAlura.model.Autor;
import br.com.cgvargas.literAlura.literAlura.model.DadosAutor;
import br.com.cgvargas.literAlura.literAlura.model.DadosLivro;
import br.com.cgvargas.literAlura.literAlura.model.Livros;
import br.com.cgvargas.literAlura.literAlura.repository.RepositorioAutor;
import br.com.cgvargas.literAlura.literAlura.repository.RepositorioLivro;
import br.com.cgvargas.literAlura.literAlura.service.ApiConsumer;
import br.com.cgvargas.literAlura.literAlura.service.Conversor;
import br.com.cgvargas.literAlura.literAlura.service.IConversor;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private ApiConsumer requisicao = new ApiConsumer();
    private RepositorioAutor repositorioAutor;
    private RepositorioLivro repositorioLivro;
    private List<Livros> livros = new ArrayList<>();
    private IConversor conversor = new Conversor();
    private final String ADDRESS = "https://gutendex.com/books?search=";

    public Main(RepositorioAutor repositorioAutor, RepositorioLivro repositorioLivro) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLivro = repositorioLivro;
    }

    public void principal() {
        String menu = """
                \n=============================================
                1 - Buscar livro pelo titulo
                2 - Listar livros cadastrados
                3 - Listar autores cadastrados
                4 - Listar autores vivos no ano
                5 - Listar livros por idioma
                6 - Top 10\s
                7 - Buscar autores pelo nome
                8 - Quantidade de downloads por autor
                
                0 - Sair\\n
                =============================================
                """;
        var opcao = -1;
        while (opcao != 0) {
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> buscarNovoLivro();
                case 2 -> buscarLivrosRegistrados();
                case 3 -> buscarAutoresRegistrados();
                case 4 -> buscarAutoresVivosPorAno();
                case 5 -> buscarLivrosPorIdioma();
                case 6 -> buscarTop10();
                case 7 -> buscarAutorPorNome();
                case 8 -> mediaDeDownloadsPorAutor();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("\n\n***Opção Inválida***\n\n");
            }
        }
    }

    private void listarAutoresCadastrados() {
        var autores = repositorioAutor.findAll();
        if (!autores.isEmpty()) {
            System.out.println("\nAutores cadastrados no banco de dados:");
            autores.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado no banco de dados!");
        }
    }

    private void buscarNovoLivro() {
        System.out.println("\nQual livro deseja buscar?");
        var buscaDoUsuario = sc.nextLine();
        var dados = requisicao.consumo(ADDRESS + buscaDoUsuario.replace(" ", "%20"));
        salvarNoDb(dados);
    }

    private void salvarNoDb(String dados) {
        try {
            System.out.println("Dados recebidos: Resumo dos dados recebidos");

            List<DadosLivro> dadosLivros = conversor.getListaData(dados, DadosLivro.class);
            List<Livros> livrosNovos = new ArrayList<>();
            List<String> livrosExistentes = new ArrayList<>();

            for (DadosLivro dadosLivro : dadosLivros) {
                if (dadosLivro != null && dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
                    DadosAutor dadosAutor = dadosLivro.autores().get(0);

                    Livros livro = new Livros(dadosLivro);
                    Autor autor = new Autor(dadosAutor);

                    Autor autorDb = repositorioAutor.existsByNome(autor.getNome()) ?
                            repositorioAutor.findByNome(autor.getNome()) : repositorioAutor.save(autor);

                    livro.setAutor(autorDb);

                    if (!repositorioLivro.existsByNome(livro.getNome())) {
                        repositorioLivro.save(livro);
                        livrosNovos.add(livro);
                    } else {
                        livrosExistentes.add(livro.getNome());
                    }
                } else {
                    System.out.println("*** Dados do livro ou autor são nulos ou incompletos ***");
                }
            }

            if (!livrosExistentes.isEmpty()) {
                System.out.println("\nEste título  já está cadastrado no banco de dados!");
            }

            // Exibir livros novos salvos
            if (!livrosNovos.isEmpty()) {
                System.out.println("\nLivros novos salvos no banco de dados:");
                livrosNovos.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println("\n\n*** Erro ao processar o livro ***\n\n");
            e.printStackTrace();
        }
    }

    private void buscarLivrosRegistrados() {
        var bucasDB = repositorioLivro.findAll();
        if (!bucasDB.isEmpty()) {
            System.out.println("\nLivros cadastrados no banco de dados: ");
            bucasDB.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum livro encontrado no banco de dados!");
        }
    }

    private void buscarAutoresRegistrados() {
        var buscaDb = repositorioAutor.findAll();
        if (!buscaDb.isEmpty()) {
            System.out.println("\nAutores cadastrados no banco de dados:");
            buscaDb.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado no banco de dados!");
        }
    }

    private void buscarAutoresVivosPorAno() {
        System.out.print("\nInforme o ano: ");
        var ano = sc.nextInt();
        sc.nextLine();
        var buscaAutoresNoDb = repositorioAutor.buscarAnoFalecimento(ano);
        if (!buscaAutoresNoDb.isEmpty()) {
            System.out.println("\n\nAtores vivos no ano de: " + ano);
            buscaAutoresNoDb.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado para esta data!");
        }
    }

    private void buscarLivrosPorIdioma() {
        var idiomasCadastrados = repositorioLivro.bucaridiomas();
        System.out.println("\nIdiomas cadastrados no banco:");
        idiomasCadastrados.forEach(System.out::println);
        System.out.println("\nSelecione um dos idiomas cadastrados no banco:\n");
        var idiomaSelecionado = sc.nextLine();
        repositorioLivro.buscarPorIdioma(idiomaSelecionado).forEach(System.out::println);
    }

    private void buscarTop10() {
        var top10 = repositorioLivro.findTop10ByOrderByQuantidadeDeDownloadsDesc();
        top10.forEach(System.out::println);
    }

    private void buscarAutorPorNome() {
        System.out.println("Qual o nome do autor?");
        var pesquisa = sc.nextLine();
        var autor = repositorioAutor.encontrarNome(pesquisa);
        if (!autor.isEmpty()) {
            autor.forEach(System.out::println);
        } else {
            System.out.println("*** Autor não encontrado! ***");
        }
    }

    private void mediaDeDownloadsPorAutor() {
        System.out.println("Qual autor deseja buscar?");
        var pesquisa = sc.nextLine();
        var test = repositorioLivro.encontrarLivrosPorAutor(pesquisa);
        DoubleSummaryStatistics media = test.stream()
                .mapToDouble(Livros::getQuantidadeDeDownloads)
                .summaryStatistics();
        System.out.println("Média de Downloads: " + media.getAverage());
    }
}
