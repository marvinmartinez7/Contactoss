package com.example.contactos.Configuraciones;

public class Contactos {
    // Nombre de la base de datos
    public static final String NameDatabase = "Administracion";

    // Creacion de las tablas Empleados en la base de datos
    public static final String tablaContac = "constactos";

    /*
      Campos especificos de la tabla empleados
    */
    public static final String pais = "pais";
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String foto = "foto";

    /* Transacciones DDL (data definition lenguage)*/

    public static final String CreateTableContac = "CREATE TABLE " + tablaContac +
            "(id INTEGER PRIMARY KEY,"+
            "nombre TEXT, telefono INTEGER, nota TEXT,pais TEXT, foto TEXT)";

    public static final String DropTableContac = "DROP TABLE IF EXISTS " + tablaContac;
}
