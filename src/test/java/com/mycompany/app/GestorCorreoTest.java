package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class GestorCorreoTest {

    private GestorCorreo gestor;

    @Before
    public void setUp() {
        gestor = new GestorCorreo();
    }

    @Test
    public void alEnviarUnCorreoSeGuardaEnLaBandejaDeEnviados() {
        // Crear remitente y destinatarios
        Contacto remitente = new Contacto("Segovia", "sego@gmail.com");
        List<Contacto> destinatarios = List.of(
                new Contacto("Pit", "pit@gmail.com"),
                new Contacto("Sanfer", "sanfer@gmail.com")
        );

        //Crear correo
        Correo correo = new Correo("Hola", "como estan los pibes", remitente, destinatarios);

        // Enviar correo
        gestor.enviarCorreo(correo);

        // Verificar que se guarde en enviados para sego
        assertEquals(1, gestor.getBandejaEnviados().size());
        assertTrue(gestor.getBandejaEnviados().contains(correo));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noSePuedeEnviarCorreoNull() {
        gestor.enviarCorreo(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noSePuedeEnviarCorreoSinRemitente() {
        // Lista de destinatarios válida
        List<Contacto> destinatarios = List.of(
                new Contacto("Destino", "destino@gmail.com")
        );

        // Remitente nulo
        Correo correo = new Correo("Asunto", "Mensaje", null, destinatarios);

        gestor.enviarCorreo(correo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noSePuedeEnviarCorreoConRemitenteSinEmail() {
        // Remitente con email vacío
        Contacto remitenteVacio = new Contacto("tonysoprano", "");

        List<Contacto> destinatarios = List.of(
                new Contacto("Destino", "destino@gmail.com")
        );

        Correo correo = new Correo("Asunto", "Mensaje", remitenteVacio, destinatarios);

        gestor.enviarCorreo(correo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noSePuedeEnviarCorreoSinDestinatarios() {
        // Remitente válido
        Contacto remitente = new Contacto("Yo", "yo@gmail.com");

        // Lista vacía de destinatarios
        Correo correo = new Correo("Asunto", "Mensaje", remitente, List.of());

        gestor.enviarCorreo(correo);
    }



    @Test
    public void sePuedeEliminarContactoPorEmail() {
        Contacto contacto = new Contacto("Pedro", "pedro@gmail.com");
        gestor.agregarContacto(contacto);

        gestor.eliminarContacto("pedro@gmail.com");

        assertTrue(gestor.getContactos().isEmpty());
}


    @Test
    public void sePuedeEditarContactoExistente() {
        Contacto contacto = new Contacto("Juan", "juan@gmail.com");
        gestor.agregarContacto(contacto);

        gestor.editarContacto("juan@gmail.com", "Juanito", "nuevojuan@gmail.com");

        assertEquals("Juanito", contacto.getNombre());
        assertEquals("nuevojuan@gmail.com", contacto.getEmail());
}



}