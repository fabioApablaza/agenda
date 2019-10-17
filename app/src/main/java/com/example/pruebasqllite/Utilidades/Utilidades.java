package com.example.pruebasqllite.Utilidades;

public class Utilidades {
    //Constantes campos tablas contactos
    public final static String TABLA_CONTACTO="contactos";
    public final static String NOMBRE="nombre";
    public final static String APELLIDO="apellido";
    public final static String TELEFONO="telefono";
    public final static String DIRECCIONPOSTAL="direccionPostal";
    public final static String FECHANAC="fechaNac";
    public static final String CREAR_TABLA_CONTACTO = "CREATE TABLE "+TABLA_CONTACTO+" ( "+NOMBRE+" TEXT NOT NULL, "+APELLIDO+" TEXT NOT NULL, "+TELEFONO+" TEXT PRIMARY KEY NOT NULL, "+DIRECCIONPOSTAL+" TEXT, "+FECHANAC+" DATE )";

}
