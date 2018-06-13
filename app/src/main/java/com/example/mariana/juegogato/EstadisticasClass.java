package com.example.mariana.juegogato;

public class EstadisticasClass {
    public long IdEstadistica;
    public long IdUsuario;
    public int Victorias;
    public  int Derrotas;
    public  int Empates;

    public EstadisticasClass(long _IdEstadistica, long _IdUsuario, int _Victoria, int _Derrota, int _Empate)
    {
        IdEstadistica=_IdEstadistica;
        IdUsuario=_IdUsuario;
        Victorias= _Victoria;
        Derrotas=_Derrota;
        Empates=_Empate;
    }

    public long getIdEstadistica() {
        return IdEstadistica;
    }
    public long getIdUsuario() {
        return IdUsuario;
    }
    public long getVictorias() {
        return Victorias;
    }
    public long getDerrotas() {
        return Derrotas;
    }
    public long getEmpates() {
        return Empates;
    }
}
