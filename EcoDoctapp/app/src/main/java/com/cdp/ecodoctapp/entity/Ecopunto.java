package com.cdp.ecodoctapp.entity;

public class Ecopunto {
    private String wkt;
    private String nombre;

    public Ecopunto() {
        // Constructor vacío requerido para Firebase Realtime Database
    }

    public Ecopunto(String wkt, String nombre) {
        this.wkt = wkt;
        this.nombre = nombre;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}