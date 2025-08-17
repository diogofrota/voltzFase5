
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {
    static List<Usuario> usuarios = new ArrayList<>();
    static List<Aplicacao> aplicacoes = new ArrayList<>();
    static List<Criptomoeda> criptomoedas = new ArrayList<>();
    static Map<Usuario, List<Aplicacao>> historicoAplicacoes = new HashMap<>();
    static Map<Criptomoeda, Double> cotacoes = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSistema();
        menuPrincipal();
    }

    static void inicializarSistema() {
        Usuario user = new Usuario("diogo", "123.456.789-10", "21-976709166", "diogofrota@icloud.com", "1234");
        usuarios.add(user);
        criptomoedas.add(new Criptomoeda("Bitcoin", "BTC", 493588.38));
        criptomoedas.add(new Criptomoeda("Ethereum", "ETH", 9194.27));

        for (Criptomoeda cripto : criptomoedas) {
            cotacoes.put(cripto, cripto.getValorAtual());
        }
    }

    static void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Acesso Cliente");
            System.out.println("2. Acesso Banco/Admin");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> acessoCliente();
                case 2 -> acessoBanco();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void acessoCliente() {
        System.out.print("Digite seu e-mail: ");
        String email = scanner.nextLine();
        Usuario cliente = buscarUsuario(email);

        if (cliente == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver Aplicações");
            System.out.println("2. Nova Aplicação");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> listarAplicacoesUsuario(cliente);
                case 2 -> novaAplicacao(cliente);
                case 0 -> {
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void acessoBanco() {
        int opcao;
        do {
            System.out.println("\n=== MENU BANCO/ADMIN ===");
            System.out.println("1. Cadastrar Criptomoeda");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Listar Aplicações");
            System.out.println("4. Gerar relatório de aplicações");
            System.out.println("5. Atualizar Preço Criptomoeda");
            System.out.println("6. Excluir Criptomoeda");
            System.out.println("7. Listar Criptomoedas Cadastradas");

            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCriptomoeda();
                case 2 -> listarUsuarios();
                case 3 -> listarTodasAplicacoes();
                case 4 -> gerarRelatorio();
                case 5 -> atualizarPrecoCriptomoeda();
                case 6 -> excluirCriptomoeda();
                case 7 -> listarCriptomoedas();
                case 0 -> {
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static Usuario buscarUsuario(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    static void listarUsuarios() {
        System.out.println("\n=== USUÁRIOS CADASTRADOS ===");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    static void listarTodasAplicacoes() {
        System.out.println("\n=== TODAS AS APLICAÇÕES ===");
        for (Aplicacao a : aplicacoes) {
            System.out.println(a.consultaAplicacao());
        }
    }

    static void listarAplicacoesUsuario(Usuario usuario) {
        System.out.println("\n=== SUAS APLICAÇÕES ===");
        List<Aplicacao> lista = historicoAplicacoes.getOrDefault(usuario, new ArrayList<>());
        for (Aplicacao a : lista) {
            System.out.println(a.consultaAplicacao());
        }
    }

    static void novaAplicacao(Usuario usuario) {
        System.out.println("\nEscolha uma criptomoeda:");
        for (int i = 0; i < criptomoedas.size(); i++) {
            Criptomoeda c = criptomoedas.get(i);
            System.out.printf("%d. %s (%s) - R$ %.2f\n", i + 1, c.getNome(), c.getSigla(), c.getValorAtual());
        }
        System.out.print("Digite o número da criptomoeda: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice < 0 || indice >= criptomoedas.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Criptomoeda escolhida = criptomoedas.get(indice);

        System.out.print("Digite o valor a investir (R$): ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        Aplicacao nova = new Aplicacao(usuario, escolhida, valor);
        aplicacoes.add(nova);
        historicoAplicacoes.putIfAbsent(usuario, new ArrayList<>());
        historicoAplicacoes.get(usuario).add(nova);
        System.out.println("Aplicação realizada com sucesso!");
    }

    static void cadastrarCriptomoeda() {

        System.out.print("Nome da criptomoeda: ");
        String nome = scanner.nextLine();

        System.out.print("Sigla: ");
        String sigla = scanner.nextLine();

        System.out.print("Valor atual (R$): ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // consome o \n

        // --- ABRE CONEXÃO AQUI ---
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Se quiser apenas testar:
            System.out.println("Conectado ao Oracle!");

            // Se já quiser persistir no BD:
            String sql = "INSERT INTO CRIPTOMOEDA (NOME, SIGLA, VALOR_ATUAL) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nome);
                ps.setString(2, sigla);
                ps.setBigDecimal(3, BigDecimal.valueOf(valor));
                int linhas = ps.executeUpdate();
                System.out.println(linhas + " registro(s) inserido(s) no banco.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar no banco: " + e.getMessage());
        }

        // Se você também mantém a lista em memória:
        // Criptomoeda nova = new Criptomoeda(nome, sigla, valor);
        // criptomoedas.add(nova);
        // cotacoes.put(nova, valor);

        System.out.println("Criptomoeda cadastrada com sucesso!");


    }

    static void gerarRelatorio() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("relatorio.txt"))) {
            bw.write("=============== Relatório Geral do Banco Voltz ===============\n");
            bw.write("Total de usuários cadastrados: " + usuarios.size() + "\n");
            bw.write("Total de criptomoedas cadastradas: " + criptomoedas.size() + "\n");
            bw.write("Total de aplicações realizadas: " + aplicacoes.size() + "\n");
            bw.write("==============================================================\n\n");

            for (Aplicacao a : aplicacoes) {
                bw.write(a.consultaAplicacao());
            }

            System.out.println("Relatório gerado com sucesso em relatorio.txt");
        } catch (IOException e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    static void atualizarPrecoCriptomoeda() {
        System.out.print("Informe a SIGLA da cripto: ");
        String sigla = scanner.nextLine();

        System.out.print("Novo valor (R$): ");
        double novoValor = scanner.nextDouble();
        scanner.nextLine(); // consome o \n

        String sql = "UPDATE CRIPTOMOEDA SET VALOR_ATUAL = ? WHERE UPPER(SIGLA) = UPPER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, BigDecimal.valueOf(novoValor));
            ps.setString(2, sigla);

            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                System.out.println("Nenhuma criptomoeda encontrada com a sigla: " + sigla);
            } else {
                System.out.println("Preço atualizado com sucesso para " + sigla + "!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar preço: " + e.getMessage());
        }
    }



    static  void excluirCriptomoeda(){
        System.out.print("Informe a SIGLA da cripto para excluir: ");
        String sigla = scanner.nextLine();

        String sql = "DELETE FROM CRIPTOMOEDA WHERE UPPER(SIGLA) = UPPER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sigla);

            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                System.out.println("Nenhuma criptomoeda encontrada com a sigla: " + sigla);
            } else {
                System.out.println("Criptomoeda " + sigla + " excluída com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir criptomoeda: " + e.getMessage());
        }

    }


    static void listarCriptomoedas() {
        String sql = "SELECT ID_CRIPT, NOME, SIGLA, VALOR_ATUAL FROM CRIPTOMOEDA ORDER BY ID_CRIPT";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== Lista de Criptomoedas ===");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                int id = rs.getInt("ID_CRIPT");
                String nome = rs.getString("NOME");
                String sigla = rs.getString("SIGLA");
                double valor = rs.getDouble("VALOR_ATUAL");

                System.out.printf("ID: %d | Nome: %-15s | Sigla: %-5s | Valor Atual: R$ %.2f%n",
                        id, nome, sigla, valor);
            }

            if (!encontrou) {
                System.out.println("Nenhuma criptomoeda cadastrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar criptomoedas: " + e.getMessage());
        }
    }

}