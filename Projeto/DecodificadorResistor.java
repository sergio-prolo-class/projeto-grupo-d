import java.util.Scanner;
import java.util.Locale;
import java.util.Set;

import calculo.CalculoResistencia;
import formatacao.FormatadorResistencia;

public class DecodificadorResistor {

    // Define as cores válidas para faixas de DÍGITO (1ª, 2ª e 3ª faixas quando aplicável)
    private static final Set<String> DIGITOS_VALIDOS = Set.of(
           "preto", "marrom", "vermelho", "laranja", "amarelo",
            "verde", "azul", "violeta", "cinza", "branco",
            "roxo", "rosa"
    );
    
    
      // Define as cores válidas para faixa de MULTIPLICADOR (ouro/prata também são permitidos)
    private static final Set<String> MULTIPLICADORES_VALIDOS = Set.of(
            "preto", "marrom", "vermelho", "laranja", "amarelo",
            "verde", "azul", "violeta", "cinza", "branco",
            "ouro", "dourado", "prata",
            "roxo", "rosa"
    );
   
    // Define as cores válidas para faixa de TOLERÂNCIA (sempre a última em 4/5, penúltima em 6)
    private static final Set<String> TOLERANCIAS_VALIDAS = Set.of(
            "marrom", "vermelho", "verde", "azul", "violeta",
            "cinza", "ouro", "dourado", "prata",
            "roxo", "rosa"
    );

    // Define as cores válidas para faixa TEMPCO (somente em resistores de 6 faixas)
    private static final Set<String> TEMPCO_VALIDOS = Set.of(
            "marrom", "vermelho", "laranja", "amarelo"
    );    

    public static void main(String[] args) {

        // Se vierem argumentos, processa UM resistor pelo args (modo linha de comando)
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Se não vierem args, lê da entrada padrão linha a linha (modo redirecionamento de arquivo)
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {

                // Lê uma linha do arquivo/entrada
                String linha = sc.nextLine().trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Divide a linha em cores separadas por espaços
                String[] cores = linha.split("\\s+");

                // Processa o resistor da linha atual
                processarResistor(cores);
            }
        }
    }

    private static void processarResistor(String[] cores) {
        try {
            // Normaliza as cores (trim + minúsculo), evitando problemas com entrada do usuário
            normalizar(cores);

            // Valida se o número de faixas é 4, 5 ou 6
            validarNumeroFaixas(cores.length);

            // Valida se cada cor é válida para a posição específica (dígito/multiplicador/tolerância/tempco)
            validarCoresPorPosicao(cores);

            // Calcula o resultado completo (valor em ohms, tolerância e possivelmente tempco)
            CalculoResistencia.Resultado r = CalculoResistencia.calcular(cores);

            // Imprime a primeira linha exigida pelo gabarito: valor inteiro em Ohms
            System.out.println(r.valorOhmsInteiro);

            // Imprime a segunda linha exigida pelo gabarito: valor formatado + tolerância (+ tempco se existir)
            System.out.println(FormatadorResistencia.formatarSaida(r));

        } catch (IllegalArgumentException e) {
            // Imprime a mensagem de erro exigida no formato do enunciado
            System.out.println(e.getMessage());

            // Encerra a execução ao encontrar erro (comportamento esperado em validação)
            System.exit(1);
        }
    }

    private static void normalizar(String[] cores) {
        // Converte cada entrada para formato padrão: sem espaços extras e em minúsculo
        for (int i = 0; i < cores.length; i++) {
            cores[i] = (cores[i] == null)
                    ? ""
                    : cores[i].trim().toLowerCase(Locale.ROOT);
        }
    }

    private static void validarNumeroFaixas(int n) {
        // Aceita apenas resistores com 4, 5 ou 6 faixas
        if (n != 4 && n != 5 && n != 6) {
            throw new IllegalArgumentException(
                    "Argumentos inválidos: número total de faixas (" + n + ") não é válido"
            );
        }
    }

    private static void validarCoresPorPosicao(String[] cores) {
        int n = cores.length;

        // Para 4 faixas: 2 dígitos, multiplicador, tolerância
        if (n == 4) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarMultiplicador(cores[2], 3);
            validarTolerancia(cores[3], 4);
            return;
        }

        // Para 5 faixas: 3 dígitos, multiplicador, tolerância
        if (n == 5) {
            validarDigito(cores[0], 1);
            validarDigito(cores[1], 2);
            validarDigito(cores[2], 3);
            validarMultiplicador(cores[3], 4);
            validarTolerancia(cores[4], 5);
            return;
        }

        // Para 6 faixas: 3 dígitos, multiplicador, tolerância, tempco
        validarDigito(cores[0], 1);
        validarDigito(cores[1], 2);
        validarDigito(cores[2], 3);
        validarMultiplicador(cores[3], 4);
        validarTolerancia(cores[4], 5);
        validarTempco(cores[5], 6);
    }

    private static void validarDigito(String cor, int posicao) {
        // Confere se a cor é válida para posição de dígito
        if (!DIGITOS_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarMultiplicador(String cor, int posicao) {
        // Confere se a cor é válida para posição de multiplicador
        if (!MULTIPLICADORES_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarTolerancia(String cor, int posicao) {
        // Confere se a cor é válida para posição de tolerância
        if (!TOLERANCIAS_VALIDAS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void validarTempco(String cor, int posicao) {
        // Confere se a cor é válida para posição de coeficiente de temperatura (apenas 6 faixas)
        if (!TEMPCO_VALIDOS.contains(cor)) {
            erroCorPosicao(cor, posicao);
        }
    }

    private static void erroCorPosicao(String cor, int posicao) {
        // Lança erro com a mensagem exigida pelo enunciado (posição é 1-based)
        throw new IllegalArgumentException(
                "Argumentos inválidos: cor da faixa (" + cor + ") não é válida para sua posição (" + posicao + ")"
        );
    }
}
