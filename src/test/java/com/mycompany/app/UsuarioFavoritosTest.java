package com.mycompany.app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UsuarioFavoritosTest {

    private Usuario usuario;
    private Correo correo1;
    private Correo correo2;

    @Before
    public void setUp() {
        Contacto contactoUsuario = new Contacto("Usuario", "usuario@gmail.com");
        usuario = new Usuario(contactoUsuario);

        Contacto remitente = new Contacto("Juan Perez", "juan@gmail.com");
        Contacto dest1 = new Contacto("Pedro", "pedro@gmail.com");
        Contacto dest2 = new Contacto("Maria Lopez", "maria@gmail.com");

        correo1 = new Correo("Reunión importante", "Nos vemos mañana", remitente, List.of(dest1));
        correo2 = new Correo("Promo imperdible", "Descuentos en toda la tienda", remitente, List.of(dest2));

        usuario.recibirCorreo(correo1);
        usuario.recibirCorreo(correo2);
    }

    //Requerimiento 9//

    @Test
    public void sePuedeMarcarComoFavoritoYQuedaEnBandejaFavoritos() {
        assertTrue(usuario.getBandejaFavoritos().isEmpty());

        usuario.marcarComoFavorito(correo1);

        assertEquals(1, usuario.getBandejaFavoritos().size());
        assertTrue(usuario.getBandejaFavoritos().contains(correo1));
        assertTrue(correo1.esFavorito());
    }

    @Test
    public void marcarComoFavoritoNoDuplicaCorreos() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo1); 

        assertEquals(1, usuario.getBandejaFavoritos().size());
        assertTrue(usuario.getBandejaFavoritos().contains(correo1));
    }

    @Test
    public void sePuedeDesmarcarComoFavorito() {
        usuario.marcarComoFavorito(correo1);
        assertTrue(correo1.esFavorito());

        usuario.desmarcarComoFavorito(correo1);

        assertFalse(correo1.esFavorito());
        assertFalse(usuario.getBandejaFavoritos().contains(correo1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void marcarComoFavoritoNoPermiteNull() {
        usuario.marcarComoFavorito(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void desmarcarComoFavoritoNoPermiteNull() {
        usuario.desmarcarComoFavorito(null);
    }

    // ---------- buscarEnFavoritos ----------

    @Test
    public void buscarEnFavoritosConTextoVacioDevuelveTodos() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("");

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(correo1));
        assertTrue(resultado.contains(correo2));
    }

    @Test
    public void buscarEnFavoritosConNullDevuelveTodos() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos(null);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(correo1));
        assertTrue(resultado.contains(correo2));
    }

    @Test
    public void buscarEnFavoritosFiltraPorAsunto() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("reunión");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo1));
        assertFalse(resultado.contains(correo2));
    }

    @Test
    public void buscarEnFavoritosFiltraPorContenido() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("descuentos");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo2));
        assertFalse(resultado.contains(correo1));
    }

    @Test
    public void buscarEnFavoritosFiltraPorEmailDeRemitente() {
        usuario.marcarComoFavorito(correo1);
        List<Correo> resultado = usuario.buscarEnFavoritos("juan@gmail.com");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo1));
    }

    @Test
    public void buscarEnFavoritosFiltraPorNombreDeRemitente() {
        usuario.marcarComoFavorito(correo1);
        List<Correo> resultado = usuario.buscarEnFavoritos("perez"); // parte del nombre

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo1));
    }

    @Test
    public void buscarEnFavoritosFiltraPorEmailDeDestinatario() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("maria@gmail.com");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo2));
    }

    @Test
    public void buscarEnFavoritosFiltraPorNombreDeDestinatario() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("pedro");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo1));
    }

    @Test
    public void buscarEnFavoritosSinCoincidenciasDevuelveListaVacia() {
        usuario.marcarComoFavorito(correo1);
        usuario.marcarComoFavorito(correo2);

        List<Correo> resultado = usuario.buscarEnFavoritos("no-existe");

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void buscarEnFavoritosConCamposNullFiltraPorDestinatario() {
        // Correo con asunto, contenido y remitente en null, pero destinatario válido
        Contacto destSoloEmail = new Contacto("SoloEmail", "soloemail@mail.com");
        Correo correo3 = new Correo(null, null, null, List.of(destSoloEmail));

        usuario.recibirCorreo(correo3);
        usuario.marcarComoFavorito(correo3);

        // Busco solo por el email del destinatario
        List<Correo> resultado = usuario.buscarEnFavoritos("soloemail");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(correo3));
    }
}