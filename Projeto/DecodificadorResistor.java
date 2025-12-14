// Importa Scanner para leitura da entrada padrão (stdin)
import java.util.Scanner;

// Importa Locale para normalização (minúsculas com padrão regional estável)
import java.util.Locale;

// Importa Set para armazenar conjuntos de cores válidas
import java.util.Set;

// Classe principal do programa
public class DecodificadorResistor {

    // Conjunto de cores válidas para faixas de DÍGITO
    // Usadas nas posições 1 e 2 (4 faixas) ou 1,2,3 (5 e 6 faixas)
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

    // Conjunto de cores válidas para TEMPCO (coeficiente de temperatura)
    // Usado somente em resistores de 6 faixas
    private static final Set<String> TEMPCO_VALIDOS = Set.of(
            "marrom", "vermelho", "laranja", "amarelo"
    );

    // Método principal do programa
    public static void main(String[] args) {

        // Se o programa for executado com argumentos, processa um único resistor
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Caso contrário, lê várias linhas da entrada padrão (stdin)
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {

                // Lê uma linha e remove espaços extras
                String linha = sc.nextLine().trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Separa as cores por espaços
                String[] cores = linha.split("\\s+");

                // Processa o resistor daquela linha
                processarResistor(cores);
            }
        }
    }

    // Método responsável por processar um resistor
    private static void processarResistor(String[] cores) {

        // Normaliza as cores (trim + minúsculas)
        normalizar(cores);

        // Valida se o número de faixas é suportado (4, 5 ou 6)
        validarNumeroFaixas(cores.length);

        // Valida se as cores são compatíveis com a posição que ocupam
        validarCoresPorPosicao(cores);

        // Neste commit ainda não calculamos: apenas confirmamos que passou pelas validações
        System.out.println("OK");
    }

    // Normaliza todas as cores recebidas
    private static void normalizar(String[] cores) {
        for (int i = 0; i < cores.length; i++) {
            cores[i] = (cores[i] == null)
                    ? ""
                    : cores[i].trim().toLowerCase(Locale.ROOT);
        }
    }

    // Valida o número total de faixas do resistor
    private static void validarNumeroFaixas(int n) {
        if (n != 4 && n != 5 && n != 6) {
            throw new IllegalArgumentException(
                    "Argumentos inválidos: número total de faixas (" + n + ") não é válido"
            );
        }
    }

    // Valida cores conforme a regra de cada tipo de resistor (4, 5 ou 6 faixas)
    private static void validarCoresPorPosicao(String[] cores) {
        int n = cores.length;

        // 4 faixas: 2 dígitos + multiplicador + tolerância
        if (n == 4) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarMultiplicador(cores[2], 3);
            validarTolerancia(cores[3], 4);
            return;
        }

        // 5 faixas: 3 dígitos + multiplicador + tolerância
        if (n == 5) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarDigito(cores[2], 3);
            validarMultiplicador(cores[3], 4);
            validarTolerancia(cores[4], 5);
            return;
        }

        // 6 faixas: 3 dígitos + multiplicador + tolerância + tempco
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

    // Verifica se uma cor é válida para a posição de tempco (6 faixas)
    private static void validarTempco(String cor, int posicao) {
        if (!TEMPCO_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    // Lança uma exceção com mensagem padronizada do enunciado
    private static void erroCorPosicao(String cor, int posicao) {
        throw new IllegalArgumentException(
                "Argumentos inválidos: cor da faixa (" + cor + ") não é válida para sua posição (" + posicao + ")"
        );
    }
}