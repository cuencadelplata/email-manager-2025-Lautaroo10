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
    private List<Correo> bandejaBorradores = new ArrayList<>();
    private List<Correo> bandejaEliminados = new ArrayList<>();


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

    public List<Correo> getBandejaBorradores() {
        return bandejaBorradores;
    }

    public List<Correo> getBandejaEliminados() {
        return bandejaEliminados;
    }


    public List<Correo> buscarEnBandejaEntrada(String textoBusqueda) {
    List<Correo> resultados = new ArrayList<>();

    for (Correo correo : bandejaRecibidos) {

        if (correo.getAsunto().contains(textoBusqueda)
            || correo.getContenido().contains(textoBusqueda)
            || correo.getRemitente().getNombre().contains(textoBusqueda)
            || correo.getRemitente().getEmail().contains(textoBusqueda)) {

            resultados.add(correo);
        } else {
            for (Contacto destinatario : correo.getDestinatarios()) {
                if (destinatario.getNombre().contains(textoBusqueda) || destinatario.getEmail().contains(textoBusqueda)) {
                    resultados.add(correo);
                    break; 
                }
            }
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




public void moverCorreo(List<Correo> origen, List<Correo> destino, Correo correo) {
    if (origen.contains(correo)) {
        origen.remove(correo);
        destino.add(correo);
    } else {
        throw new IllegalArgumentException("El correo no existe en la bandeja origen.");
    }
}


public void moverABorradores(Correo correo) {
    moverCorreo(bandejaRecibidos, bandejaBorradores, correo);
}


public void eliminarCorreo(Correo correo) {
    if (bandejaRecibidos.remove(correo) || bandejaEnviados.remove(correo) || bandejaBorradores.remove(correo)) {
        bandejaEliminados.add(correo);
    } else {
        throw new IllegalArgumentException("El correo no existe en ninguna bandeja.");
    }
}


public void restaurarCorreo(Correo correo) {
    moverCorreo(bandejaEliminados, bandejaRecibidos, correo);
}


}



