package Projeto;

// Importa a classe Scanner para leitura da entrada padrão (stdin)
import java.util.Scanner;

// Importa Locale para garantir conversão correta para minúsculas,
// independentemente da configuração regional do sistema
import java.util.Locale;

// Importa Set para armazenar conjuntos de cores válidas
import java.util.Set;

// Importa a classe responsável pelo cálculo elétrico do resistor
import calculo.CalculoResistencia;

// Importa a classe responsável por formatar a saída
import formatacao.FormatadorResistencia;

// Classe principal do programa
public class DecodificadorResistor {

    // Conjunto de cores válidas para faixas de DÍGITO
    // Usadas nas posições iniciais do resistor
    private static final Set<String> DIGITOS_VALIDOS = Set.of(
            "preto", "marrom", "vermelho", "laranja", "amarelo",
            "verde", "azul", "violeta", "cinza", "branco",
            "roxo", "rosa"
    );

    // Conjunto de cores válidas para a faixa de MULTIPLICADOR
    private static final Set<String> MULTIPLICADORES_VALIDOS = Set.of(
            "preto", "marrom", "vermelho", "laranja", "amarelo",
            "verde", "azul", "violeta", "cinza", "branco",
            "roxo", "rosa",
            "ouro", "dourado", "prata"
    );

    // Conjunto de cores válidas para a faixa de TOLERÂNCIA
    private static final Set<String> TOLERANCIAS_VALIDAS = Set.of(
            "marrom", "vermelho", "verde", "azul", "violeta",
            "cinza", "roxo", "rosa",
            "ouro", "dourado", "prata"
    );

    // Conjunto de cores válidas para o coeficiente de temperatura (TEMPCO)
    // Utilizado apenas em resistores de 6 faixas
    private static final Set<String> TEMPCO_VALIDOS = Set.of(
            "marrom", "vermelho", "laranja", "amarelo"
    );

    // Método principal do programa
    public static void main(String[] args) {

        // Se o programa for executado com argumentos de linha de comando,
        // trata esses argumentos como as cores de um único resistor
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Caso contrário, o programa passa a ler da entrada padrão (stdin),
        // permitindo o processamento de múltiplas linhas
        try (Scanner sc = new Scanner(System.in)) {

            // Lê a entrada enquanto houver linhas disponíveis
            while (sc.hasNextLine()) {

                // Lê uma linha completa e remove espaços extras
                String linha = sc.nextLine().trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Divide a linha em cores separadas por espaços
                String[] cores = linha.split("\\s+");

                // Processa o resistor representado pela linha atual
                processarResistor(cores);
            }
        }
    }

    // Método responsável por processar um único resistor.
    // Neste commit, ele passa a tratar erros e encerrar o programa
    // imediatamente ao encontrar a primeira entrada inválida.
    private static void processarResistor(String[] cores) {

        try {
            // Normaliza as cores para padronizar a entrada do usuário
            normalizar(cores);

            // Valida se o número total de faixas é permitido (4, 5 ou 6)
            validarNumeroFaixas(cores.length);

            // Valida se cada cor é compatível com a posição que ocupa
            validarCoresPorPosicao(cores);

            // Variável que armazenará o valor formado pelos dígitos do resistor
            int digitos;

            // Variável que armazenará o valor final da resistência em Ohms
            double valorOhms;

            // Verifica o número de faixas do resistor para decidir
            // quantos dígitos devem ser considerados no cálculo
            if (cores.length == 4) {

            // Caso do resistor de 4 faixas:
            // As duas primeiras faixas representam os dígitos significativos

            // Converte as duas primeiras cores em um número inteiro
            // Exemplo: marrom(1) e preto(0) → 10
            digitos = CalculoResistencia.calcularDigitos(
                cores[0],
                cores[1]
            );

            // Aplica o multiplicador representado pela terceira faixa
            // Exemplo: 10 × vermelho(100) → 1000 Ohms
            valorOhms = CalculoResistencia.aplicarMultiplicador(
                digitos,
                cores[2]
            );

            } else {

            // Caso do resistor de 5 ou 6 faixas:
            // As três primeiras faixas representam os dígitos significativos

            // Converte as três primeiras cores em um número inteiro
            // Exemplo: marrom(1), preto(0), vermelho(2) → 102
            digitos = CalculoResistencia.calcularDigitos(
                cores[0],
                cores[1],
                cores[2]
            );

            // Aplica o multiplicador representado pela quarta faixa
            valorOhms = CalculoResistencia.aplicarMultiplicador(
                digitos,
                cores[3]
            );
        }

            // PRIMEIRA LINHA DA SAÍDA:
            // Imprime o valor inteiro da resistência em Ohms,
            // conforme exigido no enunciado do desafio
            System.out.println((int) valorOhms);

            // SEGUNDA LINHA DA SAÍDA:
            // Imprime o valor formatado em Ohms, kOhms ou MOhms,
            // utilizando o formatador responsável pela apresentação
            System.out.println(FormatadorResistencia.formatar(valorOhms));


        } catch (IllegalArgumentException e) {

            // Em caso de erro de validação, imprime a mensagem
            // exatamente no formato exigido
            System.out.println(e.getMessage());

            // Encerra imediatamente o programa com código de erro,
            // conforme especificação do desafio
            System.exit(1);
        }
    }

