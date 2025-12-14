package formatacao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatadorResistencia {

    public static String formatar(double ohms) {

        double valor = ohms;
        String unidade = "Ohms";

        if (valor >= 1_000 && valor < 1_000_000) {               //converter para kohms
            valor /= 1_000.0;
            unidade = "kOhms";
    }
        else if (valor >= 1_000_000) {                           // converter para mohms
            valor /= 1_000_000.0;
            unidade = "MOhms";
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();  
        symbols.setDecimalSeparator(',');        //configura decimal com virgula

        DecimalFormat df = new DecimalFormat("#.##", symbols);

        String texto = df.format(valor);
        
        if (texto.endsWith(",")) {
            texto = texto.substring(0, texto.length() - 1);
        }


        return texto + " " + unidade; 
    }
}