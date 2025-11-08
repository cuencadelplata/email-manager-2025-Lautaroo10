package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

public class GestorCorreo {

    private final List<Correo> bandejaEnviados = new ArrayList<>();
    private final List<Contacto> contactos = new ArrayList<>();

    //Agregar contacto
    public void agregarContacto(Contacto contacto){
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto debe existir.");
        }
        // Evitar duplicados por email
        for (Contacto contactoActual : contactos) {
            if (contactoActual.getEmail().equalsIgnoreCase(contacto.getEmail())) {
                throw new IllegalArgumentException("Ya existe un contacto con ese email");
            }
        }

        contactos.add(contacto);
    }

    // Editar contacto
    public void editarContacto(String email, String nuevoNombre, String nuevoEmail) {
        for (Contacto contactoEditar : contactos) {
            if (contactoEditar.getEmail().equalsIgnoreCase(email)) {
                if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                    contactoEditar.setNombre(nuevoNombre);
                }
                if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
                    contactoEditar.setEmail(nuevoEmail);
                }
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró un contacto con ese email");
    }

    //Eliminar contacto
    public void eliminarContacto(String email){
        contactos.removeIf(contactoEliminar -> contactoEliminar.getEmail().equalsIgnoreCase(email));
    }

    public void enviarCorreo(Correo correo) {
        if (correo == null) {
            throw new IllegalArgumentException("El correo no puede ser null.");
        }
        if (correo.getRemitente() == null || correo.getRemitente().getEmail().isEmpty()) {
            throw new IllegalArgumentException("El correo debe tener un remitente.");
        }
        if (correo.getDestinatarios() == null || correo.getDestinatarios().isEmpty()) {
            throw new IllegalArgumentException("El correo debe tener al menos un destinatario.");
        }

        bandejaEnviados.add(correo);
    }

    public List<Correo> getBandejaEnviados() {
        return bandejaEnviados;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }
}