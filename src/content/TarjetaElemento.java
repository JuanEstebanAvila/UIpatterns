package content;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class TarjetaElemento {
    private final String nombre;
    private final String estado;
    private final Map<String, String> detallesTecnicos = new LinkedHashMap<>();
    private boolean verAvanzado = false;

    public TarjetaElemento(String nombre, String estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public TarjetaElemento agregarDetalle(String clave, String valor) {
        detallesTecnicos.put(clave, valor);
        return this;
    }

    public void desplegar() { verAvanzado = true; }

    public void contraer() { verAvanzado = false; }

    public void alternar() { verAvanzado = !verAvanzado; }

    public boolean isVerAvanzado() { return verAvanzado; }

    public String render() {
        String resumen = nombre + " · Estado: " + estado;
        if (!verAvanzado) {
            return "[Vista Resumen] " + resumen;
        }
        StringJoiner detalles = new StringJoiner(" | ");
        detallesTecnicos.forEach((k, v) -> detalles.add(k + ": " + v));
        return "[Acordeón Desplegado] " + resumen + "\n    " + detalles;
    }
}
