package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CorreoTest {

    @Test
    public void SePuedeCrearUnCorreo(){
        Contacto remitente = new Contacto("sanfer", "romerostachlautaro@gmail.com");
        Contacto destinatario1 = new Contacto("agustin", "agustinquetglas19@gmail.com");
        Contacto destinatario2 = new Contacto("juanto", "juantomasegovia05@gmail.com");
        List<Contacto> destinatarios = List.of(destinatario1, destinatario2);

        Correo c = new Correo("Prueba Correo", "Probando que se puede crear un correo",remitente, destinatarios);

        assertEquals("Prueba Correo", c.getAsunto());
        assertEquals("Probando que se puede crear un correo", c.getContenido());
        assertEquals("romerostachlautaro@gmail.com", c.getRemitente().getEmail());
        assertTrue(c.getDestinatarios().contains(destinatario1));
        assertTrue(c.getDestinatarios().contains(destinatario1));
    }


    //Requerimiento 8//
    @Test
    public void correoPuedeMarcarseComoLeidoYNoLeido() {
        Contacto remitente = new Contacto("Agustin", "agustin@gmail.com");
        Contacto destino = new Contacto("Lautaro", "lautaro@gmail.com");
        Correo correo = new Correo("juntada", "trae para tomar", remitente, List.of(destino));

    
        assertFalse(correo.estaLeido());

    
        correo.marcarComoLeido();
        assertTrue(correo.estaLeido());


        correo.marcarComoNoLeido();
        assertFalse(correo.estaLeido());
    }

}