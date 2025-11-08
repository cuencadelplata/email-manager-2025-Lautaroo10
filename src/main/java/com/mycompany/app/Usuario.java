package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private Contacto contacto;
    private List<Correo> bandejaEnviados = new ArrayList<>();
    private List<Correo> bandejaRecibidos = new ArrayList<>();
    private List<String> nombresFiltros = new ArrayList<>();
    private List<String> criteriosFiltros = new ArrayList<>();




    public Usuario(Contacto contacto){
        this.contacto = contacto;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public List<Correo> getBandejaEnviados() {
        return bandejaEnviados;
    }

    public List<Correo> getBandejaRecibidos() {
        return bandejaRecibidos;
    }

    public void recibirCorreo(Correo correo){
        bandejaRecibidos.add(correo);
    }
    public void enviarCorreo(Correo correo){
        bandejaEnviados.add(correo);
    }


    public List<String> getNombresFiltros() {
    return nombresFiltros;
}


    public List<Correo> buscarCorreos(String textoBusqueda) {
        List<Correo> resultados = new ArrayList<>();

        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            return resultados;
        }

        String texto = textoBusqueda.toLowerCase();

        for (Correo correo : bandejaRecibidos) {
            boolean coincide =
                (correo.getAsunto() != null && correo.getAsunto().toLowerCase().contains(texto)) ||
                (correo.getContenido() != null && correo.getContenido().toLowerCase().contains(texto)) ||
                (correo.getRemitente() != null && correo.getRemitente().getNombre().toLowerCase().contains(texto)) ||
                (correo.getRemitente() != null && correo.getRemitente().getEmail().toLowerCase().contains(texto));

            // Buscar también en los destinatarios
            if (!coincide && correo.getDestinatarios() != null) {
                for (Contacto c : correo.getDestinatarios()) {
                    if (c.getNombre().toLowerCase().contains(texto) || c.getEmail().toLowerCase().contains(texto)) {
                        coincide = true;
                        break;
                    }
                }
            }

            if (coincide) {
                resultados.add(correo);
            }
        }

        return resultados;
    }



    public void crearFiltro(String nombre, String criterio) {

    if (nombresFiltros.contains(nombre)) {
        throw new IllegalArgumentException("Ya existe un filtro con ese nombre.");
    }
    

    if (nombresFiltros.size() >= 5) {
        throw new IllegalArgumentException("No se pueden crear más de 5 filtros.");
    }

    nombresFiltros.add(nombre);
    criteriosFiltros.add(criterio);


    }       




    public List<Correo> filtrarCorreos(String texto) {
    if (texto == null || texto.isEmpty()) {
        return new ArrayList<>();
    }

    String textoFiltrar = texto.toLowerCase();

    return bandejaRecibidos.stream()
            .filter(correo ->
                    (correo.getAsunto() != null && correo.getAsunto().toLowerCase().contains(textoFiltrar)) ||
                    (correo.getContenido() != null && correo.getContenido().toLowerCase().contains(textoFiltrar)) ||
                    (correo.getRemitente() != null && correo.getRemitente().getEmail().toLowerCase().contains(textoFiltrar)) ||
                    (correo.getDestinatarios() != null && correo.getDestinatarios().stream()
                            .anyMatch(d -> d.getEmail().toLowerCase().contains(textoFiltrar)))
            )
            .collect(Collectors.toList());


}



    public List<Correo> aplicarFiltro(String nombreFiltro) {
    int posicion = nombresFiltros.indexOf(nombreFiltro);

    if (posicion == -1) {
        throw new IllegalArgumentException("No existe un filtro con ese nombre: " + nombreFiltro);
    }

    String textoFiltrar = criteriosFiltros.get(posicion);
    return filtrarCorreos(textoFiltrar);
}






}