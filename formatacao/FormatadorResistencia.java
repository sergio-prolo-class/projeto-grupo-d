package formatacao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatadorResistencia {

    public static String formatar(double ohms) {

        double valor = ohms;
        String unidade = "Ohms";

        if (valor >= 1_000 && valor < 1_000_000) {               //converter para kohms
            valor = valor / 1000.0;
            unidade = " kOhms ";
    }
        else if (valor >= 1_000_000) {                           // converter para mohms
            valor = valor / 1_000_000.0;
            unidade = " MOhms";
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();  
        symbols.setDecimalSeparator(',');        //configura decimal com virgula

        DecimalFormat df = new DecimalFormat("#.##", symbols);

        String texto = df.format(valor);

        texto = texto.replaceAll(",0$", "");
        texto = texto.replaceAll(",00$", "");                     //p nao aparecer como '0,' ou '0.0'

        return texto + unidade; 
    }
}