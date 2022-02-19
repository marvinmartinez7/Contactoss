package com.example.contactos.tablas;
import android.content.Intent;

public class Contact {
    private Integer id;
    private String nombre;
    private Integer telefono;
    private String nota;
    private String pais;
    private byte[] foto;

    public Contact()
    {
        // Constructor Vacio
    }
    public Contact(Integer id, String nombre, Integer telefono, String nota,String pais,byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono =telefono;
        this.nota = nota;
        this.pais = pais;
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombres() {
        return nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }
    public String getNota() {
        return nota;
    }
    public String getPais() {
        return pais;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombre = nombres;
    }

    public void setTelefono(Integer telefono) {
        this.telefono =telefono;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }
}


