// Importa a classe Scanner para leitura da entrada padrão (stdin)
import java.util.Scanner;

// Importa Locale para garantir conversão correta para minúsculas,
// independentemente da configuração regional do sistema
import java.util.Locale;

// Classe principal do programa
public class DecodificadorResistor {

    // Método principal do programa
    public static void main(String[] args) {

        // Se o programa for executado com argumentos de linha de comando,
        // trata esses argumentos como as cores de um único resistor
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Caso não haja argumentos, o programa lê da entrada padrão (stdin),
        // permitindo processar várias linhas (ex.: redirecionamento de arquivo)
        try (Scanner sc = new Scanner(System.in)) {

            // Lê a entrada enquanto houver linhas disponíveis
            while (sc.hasNextLine()) {

                // Lê uma linha completa e remove espaços extras no começo e no fim
                String linha = sc.nextLine().trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Divide a linha em cores separadas por um ou mais espaços
                String[] cores = linha.split("\\s+");

                // Processa o resistor representado pela linha atual
                processarResistor(cores);
            }
        }
    }

    // Método responsável por processar um resistor.
    // Neste commit, ainda não há validação nem cálculo: apenas normalização e exibição.
    private static void processarResistor(String[] cores) {

        // Normaliza as cores antes de qualquer processamento (trim + minúsculas)
        normalizar(cores);

        // Imprime as cores normalizadas para confirmar que a leitura e normalização funcionam
        System.out.println(String.join(" ", cores));
    }

    // Método responsável por normalizar as cores recebidas
    private static void normalizar(String[] cores) {

        // Percorre todas as posições do vetor de cores
        for (int i = 0; i < cores.length; i++) {

            // Remove espaços extras e converte para letras minúsculas
            // Caso a posição seja nula, substitui por string vazia
            cores[i] = (cores[i] == null)
                    ? ""
                    : cores[i].trim().toLowerCase(Locale.ROOT);
        }
    }
}