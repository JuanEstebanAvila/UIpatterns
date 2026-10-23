package input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorForgiving {

    private static final Pattern NO_DIGITO = Pattern.compile("\\D");
    private static final Pattern FECHA_DMA = Pattern.compile("^(\\d{1,2})\\D+(\\d{1,2})\\D+(\\d{4})$");
    private static final Pattern FECHA_AMD = Pattern.compile("^(\\d{4})\\D+(\\d{1,2})\\D+(\\d{1,2})$");
    private static final Pattern FECHA_COMPACTA = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})$");

    public String normalizarTelefono(String entrada) {
        if (entrada == null) {
            return "";
        }
        String digitos = NO_DIGITO.matcher(entrada).replaceAll("");
        if (digitos.startsWith("0057")) {
            digitos = digitos.substring(4);
        } else if (digitos.length() == 12 && digitos.startsWith("57")) {
            digitos = digitos.substring(2);
        }
        return digitos;
    }
    
    public String normalizarFecha(String entrada) {
        if (entrada == null) {
            return "";
        }
        String texto = entrada.trim();
        Matcher m = FECHA_AMD.matcher(texto);
        if (m.matches()) {
            return formatear(m.group(1), m.group(2), m.group(3));
        }
        m = FECHA_DMA.matcher(texto);
        if (m.matches()) {
            return formatear(m.group(3), m.group(2), m.group(1));
        }
        m = FECHA_COMPACTA.matcher(texto);
        if (m.matches()) {
            return formatear(m.group(1), m.group(2), m.group(3));
        }
        return texto;
    }

    private String formatear(String anio, String mes, String dia) {
        return String.format("%s-%02d-%02d", anio, Integer.parseInt(mes), Integer.parseInt(dia));
    }
}
