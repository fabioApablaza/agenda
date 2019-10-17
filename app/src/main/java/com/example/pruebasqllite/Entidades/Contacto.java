package com.example.pruebasqllite.Entidades;

import java.io.Serializable;

public class Contacto implements Serializable {
    private String nombre;
    private String Apellido;
    private String telefono;
    private String direccionPostal;
    private String fechaNac;

    public Contacto(){

    }
    public Contacto(String nombre, String apellido, String telefono, String direccionPostal, String fechaNac) {
        this.nombre = nombre;
        this.Apellido = apellido;
        this.telefono = telefono;
        this.direccionPostal = direccionPostal;
        this.fechaNac = fechaNac;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }
}
