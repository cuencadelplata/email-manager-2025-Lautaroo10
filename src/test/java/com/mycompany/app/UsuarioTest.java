package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;

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




    @Test
    public void alCrearUsuarioSusListasEstanVaciasYContactoAsignado() {

        assertEquals(remitente, usuario.getContacto());


        assertNotNull(usuario.getBandejaEnviados());
        assertNotNull(usuario.getBandejaRecibidos());
        assertTrue(usuario.getBandejaEnviados().isEmpty());
        assertTrue(usuario.getBandejaRecibidos().isEmpty());
}



    @Test
    public void sePuedeBuscarUnCorreo() {

        Usuario usuario = new Usuario(new Contacto("Agustin", "agustinquetglas19@gmail.com"));

        Contacto remitente = new Contacto("Lautaro", "romerostachlautaro@gmail.com");
        Contacto destinatario = usuario.getContacto();

        Correo c1 = new Correo("Encuentro", "Mañana futbol 5", remitente, List.of(destinatario));
        Correo c2 = new Correo("Asado", "No te olvides de comprar la carne", remitente, List.of(destinatario));

        usuario.recibirCorreo(c1);
        usuario.recibirCorreo(c2);


        List<Correo> resultados = usuario.buscarCorreos("asado");
        List<Correo> resultados1 = usuario.buscarCorreos("futbol");


        assertTrue(resultados.get(0).getAsunto().equalsIgnoreCase("Asado"));

        assertTrue(resultados1.get(0).getContenido().toLowerCase().contains("futbol"));
}


@Test
public void testFiltrarCorreosYAplicarFiltros() {
    // 🔹 1) Crear usuario y contactos
    Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.edu.ar"));
    Contacto remitente1 = new Contacto("Lautaro", "romerostachlautaro@ucp.edu.ar");
    Contacto remitente2 = new Contacto("Tomas", "tomas@messi.edu.ar");

    Correo c1 = new Correo("Entrega TP", "Recordá enviar el trabajo final", remitente1, List.of(usuario.getContacto()));
    Correo c2 = new Correo("Notas", "Ya están las notas disponibles", remitente1, List.of(usuario.getContacto()));
    Correo c3 = new Correo("Partido", "No te olvides del partido de hoy", remitente2, List.of(usuario.getContacto()));

    usuario.recibirCorreo(c1);
    usuario.recibirCorreo(c2);
    usuario.recibirCorreo(c3);

    
    List<Correo> resultadosUCP = usuario.filtrarCorreos("@ucp");
    assertEquals("Debería encontrar 2 correos con '@ucp'", 2, resultadosUCP.size());

    List<Correo> resultadosMessi = usuario.filtrarCorreos("@messi");
    assertEquals("Debería encontrar 1 correo con '@messi'", 1, resultadosMessi.size());

    List<Correo> resultadosPartido = usuario.filtrarCorreos("partido");
    assertEquals("Debería encontrar 1 correo con la palabra 'partido'", 1, resultadosPartido.size());

    
    usuario.crearFiltro("Correos UCP", "@ucp.edu.ar");
    usuario.crearFiltro("Correos Messi", "@messi.edu.ar");


    List<Correo> ucps = usuario.aplicarFiltro("Correos UCP");
    List<Correo> messis = usuario.aplicarFiltro("Correos Messi");

    
    assertEquals("El filtro 'Correos UCP' debería devolver 2 correos", 2, ucps.size());
    assertEquals("El filtro 'Correos Messi' debería devolver 1 correo", 1, messis.size());
}



    @Test
    public void testFiltroDuplicadoDebeFallar() {
        Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.com"));

        usuario.crearFiltro("Correos UCP", "@ucp.edu.ar");

        boolean repetido = usuario.getNombresFiltros().contains("Correos UCP");

        assertTrue( "Debe detectar que el filtro con ese nombre ya existe",repetido);
    }



    @Test
    public void noSePuedenCrearMasDeCincoFiltros() {
        Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.com"));

        for (int i = 1; i <= 5; i++) {
            usuario.crearFiltro("Filtro" + i, "criterio" + i);
        }

        assertTrue("No se deberían poder crear más de 5 filtros", 
            usuario.getNombresFiltros().size() == 5);
    }





    @Test
public void buscarCorreosConTextoVacioODebeRetornarListaVacia() {
    Usuario usuario = new Usuario(new Contacto("Agustin", "agus@gmail.com"));
    List<Correo> resultados1 = usuario.buscarCorreos("");
    List<Correo> resultados2 = usuario.buscarCorreos(null);
    assertTrue(resultados1.isEmpty());
    assertTrue(resultados2.isEmpty());
}

    @Test
    public void buscarCorreosPorNombreDelRemitenteODestinatario() {
        Usuario usuario = new Usuario(new Contacto("Agustin", "agustin@gmail.com"));
        Contacto remitente = new Contacto("Carlos", "carlos@mail.com");
        Contacto destinatario = new Contacto("Pepe", "pepe@mail.com");

        Correo correo = new Correo("Saludo", "Hola!", remitente, List.of(destinatario));
        usuario.recibirCorreo(correo);

        List<Correo> resultadosRem = usuario.buscarCorreos("Carlos");
        assertEquals(1, resultadosRem.size());

        List<Correo> resultadosDest = usuario.buscarCorreos("Pepe");
        assertEquals(1, resultadosDest.size());
}






























}


