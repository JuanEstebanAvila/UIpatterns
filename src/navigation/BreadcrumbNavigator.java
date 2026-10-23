package navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreadcrumbNavigator {
    private static final String RAIZ = "Inicio";
    private static final String SEPARADOR = " > ";

    private final List<String> ruta = new ArrayList<>();

    public BreadcrumbNavigator() {
        ruta.add(RAIZ);
    }

    public void cambiarTab(String tab) {
        ruta.clear();
        ruta.add(RAIZ);
        if (tab != null && !tab.isBlank() && !tab.equalsIgnoreCase(RAIZ)) {
            ruta.add(tab.trim());
        }
    }

    public void entrarSubcategoria(String subcategoria) {
        if (subcategoria != null && !subcategoria.isBlank()) {
            ruta.add(subcategoria.trim());
        }
    }

    public void volver() {
        if (ruta.size() > 1) {
            ruta.remove(ruta.size() - 1);
        }
    }

    public void irANivel(int indice) {
        if (indice >= 0 && indice < ruta.size()) {
            while (ruta.size() > indice + 1) {
                ruta.remove(ruta.size() - 1);
            }
        }
    }

    public List<String> getRuta() {
        return Collections.unmodifiableList(ruta);
    }

    public String getBreadcrumb() {
        return String.join(SEPARADOR, ruta);
    }
}
