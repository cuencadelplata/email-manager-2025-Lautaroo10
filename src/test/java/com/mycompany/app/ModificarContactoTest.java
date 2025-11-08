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

    //Editar contacto
    @Test
    public void editarContacto() {
        // Crear y agregar contacto
        Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
        gestor.agregarContacto(contacto);

        // Editar nombre y email
        gestor.editarContacto("agustin@gmail.com", "Agus", "agus@gmail.com");

        Contacto editado = gestor.getContactos().get(0);
        assertEquals("Agus", editado.getNombre());
        assertEquals("agus@gmail.com", editado.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void editarContactoNoExistenteLanzaExcepcion() {
        // Intentar editar un contacto que no existe
        gestor.editarContacto("noexiste@gmail.com", "Nombre", "email@gmail.com");
    }

    //Eliminar contacto
    @Test
    public void eliminarContacto() {
        Contacto contacto1 = new Contacto("Agustin", "agustin@gmail.com");
        Contacto contacto2 = new Contacto("Juan", "juan@gmail.com");

        gestor.agregarContacto(contacto1);
        gestor.agregarContacto(contacto2);

        // Eliminar contacto1
        gestor.eliminarContacto("agustin@gmail.com");

        assertEquals(1, gestor.getContactos().size());
        assertFalse(gestor.getContactos().contains(contacto1));
        assertTrue(gestor.getContactos().contains(contacto2));
    }

    @Test
    public void eliminarContactoNoExistenteNoHaceNada() {
        Contacto contacto = new Contacto("Agustin", "agustin@gmail.com");
        gestor.agregarContacto(contacto);

        // Intentar eliminar un email que no existe
        gestor.eliminarContacto("noexiste@gmail.com");

        // La lista sigue con el contacto original
        assertEquals(1, gestor.getContactos().size());
        assertTrue(gestor.getContactos().contains(contacto));
    }
}