    // Método responsável por normalizar todas as cores recebidas
    private static void normalizar(String[] cores) {

        // Percorre todas as posições do vetor de cores
        for (int i = 0; i < cores.length; i++) {

            // Remove espaços extras e converte para minúsculas;
            // se a posição for nula, substitui por string vazia
            cores[i] = (cores[i] == null)
                    ? ""
                    : cores[i].trim().toLowerCase(Locale.ROOT);
        }
    }

    // Valida se o número total de faixas do resistor é permitido
    private static void validarNumeroFaixas(int n) {
        if (n != 4 && n != 5 && n != 6) {
            throw new IllegalArgumentException(
                    "Argumentos inválidos: número total de faixas (" + n + ") não é válido"
            );
        }
    }

    // Valida as cores de acordo com a posição ocupada no resistor
    private static void validarCoresPorPosicao(String[] cores) {
        int n = cores.length;

        // Resistor de 4 faixas:
        // 2 dígitos, 1 multiplicador e 1 tolerância
        if (n == 4) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarMultiplicador(cores[2], 3);
            validarTolerancia(cores[3], 4);
            return;
        }

        // Resistor de 5 faixas:
        // 3 dígitos, 1 multiplicador e 1 tolerância
        if (n == 5) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarDigito(cores[2], 3);
            validarMultiplicador(cores[3], 4);
            validarTolerancia(cores[4], 5);
            return;
        }

        // Resistor de 6 faixas:
        // 3 dígitos, 1 multiplicador, 1 tolerância e 1 tempco
        validarDigito(cores[0], 1);
        validarDigito(cores[1], 2);
        validarDigito(cores[2], 3);
        validarMultiplicador(cores[3], 4);
        validarTolerancia(cores[4], 5);
        validarTempco(cores[5], 6);
    }

    // Verifica se uma cor é válida para uma posição de dígito
    private static void validarDigito(String cor, int posicao) {
        if (!DIGITOS_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    // Verifica se uma cor é válida para a posição de multiplicador
    private static void validarMultiplicador(String cor, int posicao) {
        if (!MULTIPLICADORES_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    // Verifica se uma cor é válida para a posição de tolerância
    private static void validarTolerancia(String cor, int posicao) {
        if (!TOLERANCIAS_VALIDAS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    // Verifica se uma cor é válida para a posição de tempco
    private static void validarTempco(String cor, int posicao) {
        if (!TEMPCO_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    // Lança uma exceção padronizada indicando erro de cor por posição
    private static void erroCorPosicao(String cor, int posicao) {
        throw new IllegalArgumentException(
                "Argumentos inválidos: cor da faixa (" + cor + ") não é válida para sua posição (" + posicao + ")"
        );
    }
}
