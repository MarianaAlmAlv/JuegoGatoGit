package com.example.mariana.juegogato;

import android.support.annotation.Nullable;

public class TurnoClass {

    public long IdJugador;

    public long IdPartida;

    public int Movimiento;

    public Nullable Habilidad;

    public TurnoClass(long idJugador, long idPartida, int movimiento, Nullable habilidad) {
        IdJugador = idJugador;
        IdPartida = idPartida;
        Movimiento = movimiento;
        Habilidad = habilidad;
    }

    public long getIdJugador() {
        return IdJugador;
    }

    public void setIdJugador(long idJugador) {
        IdJugador = idJugador;
    }

    public long getIdPartida() {
        return IdPartida;
    }

    public void setIdPartida(long idPartida) {
        IdPartida = idPartida;
    }

    public int getMovimiento() {
        return Movimiento;
    }

    public void setMovimiento(int movimiento) {
        Movimiento = movimiento;
    }

    public Nullable getHabilidad() {
        return Habilidad;
    }

    public void setHabilidad(Nullable habilidad) {
        Habilidad = habilidad;
    }
}

