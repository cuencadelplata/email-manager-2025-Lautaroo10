package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;



public class UsuarioTest {

    private Usuario usuario;
    private Contacto remitente;
    private Contacto destinatario1;
    private Contacto destinatario2;

    @Before
    public void setUp() {
        // Crear contactos
        remitente = new Contacto("Agustin", "agustin@gmail.com");
        destinatario1 = new Contacto("Juan", "juan@gmail.com");
        destinatario2 = new Contacto("Maria", "maria@gmail.com");

        // Crear usuario con su contacto principal
        usuario = new Usuario(remitente);
    }

    // RF-01: Creación de correos
    @Test
    public void sePuedeCrearUnCorreoConRemitenteYDestinatarios() {
        Correo correo = new Correo(
                "Asunto de prueba",
                "Contenido del mensaje",
                remitente,
                List.of(destinatario1, destinatario2)
        );

        assertEquals("Asunto de prueba", correo.getAsunto());
        assertEquals("Contenido del mensaje", correo.getContenido());
        assertEquals(remitente, correo.getRemitente());
        assertEquals(2, correo.getDestinatarios().size());
        assertTrue(correo.getDestinatarios().contains(destinatario1));
    }

    // RF-02: Envío de correos
    @Test
    public void alEnviarUnCorreoSeGuardaEnLaBandejaDeEnviados() {
        Correo correo = new Correo(
                "Hola",
                "Mensaje de prueba",
                remitente,
                List.of(destinatario1, destinatario2)
        );

        usuario.enviarCorreo(correo);

        assertEquals(1, usuario.getBandejaEnviados().size());
        assertTrue(usuario.getBandejaEnviados().contains(correo));
    }

    // RF-02 (parte 2): Recepción de correos
    @Test
    public void alRecibirUnCorreoSeGuardaEnLaBandejaDeRecibidos() {
        Correo correo = new Correo(
                "Recordatorio",
                "Tenés una reunión mañana",
                remitente,
                List.of(destinatario1)
        );

        usuario.recibirCorreo(correo);

        assertEquals(1, usuario.getBandejaRecibidos().size());
        assertTrue(usuario.getBandejaRecibidos().contains(correo));
    }




    //requermiiento 5//
    
    @Test
    public void testFiltrarCorreosYAplicarFiltros() {

    Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.edu.ar"));
    Contacto remitente1 = new Contacto("Lautaro", "romerostachlautaro@ucp.edu.ar");
    Contacto remitente2 = new Contacto("Tomas", "tomas@messi.edu.ar");

    Correo c1 = new Correo("Entrega TP", "Recordá enviar el trabajo final", remitente1, List.of(usuario.getContacto()));
    Correo c2 = new Correo("Notas", "Ya están las notas disponibles", remitente1, List.of(usuario.getContacto()));
    Correo c3 = new Correo("Partido", "No te olvides del partido de hoy", remitente2, List.of(usuario.getContacto()));

    usuario.recibirCorreo(c1);
    usuario.recibirCorreo(c2);
    usuario.recibirCorreo(c3);


    
    usuario.crearFiltro("Correos UCP", "@ucp.edu.ar");
    usuario.crearFiltro("Correos Messi", "@messi.edu.ar");


    List<Correo> ucps = usuario.aplicarFiltro("Correos UCP");
    List<Correo> messis = usuario.aplicarFiltro("Correos Messi");

    
    assertEquals("El filtro 'Correos UCP' debería devolver 2 correos", 2, ucps.size());
    assertEquals("El filtro 'Correos Messi' debería devolver 1 correo", 1, messis.size());
}


