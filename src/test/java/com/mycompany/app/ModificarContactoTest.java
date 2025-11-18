package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModificarContactoTest {

    private GestorCorreo gestor;

    @Before
    public void setUp() {
        gestor = new GestorCorreo();
    }


    //Agregar contacto
    @Test
    public void agregarContacto() {
        Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");

        gestor.agregarContacto(contacto);

        assertEquals(1, gestor.getContactos().size());
        assertTrue(gestor.getContactos().contains(contacto));
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarContactoSinNombre(){
        Contacto contacto1 = new Contacto("", "Lukeskywalker@gmail.com");
        gestor.agregarContacto(contacto1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarContactoNombreNull(){
        Contacto contacto1 = new Contacto(null, "Lukeskywalker@gmail.com");
        gestor.agregarContacto(contacto1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarContactoSinEmail(){
        Contacto contacto1 = new Contacto("John Snow", "");
        gestor.agregarContacto(contacto1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarContactoEmailNull(){
        Contacto contacto1 = new Contacto("John Snow", null);
        gestor.agregarContacto(contacto1);
    }

@Test
public void editarContacto_CambiaNombreYEmail() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.editarContacto("agustin@gmail.com", "Agus", "agus@gmail.com");

    Contacto editado = gestor.getContactos().get(0);
    assertEquals("Agus", editado.getNombre());
    assertEquals("agus@gmail.com", editado.getEmail());
}

@Test
public void editarContactoSoloNombre_NoModificaEmail() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.editarContacto("agustin@gmail.com", "Agus", null); // nuevoEmail = null

    Contacto editado = gestor.getContactos().get(0);
    assertEquals("Agus", editado.getNombre());
    assertEquals("agustin@gmail.com", editado.getEmail());
}

@Test
public void editarContactoSoloEmail_NoModificaNombre() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.editarContacto("agustin@gmail.com", null, "nuevo@gmail.com"); // nuevoNombre = null

    Contacto editado = gestor.getContactos().get(0);
    assertEquals("Agustin", editado.getNombre());
    assertEquals("nuevo@gmail.com", editado.getEmail());
}

@Test
public void editarContactoIgnoraStringsVacios() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.editarContacto("AGUSTIN@gmail.com", "", ""); // prueba equalsIgnoreCase + isEmpty

    Contacto editado = gestor.getContactos().get(0);
    assertEquals("Agustin", editado.getNombre());       // no cambia
    assertEquals("agustin@gmail.com", editado.getEmail()); // no cambia
}

@Test(expected = IllegalArgumentException.class)
public void editarContactoNoExistenteLanzaExcepcion() {
    gestor.editarContacto("noexiste@gmail.com", "Nombre", "email@gmail.com");
}

// ------------------------- ELIMINAR CONTACTO -------------------------

@Test
public void eliminarContacto_ExistenteLoRemueve() {
    Contacto contacto1 = new Contacto("Agustin", "agustin@gmail.com");
    Contacto contacto2 = new Contacto("Juan", "juan@gmail.com");

    gestor.agregarContacto(contacto1);
    gestor.agregarContacto(contacto2);

    gestor.eliminarContacto("agustin@gmail.com");

    assertEquals(1, gestor.getContactos().size());
    assertFalse(gestor.getContactos().contains(contacto1));
    assertTrue(gestor.getContactos().contains(contacto2));
}

@Test
public void eliminarContactoConMayusculasUsaEqualsIgnoreCase() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.eliminarContacto("AGUSTIN@GMAIL.COM");

    assertTrue(gestor.getContactos().isEmpty());
}

@Test
public void eliminarContactoNoExistenteNoHaceNada() {
    Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
    gestor.agregarContacto(contacto);

    gestor.eliminarContacto("noexiste@gmail.com");

    assertEquals(1, gestor.getContactos().size());
    assertTrue(gestor.getContactos().contains(contacto));
}

}