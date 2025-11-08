package com.mycompany.app;

public class Contacto {
    private String nombre;
    private String email;
    
    public Contacto(String nombre, String email){
        if(nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}