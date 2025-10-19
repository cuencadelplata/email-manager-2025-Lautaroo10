package com.mycompany.app;

import java.util.List;

public class Correo {
    
    private String asunto;
    private String contenido;
    private String remitente;
    private List<String> destinatarios;



    public Correo(String asunto , String contenido , String remitente , List<String>destinatarios){
        this.asunto=asunto;
        this.contenido=contenido;
        this.remitente=remitente;
        this.destinatarios=destinatarios;
    }


    public String getAsunto(){
        return asunto;
    }

    public String getContenido(){
        return contenido;
    }

    public String getRemitente(){
        return remitente;
    }

    public List<String> getDestinatarios(){
        return destinatarios;
    }
    



}
