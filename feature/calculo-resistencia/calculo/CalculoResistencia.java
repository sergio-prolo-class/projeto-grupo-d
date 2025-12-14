package calculo;                  //declaração do modulo

import java.util.Map;                 //import da interface Map pra nao usar mt if/switch na hora de converter cor pra valor

public class CalculoResistencia {

    private static final Map<String, Integer> DIGITOS = Map.of(             //associa cor a digito; Integer usado por causa do Map pq Int nao funciona (versao objeto do int)
        "preto", 0,
        "marrom", 1,
        "vermelho", 2,
        "laranja", 3,
        "amarelo", 4,
        "verde", 5,
        "azul", 6,
        "violeta", 7,
        "cinza", 8,
        "branco", 9
    );

    private static final Map<String, Double> MULTIPLICADORES = Map.of(
        "preto", 1.0,
        "marrom", 10.0,
        "vermelho", 100.0,
        "laranja", 1000.0,
        "amarelo", 10000.0,
        "verde", 100000.0,
        "azul", 1000000.0,
        "violeta", 10000000.0,
        "cinza", 100000000.0,
        "branco", 1000000000.0,
        "dourado", 0.1,
        "prata", 0.01
    );

    public static int calcularDigitos(String... cores) {
        int valor = 0;
        for (String cor : cores) {
            Integer dig = DIGITOS.get(cor);
            valor = valor * 10 + dig;
        }
        return valor;
    }

    public static double aplicarMultiplicador(int digitos, String corMultiplicador) {
        Double mult = MULTIPLICADORES.get(corMultiplicador);
        return digitos * mult;
    }
}


