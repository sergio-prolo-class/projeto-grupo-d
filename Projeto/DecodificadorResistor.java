// Importa Scanner para leitura da entrada padrão (stdin)
import java.util.Scanner;

// Importa Locale para normalização de strings
import java.util.Locale;

// Importa Set para conjuntos de valores válidos
import java.util.Set;

// Importa a classe que executa o cálculo elétrico do resistor
import calculo.CalculoResistencia;

// Importa a classe que formata a saída conforme o enunciado
import formatacao.FormatadorResistencia;

// Classe principal do programa
public class DecodificadorResistor {

    // Conjunto de cores válidas para faixas de DÍGITO
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
    private static final Set<String> TEMPCO_VALIDOS = Set.of(
            "marrom", "vermelho", "laranja", "amarelo"
    );

    // Método principal do programa
    public static void main(String[] args) {

        // Se houver argumentos, trata como um único resistor
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Caso contrário, lê múltiplos resistores da entrada padrão
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {

                // Lê a linha atual e remove espaços nas extremidades
                String linha = sc.nextLine().trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Quebra a linha em cores
                String[] cores = linha.split("\\s+");

                // Processa o resistor representado pela linha
                processarResistor(cores);
            }
        }
    }

    // Processa um resistor (ainda sem encerramento imediato em caso de erro neste commit)
    private static void processarResistor(String[] cores) {

        // Normaliza as cores para padronizar entrada do usuário
        normalizar(cores);

        // Valida a quantidade de faixas
        validarNumeroFaixas(cores.length);

        // Valida a cor conforme a posição da faixa
        validarCoresPorPosicao(cores);

        // Executa o cálculo do resistor e obtém um resultado estruturado
        CalculoResistencia.Resultado r = CalculoResistencia.calcular(cores);

        // PRIMEIRA LINHA DO GABARITO: imprime o valor inteiro (truncado) em Ohms
        System.out.println(r.valorOhmsInteiro);

        // SEGUNDA LINHA DO GABARITO: imprime o texto formatado completo
        System.out.println(FormatadorResistencia.formatarSaida(r));
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

        if (n == 4) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarMultiplicador(cores[2], 3);
            validarTolerancia(cores[3], 4);
            return;
        }

        if (n == 5) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarDigito(cores[2], 3);
            validarMultiplicador(cores[3], 4);
            validarTolerancia(cores[4], 5);
            return;
        }

        validarDigito(cores[0], 1);
        validarDigito(cores[1], 2);
        validarDigito(cores[2], 3);
        validarMultiplicador(cores[3], 4);
        validarTolerancia(cores[4], 5);
        validarTempco(cores[5], 6);
    }

    private static void validarDigito(String cor, int posicao) {
        if (!DIGITOS_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarMultiplicador(String cor, int posicao) {
        if (!MULTIPLICADORES_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarTolerancia(String cor, int posicao) {
        if (!TOLERANCIAS_VALIDAS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarTempco(String cor, int posicao) {
        if (!TEMPCO_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void erroCorPosicao(String cor, int posicao) {
        throw new IllegalArgumentException(
                "Argumentos inválidos: cor da faixa (" + cor + ") não é válida para sua posição (" + posicao + ")"
        );
    }
}