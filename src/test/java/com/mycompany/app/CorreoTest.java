package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CorreoTest {


    @Test
    public void SePuedeCrearUnCorreo(){


        String remitente = "romerostachlautaro@gmail.com";
        List<String> destinatarios = List.of("agustinquetglas19@gmail.com", "juantomasegovia05@gmail.com");

        Correo c = new Correo("Prueba Correo", "Probando que se puede crear un correo",remitente, destinatarios);

        assertEquals("Prueba Correo", c.getAsunto());
        assertEquals("Probando que se puede crear un correo", c.getContenido());
        assertEquals("romerostachlautaro@gmail.com", c.getRemitente());
        assertTrue(c.getDestinatarios().contains("agustinquetglas19@gmail.com"));

    }






}
