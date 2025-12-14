// Importa a classe Scanner para leitura da entrada padrão (stdin)
import java.util.Scanner;

// Classe principal do programa
public class DecodificadorResistor {

    // Método principal do programa
    public static void main(String[] args) {

        // Verifica se o programa foi executado com argumentos de linha de comando.
        // Nesse caso, os argumentos representam as cores de um único resistor.
        if (args != null && args.length > 0) {
            processarResistor(args);
            return;
        }

        // Caso não haja argumentos, o programa passa a ler da entrada padrão,
        // permitindo o processamento de várias linhas (arquivo redirecionado).
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

                // Processa o resistor representado pela linha lida
                processarResistor(cores);
            }
        }
    }

    // Método responsável por processar um resistor.
    // Nesta etapa, ainda não há lógica de validação ou cálculo.
    private static void processarResistor(String[] cores) {

       
// Apenas imprime as cores recebidas, para confirmar a leitura correta
        System.out.println(String.join(" ", cores));
    }    
}