    @Test
public void crearFiltroAgregaCorrectamente() {
    usuario.crearFiltro("Trabajo", "reunion");
    assertEquals(1, usuario.getNombresFiltros().size());
}

@Test(expected = IllegalArgumentException.class)
public void noSePuedeCrearFiltroConNombreRepetido() {
    usuario.crearFiltro("Urgente", "ahora");
    usuario.crearFiltro("Urgente", "mañana"); 
}

@Test(expected = IllegalArgumentException.class)
public void noSePuedenCrearMasDeCincoFiltros() {
    usuario.crearFiltro("F1", "a");
    usuario.crearFiltro("F2", "b");
    usuario.crearFiltro("F3", "c");
    usuario.crearFiltro("F4", "d");
    usuario.crearFiltro("F5", "e");
    usuario.crearFiltro("F6", "f"); 
}




@Test
public void filtrarCorreosEncuentraPorContenido() {
    Correo correo = new Correo(
            "Titulo",
            "Mensaje urgente",
            remitente,
            List.of(destinatario1)
    );
    usuario.recibirCorreo(correo);

    List<Correo> filtrados = usuario.filtrarCorreos("urgente");

    assertEquals(1, filtrados.size());
}


@Test
public void aplicarFiltroDevuelveCorreosCorrectos() {
    usuario.crearFiltro("Urgentes", "alerta");

    Correo correo = new Correo("Aviso", "Mensaje alerta", remitente, List.of(destinatario1));
    usuario.recibirCorreo(correo);

    List<Correo> resultado = usuario.aplicarFiltro("Urgentes");

    assertEquals(1, resultado.size());
}

@Test(expected = IllegalArgumentException.class)
public void aplicarFiltroLanzaErrorSiNoExiste() {
    usuario.aplicarFiltro("NoExiste");
}

@Test
public void filtrarCorreosEncuentraPorAsunto() {
    Correo correo = new Correo("Reunion urgente, tengo hambre", "Loco porfavor compren algo para comer", remitente, List.of(destinatario1));
    usuario.recibirCorreo(correo);

    List<Correo> filtrados = usuario.filtrarCorreos("reunion");

    assertEquals(1, filtrados.size());
    assertTrue(filtrados.contains(correo));
}

@Test
public void filtrarCorreosEncuentraPorRemitenteEmail() {
    Correo correo = new Correo("Prueba", "AVADA KEDAVRA!", new Contacto("Tom Riddle", "LordVoldemort@mail.com"), List.of(destinatario1));
    usuario.recibirCorreo(correo);

    List<Correo> filtrados = usuario.filtrarCorreos("LordVoldemort@mail.com");

    assertEquals(1, filtrados.size());
}


@Test
public void aplicarFiltroConTextoVacioDevuelveListaVacia() {
    usuario.crearFiltro("FiltroVacio", "");

    Correo correo = new Correo("Hola", "Mensaje", remitente, List.of(destinatario1));
    usuario.recibirCorreo(correo);

    List<Correo> resultado = usuario.aplicarFiltro("FiltroVacio");

    assertTrue(resultado.isEmpty());
}

@Test
public void filtrarCorreosSinCoincidenciasDevuelveListaVacia() {
    Correo correo = new Correo("Asunto", "Contenido", remitente, List.of(destinatario1));
    usuario.recibirCorreo(correo);

    List<Correo> filtrados = usuario.filtrarCorreos("Goku");

    assertTrue(filtrados.isEmpty());
}

//requerimiento 6//

@Test
public void moverCorreoABorradores() {
    Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.edu.ar"));

    Contacto remitente1 = new Contacto("Lautaro", "romerostachlautaro@ucp.edu.ar");
    Contacto remitente2 = new Contacto("Tomas", "tomas@messi.edu.ar");
    Correo correo = new Correo("Hola", "Mensaje", remitente1, List.of(remitente2));

    usuario.recibirCorreo(correo);

    usuario.moverABorradores(correo);

    assertTrue(usuario.getBandejaBorradores().contains(correo));
    assertFalse(usuario.getBandejaRecibidos().contains(correo));
}



@Test
public void eliminarCorreoLoMueveABandejaEliminados() {
    Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.edu.ar"));

    Contacto remitente1 = new Contacto("Lautaro", "romerostachlautaro@ucp.edu.ar");
    Contacto remitente2 = new Contacto("Tomas", "tomas@messi.edu.ar");

    Correo correo = new Correo("Hola", "Mensaje", remitente1, List.of(remitente2));

    usuario.recibirCorreo(correo);

    usuario.eliminarCorreo(correo);

    assertTrue(usuario.getBandejaEliminados().contains(correo));
}



@Test
public void restaurarCorreoDesdeEliminados() {
    Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.edu.ar"));

    Contacto remitente1 = new Contacto("Lautaro", "romerostachlautaro@ucp.edu.ar");
    Contacto remitente2 = new Contacto("Tomas", "tomas@messi.edu.ar");

    Correo correo = new Correo("Hola", "Mensaje", remitente1, List.of(remitente2));

    usuario.recibirCorreo(correo);
    usuario.eliminarCorreo(correo);

    usuario.restaurarCorreo(correo);

    assertTrue(usuario.getBandejaRecibidos().contains(correo));
    assertFalse(usuario.getBandejaEliminados().contains(correo));
}


//Requerimiento 7//
@Test
    public void testGuardarEditarYEnviarBorrador() {
        Contacto remitente = new Contacto("Agustin", "a@mail.com");
        Contacto destinatario = new Contacto("Sanfer", "romerostachlautaro@ucp.edu.ar");
        List<Contacto> destinatarios = new ArrayList<>();
        destinatarios.add(destinatario);

        Usuario usuario = new Usuario(remitente);
        Correo correo = new Correo("Facu", "Sanfer ponete las pilas que recursamos", remitente, destinatarios);

        
        usuario.guardarBorrador(correo);
        assertEquals(1, usuario.getBandejaBorradores().size());
        assertEquals("Facu", usuario.getBandejaBorradores().get(0).getAsunto());

    
        usuario.editarBorrador(correo, "Dale!", "Dale que regularizamos sanfer");
        assertEquals("Dale!", usuario.getBandejaBorradores().get(0).getAsunto());
        assertEquals("Dale que regularizamos sanfer", usuario.getBandejaBorradores().get(0).getContenido());

        
        usuario.enviarBorrador(correo);
        assertEquals(0, usuario.getBandejaBorradores().size());
        assertEquals(1, usuario.getBandejaEnviados().size());
        assertEquals("Dale!", usuario.getBandejaEnviados().get(0).getAsunto());

        
        assertThrows(IllegalArgumentException.class, () -> usuario.editarBorrador(correo, "X", "Y"));
        assertThrows(IllegalArgumentException.class, () -> usuario.enviarBorrador(correo));
        assertThrows(IllegalArgumentException.class, () -> usuario.guardarBorrador(null));
    }




