package com.example.mariana.juegogato;

import android.support.annotation.Nullable;

public class FinalizaTurnoClass {

    public long IdJugador;

    public long IdPartida;

    public long Resultado;

    public String[] Gato;

    public Nullable Habilidad;

    public FinalizaTurnoClass(long idJugador, long idPartida, long resultado, String[] gato, Nullable habilidad) {
        IdJugador = idJugador;
        IdPartida = idPartida;
        Resultado = resultado;
        Gato = gato;
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

    public long getResultado() {
        return Resultado;
    }

    public void setResultado(long resultado) {
        Resultado = resultado;
    }

    public String[] getGato() {
        return Gato;
    }

    public void setGato(String[] gato) {
        Gato = gato;
    }

    public Nullable getHabilidad() {
        return Habilidad;
    }

    public void setHabilidad(Nullable habilidad) {
        Habilidad = habilidad;
    }
}
