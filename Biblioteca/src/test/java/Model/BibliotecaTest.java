package Model;

import Model.Biblioteca;
import Model.Livro;
import Model.Usuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibliotecaTest {
    private Biblioteca biblioteca;
    private Livro livro1;
    private Livro livro2;
    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        biblioteca = new Biblioteca();

        livro1 = new Livro("123456", "Dom Casmurro", "Machado de Assis", 1899);
        livro2 = new Livro("789012", "O Cortiço", "Aluísio Azevedo", 1890);

        usuario1 = new Usuario("U001", "João Silva", "joao@email.com");
        usuario2 = new Usuario("U002", "Maria Santos", "maria@email.com");

        // TODO: Adicionar livros e usuários à biblioteca
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.registrarUsuario(usuario1);
        biblioteca.registrarUsuario(usuario2);

        System.out.println("👌 Cenário de Teste está preparado.");

        /// fiz isso dai gabssss espero ter ajudado : )
    }

    @AfterEach
    void limparTeste() {
        String[] mensagens = {"\uD83D\uDCDD Testado e Preparado!", "\uD83E\uDDE0 Testado com Sucesso!", "✅ Teste Finalizado!"};
        System.out.println(mensagens[(int) (Math.random() * mensagens.length)]);
    }

    @BeforeAll
    static void iniciarTestes() {
        System.out.println("🚀 Iniciando os testes...");
    }

    @AfterAll
    static void testesFinalizados(){
        System.out.println("✅ Todos os Testes Concluídos!");
    }

    @Test
    @DisplayName("✅ Deve cadastrar livro com sucesso")
    void testCadastrarLivro() {
        // TODO: Implementar teste para cadastro de livro
        // Verificar se livro foi adicionado à biblioteca

        Livro livroNovo = new Livro("183646", "O Roubo do Livro", "O Ladrão", 1001);

        biblioteca.adicionarLivro(livroNovo);
        List<Livro> livros = biblioteca.listarLivrosDisponiveis();

        assertTrue(livros.contains(livroNovo));
    }

    @Test
    @DisplayName("❌ Deve lançar exceção ao cadastrar livro nulo")
    void testCadastrarLivroNulo() {
        // TODO: Implementar teste para livro nulo
        // Usar assertThrows para verificar exceção
        assertThrows(IllegalArgumentException.class, () -> biblioteca.adicionarLivro(null));
    }

    @Test
    @DisplayName("✅ Deve realizar empréstimo com sucesso")
    void testEmprestarLivro() {
        // TODO: Implementar teste de empréstimo bem-sucedido
        // Verificar se livro ficou indisponível
        biblioteca.adicionarLivro(livro1);
        biblioteca.registrarUsuario(usuario1);

        assertTrue(biblioteca.emprestarLivro("123456", "U001", LocalDate.now()), "O Empréstimo deveria ter sido concluído");
    }

    @Test
    @DisplayName("❌ Não deve emprestar livro indisponível")
    void testEmprestarLivroIndisponivel() {
        // TODO: Implementar teste para livro já emprestado
        biblioteca.adicionarLivro(livro1);
        biblioteca.registrarUsuario(usuario1);

        biblioteca.emprestarLivro("123456", "U001", LocalDate.now());

        assertFalse(biblioteca.emprestarLivro("123456", "U001", LocalDate.now()), "O Empréstimo deveria ter sido concluído");
    }

    @ParameterizedTest
    @DisplayName("💰 Deve calcular multa corretamente")
    @ValueSource(ints = {0, 5, 10}) // Dias de atraso
    void testCalcularMulta(int diasAtraso) {
        // TODO: Implementar teste parametrizado para cálculo de multa
        // Dica: usar LocalDate.now().plusDays(diasAtraso) para data de devolução
        biblioteca.emprestarLivro("123456", "U001", LocalDate.now());

        LocalDate dataDevolucao = LocalDate.now().plusDays(14+diasAtraso);

        assertEquals(biblioteca.devolverLivro("123456", dataDevolucao), (double) diasAtraso*2);
    }

    @Test
    @DisplayName("📚 Deve listar apenas livros disponíveis")
    void testListarLivrosDisponiveis() {
        // TODO: Implementar teste para listagem de livros disponíveis
        // Emprestar um livro e verificar se não aparece na lista
        List<Livro> livros = biblioteca.listarLivrosDisponiveis();

        assertTrue(biblioteca.listarLivrosDisponiveis().contains(livro1), "O Livro deveria estar disponível!");

        biblioteca.emprestarLivro("123456", "U001", LocalDate.now());

        assertFalse(biblioteca.listarLivrosDisponiveis().contains(livro1), "O Livro não deveria estar mais disponível!");
    }

    @Test
    @DisplayName("⏰ Deve bloquear empréstimo para usuário com multa")
    void testUsuarioComMulta() {
        // TODO: Implementar teste para usuário com multa
        // Configurar usuário com multa e tentar empréstimo
        usuario1.setPossuiMulta(true);

        assertFalse(biblioteca.emprestarLivro("123456", "U001", LocalDate.now()), "O Empréstimo deveria ter sido bloqueado!");
    }
}