package com.mycompany.app;

import java.util.List;

public class Correo {

    private String asunto;
    private String contenido;
    private Contacto remitente;
    private List<Contacto> destinatarios;
    private boolean leido;





    public Correo(String asunto, String contenido, Contacto remitente, List<Contacto> destinatarios) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.remitente = remitente;
        this.destinatarios = destinatarios;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public Contacto getRemitente() {
        return remitente;
    }

    public List<Contacto> getDestinatarios() {
        return destinatarios;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }


    public void marcarComoLeido() {
        this.leido = true;
    }

    public void marcarComoNoLeido() {
        this.leido = false;
    }

    public boolean estaLeido() {
        return leido;
    }

}