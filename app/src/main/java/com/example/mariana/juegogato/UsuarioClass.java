package com.example.mariana.juegogato;

public class UsuarioClass {

    public long IdUsuario;

    public String NombreUsuario;

    public String Contra;

    public int Estatus;

    public int Dispositivo;


    public UsuarioClass(long idUsuario, String nombreUsuario, String contra, int estatus, int dispositivo) {
        IdUsuario = idUsuario;
        NombreUsuario = nombreUsuario;
        Contra = contra;
        Estatus = estatus;
        Dispositivo = dispositivo;
    }


    public long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return Contra;
    }

    public void setContra(String contra) {
        Contra = contra;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int estatus) {
        Estatus = estatus;
    }

    public int getDispositivo() {
        return Dispositivo;
    }

    public void setDispositivo(int dispositivo) {
        Dispositivo = dispositivo;
    }



}
