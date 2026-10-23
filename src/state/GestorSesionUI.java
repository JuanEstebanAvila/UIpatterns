package state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestorSesionUI {
    private final List<String> camposRequeridos;
    private final Map<String, String> campos = new LinkedHashMap<>();
    private Map<String, String> borrador = null;
    private boolean usuarioActivo = false;
    private final List<String> toasts = new ArrayList<>();

    public GestorSesionUI(List<String> camposRequeridos) {
        this.camposRequeridos = List.copyOf(camposRequeridos);
    }


    public void setCampo(String nombre, String valor) {
        if (camposRequeridos.contains(nombre)) {
            if (valor == null || valor.isBlank()) {
                campos.remove(nombre);
            } else {
                campos.put(nombre, valor);
            }
        }
    }

    public int camposCompletados() {
        return campos.size();
    }

    public int porcentajeProgreso() {
        if (camposRequeridos.isEmpty()) {
            return 100;
        }
        return Math.round(campos.size() * 100f / camposRequeridos.size());
    }

    public List<String> camposFaltantes() {
        return camposRequeridos.stream()
                .filter(c -> !campos.containsKey(c))
                .collect(Collectors.toList());
    }

    public String medidor() {
        int total = camposRequeridos.size();
        if (camposCompletados() >= total) {
            return "[Progreso: " + porcentajeProgreso() + "%] ¡Formulario completo!";
        }
        return "[Progreso: " + porcentajeProgreso() + "%] Campos listos: "
                + camposCompletados() + " de " + total
                + " (Falta: " + String.join(", ", camposFaltantes()) + ")";
    }

    public boolean isUsuarioActivo() { return usuarioActivo; }

    public boolean tieneBorrador() { return borrador != null; }


    public String guardarReserva() {
        if (!usuarioActivo) {
            borrador = new LinkedHashMap<>(campos);
            return "[Lazy Registration] Avance guardado en borrador temporal. "
                    + "Por favor cree su cuenta para finalizar.";
        }
        if (borrador != null && campos.isEmpty()) {
            campos.putAll(borrador);
        }
        borrador = null;
        return enviarToast("Reserva guardada exitosamente.");
    }

    public void registrarCuenta() {
        usuarioActivo = true;
        if (borrador != null && campos.isEmpty()) {
            campos.putAll(borrador);
        }
    }


    public String cancelarReserva() {
        campos.clear();
        borrador = null;
        return enviarToast("Reserva cancelada.");
    }

    public String cerrarSesion() {
        usuarioActivo = false;
        return enviarToast("Sesión cerrada.");
    }

    private String enviarToast(String mensaje) {
        toasts.add(mensaje);
        return "[Toast de Estado] " + mensaje;
    }

    public List<String> getToasts() {
        return Collections.unmodifiableList(toasts);
    }
}
