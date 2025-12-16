package calculo;                  //declaração do modulo

import java.util.Map;             //import da interface Map pra nao usar mt if/switch na hora de converter cor pra valor

public class CalculoResistencia {

    private static final Map<String, Integer> DIGITOS = Map.ofEntries(//associa cor a digito; Integer usado por causa do Map pq Int nao funciona (versao objeto do int)
    Map.entry("preto", 0),
    Map.entry("marrom", 1),
    Map.entry("vermelho", 2),
    Map.entry("laranja", 3),
    Map.entry("amarelo", 4),
    Map.entry("verde", 5),
    Map.entry("azul", 6),
    Map.entry("roxo", 7),
    Map.entry("cinza", 8),
    Map.entry("branco", 9)
);    

    private static final Map<String, Double> MULTIPLICADORES = Map.ofEntries(
    Map.entry("preto", 1.0),
    Map.entry("marrom", 10.0),
    Map.entry("vermelho", 100.0),
    Map.entry("laranja", 1000.0),
    Map.entry("amarelo", 10000.0),
    Map.entry("verde", 100000.0),
    Map.entry("azul", 1000000.0),
    Map.entry("roxo", 10000000.0),
    Map.entry("ouro", 0.1),
    Map.entry("prata", 0.01)
);

    private static final Map<String, Double> TOLERANCIAS = Map.ofEntries(
    Map.entry("marrom", 1.0),
    Map.entry("vermelho", 2.0),
    Map.entry("verde", 0.5),
    Map.entry("azul", 0.25),
    Map.entry("roxo", 0.1),
    Map.entry("cinza", 0.05),
    Map.entry("ouro", 5.0),
    Map.entry("prata", 10.0)
);

    private static final Map<String, Integer> TEMPCO = Map.ofEntries(
    Map.entry("marrom", 100),
    Map.entry("vermelho", 50),
    Map.entry("laranja", 15),
    Map.entry("amarelo", 25)
);

    public static int calcularDigitos(String... cores) {
    int valor = 0;

    for (String cor : cores) {
        Integer dig = DIGITOS.get(cor);
        if (dig == null) {
            throw new IllegalArgumentException("Cor de dígito inválida: " + cor);
        }
        valor = valor * 10 + dig;
    }

    return valor;
}

    public static double aplicarMultiplicador(int digitos, String corMultiplicador) {
        Double mult = MULTIPLICADORES.get(corMultiplicador);
        return digitos * mult;
    }

    public static double obterTolerancia(String corTolerancia) {
    Double t = TOLERANCIAS.get(corTolerancia);
    if (t == null) {
        throw new IllegalArgumentException("Cor de tolerância inválida: " + corTolerancia);
    }
    return t;
}

    public static int obterTempco(String corTempco) {
    Integer t = TEMPCO.get(corTempco);
    if (t == null) {
        throw new IllegalArgumentException("Cor de tempco inválida: " + corTempco);
    }
    return t;
}

}