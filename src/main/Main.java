package main;

import content.TarjetaElemento;
import input.ValidadorForgiving;
import java.util.List;
import navigation.BreadcrumbNavigator;
import state.GestorSesionUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- 1. PATRÓN DE NAVEGACIÓN (BREADCRUMBS + TABS) ---");
        BreadcrumbNavigator nav = new BreadcrumbNavigator();
        System.out.println("Ruta inicial: " + nav.getBreadcrumb());
        nav.cambiarTab("Catálogo");
        System.out.println("Cambio de pestaña -> Ruta actual: " + nav.getBreadcrumb());
        nav.entrarSubcategoria("Servidor Principal");
        System.out.println("Entra a subcategoría -> Ruta actual: " + nav.getBreadcrumb());
        nav.cambiarTab("Reservas");
        nav.entrarSubcategoria("Laboratorio de Redes");
        System.out.println("Otra pestaña -> Ruta actual: " + nav.getBreadcrumb());

        System.out.println("\n--- 2. PATRÓN DE CONTENIDO Y ESTRUCTURA (PROGRESSIVE DISCLOSURE) ---");
        TarjetaElemento tarjeta = new TarjetaElemento("Servidor Principal", "Activo")
                .agregarDetalle("IP", "192.168.1.10")
                .agregarDetalle("Puerto", "8080")
                .agregarDetalle("RAM", "4GB/16GB");
        System.out.println("verAvanzado = " + tarjeta.isVerAvanzado());
        System.out.println(tarjeta.render());
        System.out.println("\n[Usuario solicita ver detalles avanzados...]");
        tarjeta.desplegar();
        System.out.println("verAvanzado = " + tarjeta.isVerAvanzado());
        System.out.println(tarjeta.render());

        System.out.println("\n--- 3. PATRÓN DE ENTRADA DE DATOS (FORGIVING FORMAT) ---");
        ValidadorForgiving validador = new ValidadorForgiving();
        String[] entradas = {"+57 (310) 999-8877", "310.999.8877", "573109998877", "  3109998877 "};
        for (String e : entradas) {
            System.out.println("Entrada del usuario: \"" + e + "\"  ->  Teléfono normalizado sin error: \""
                    + validador.normalizarTelefono(e) + "\"");
        }
        System.out.println("Fecha \"25/12/2026\" -> \"" + validador.normalizarFecha("25/12/2026") + "\"");
        System.out.println("Fecha \"5 - 3 - 2026\" -> \"" + validador.normalizarFecha("5 - 3 - 2026") + "\"");

        System.out.println("\n--- 4. PATRÓN DE FEEDBACK Y ESTADO (COMPLETENESS METER) ---");
        GestorSesionUI gestor = new GestorSesionUI(List.of("Teléfono", "Laboratorio", "Correo de contacto"));
        System.out.println(gestor.medidor());
        gestor.setCampo("Laboratorio", "Laboratorio de Redes");
        System.out.println("Usuario llena 1 campo: " + gestor.medidor());
        gestor.setCampo("Teléfono", validador.normalizarTelefono("+57 (310) 999-8877"));
        System.out.println("Usuario llena 2 campos: " + gestor.medidor());
        gestor.setCampo("Correo de contacto", "estudiante@udistrital.edu.co");
        System.out.println("Usuario ingresa correo \"estudiante@udistrital.edu.co\": " + gestor.medidor());

        System.out.println("\n--- 5. LAZY REGISTRATION + TOAST ---");
        System.out.println("usuarioActivo = " + gestor.isUsuarioActivo());
        System.out.println("Usuario intenta guardar sin haber iniciado sesión...");
        System.out.println(gestor.guardarReserva());
        System.out.println("¿Borrador conservado? " + gestor.tieneBorrador());
        gestor.registrarCuenta();
        System.out.println("\nUsuario completa registro rápido -> usuarioActivo = " + gestor.isUsuarioActivo());
        System.out.println(gestor.guardarReserva());

        System.out.println("\n--- EXTRA: CERO DARK PATTERNS (SALIDA EN UN SOLO PASO) ---");
        System.out.println(gestor.cancelarReserva());
        System.out.println(gestor.cerrarSesion());
    }
}