    @Test(expected = IllegalArgumentException.class)
    public void moverCorreoLanzaErrorSiNoExiste() {
        Correo correo = new Correo("Asunto", "Contenido", remitente, List.of(destinatario1));
        usuario.moverCorreo(usuario.getBandejaRecibidos(), usuario.getBandejaBorradores(), correo);
    }



    @Test(expected = IllegalArgumentException.class)
    public void eliminarCorreoInexistenteLanzaError() {
        Correo correo = new Correo("Hola", "Mensaje", remitente, List.of(destinatario1));
        usuario.eliminarCorreo(correo);
    }


    @Test(expected = IllegalArgumentException.class)
    public void restaurarCorreoInexistenteLanzaError() {
        Correo correo = new Correo("Hola", "Mensaje", remitente, List.of(destinatario1));
        usuario.restaurarCorreo(correo);
    }



    @Test
    public void filtrarCorreosConTextoVacioONullDevuelveListaVacia() {
        Correo correo = new Correo("Asunto", "Contenido", remitente, List.of(destinatario1));
        usuario.recibirCorreo(correo);

        assertTrue(usuario.filtrarCorreos("").isEmpty());
        assertTrue(usuario.filtrarCorreos(null).isEmpty());
    }





    @Test
    public void buscarEnBandejaEntradaEncuentraPorAsuntoRemitenteYDestinatario() {
        Contacto remitente = new Contacto("Lautaro", "lautaro@mail.com");
        Contacto destinatario = new Contacto("Agustin", "agustin@mail.com");
        Correo correo = new Correo("Partido", "No te olvides de traer la bocha", remitente, List.of(destinatario));

        usuario.recibirCorreo(correo);

    assertTrue(usuario.buscarEnBandejaEntrada("Partido").contains(correo));
    
        assertTrue(usuario.buscarEnBandejaEntrada("No te olvides de traer la bocha").contains(correo));
    
        assertTrue(usuario.buscarEnBandejaEntrada("Lautaro").contains(correo));
    
        assertTrue(usuario.buscarEnBandejaEntrada("lautaro@mail.com").contains(correo));
        
        assertTrue(usuario.buscarEnBandejaEntrada("Agustin").contains(correo));
    
        assertTrue(usuario.buscarEnBandejaEntrada("agustin@mail.com").contains(correo));
    }


}