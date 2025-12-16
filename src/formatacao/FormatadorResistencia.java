package formatacao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatadorResistencia {

    public static String formatar(double ohms, double tolerancia, Integer tempco) {

        double valor = ohms;
        String unidade = "Ohms";

        if (valor >= 1_000 && valor < 1_000_000) { // converter para kohms
            valor /= 1_000.0;
            unidade = "kOhms";
    }
        else if (valor >= 1_000_000) { // converter para mohms
            valor /= 1_000_000.0;
            unidade = "MOhms";
        }

        String valorTexto = formatarNumero(valor);
        //ex.: "4,7 kOhms (+-10%)"
        String saida = "Resistência: " + formatarNumero(valor) + " " + unidade + " (+- " + formatarNumero(tolerancia) + "%)";
        //se for 6 faixas, adiciona tempco: "50 ppm/K"
        if (tempco != null) {
            saida += " " + tempco + " ppm / K";
        }

        return saida;
    }
        
    private static String formatarNumero(double n) {           
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();  
        symbols.setDecimalSeparator(','); //configura decimal com virgula

        DecimalFormat df = new DecimalFormat("#.##", symbols);

        String texto = df.format(n);
        
        if (texto.endsWith(",")) { // segurança extra, normalmente não acontece com "#.##"
            texto = texto.substring(0, texto.length() - 1);
        }

        return texto; 
    }
}