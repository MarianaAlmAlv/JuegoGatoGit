package com.example.mariana.juegogato;

/**
 * Created by Felix on 05/06/2018.
 */

public class PartidaClass {


    public long IdPartida;

    public long Jugador1;

    public long Jugador2;

    public long Turno;

    public long Lugar1;

    public long Lugar2;

    public long Lugar3;

    public long Lugar4;

    public long Lugar5;

    public long Lugar6;

    public long Lugar7;

    public long Lugar8;

    public long Lugar9;

    public int IdModoJuego;


    PartidaClass(){}

    PartidaClass(long idPartida, long jugador1, long jugador2, long turno,
                 long lugar1, long lugar2, long lugar3, long lugar4, long lugar5,
                 long lugar6, long lugar7, long lugar8, long lugar9, int idModoJuego){

        IdPartida = idPartida;
        Jugador1 = jugador1;
        Jugador2 = jugador2;
        Turno = turno;
        Lugar1 = lugar1;
        Lugar2 = lugar2;
        Lugar3 = lugar3;
        Lugar4 = lugar4;
        Lugar5 = lugar5;
        Lugar6 = lugar6;
        Lugar7 = lugar7;
        Lugar8 = lugar8;
        Lugar9 = lugar9;
        IdModoJuego = idModoJuego;
    }

    public long getIdPartida() {
        return IdPartida;
    }

    public void setIdPartida(long idPartida) {
        IdPartida = idPartida;
    }

    public long getJugador1() {
        return Jugador1;
    }

    public void setJugador1(long jugador1) {
        Jugador1 = jugador1;
    }

    public long getJugador2() {
        return Jugador2;
    }

    public void setJugador2(long jugador2) {
        Jugador2 = jugador2;
    }

    public long getTurno() {
        return Turno;
    }

    public void setTurno(long turno) {
        Turno = turno;
    }

    public long getLugar1() {
        return Lugar1;
    }

    public void setLugar1(long lugar1) {
        Lugar1 = lugar1;
    }

    public long getLugar2() {
        return Lugar2;
    }

    public void setLugar2(long lugar2) {
        Lugar2 = lugar2;
    }

    public long getLugar3() {
        return Lugar3;
    }

    public void setLugar3(long lugar3) {
        Lugar3 = lugar3;
    }

    public long getLugar4() {
        return Lugar4;
    }

    public void setLugar4(long lugar4) {
        Lugar4 = lugar4;
    }

    public long getLugar5() {
        return Lugar5;
    }

    public void setLugar5(long lugar5) {
        Lugar5 = lugar5;
    }

    public long getLugar6() {
        return Lugar6;
    }

    public void setLugar6(long lugar6) {
        Lugar6 = lugar6;
    }

    public long getLugar7() {
        return Lugar7;
    }

    public void setLugar7(long lugar7) {
        Lugar7 = lugar7;
    }

    public long getLugar8() {
        return Lugar8;
    }

    public void setLugar8(long lugar8) {
        Lugar8 = lugar8;
    }

    public long getLugar9() {
        return Lugar9;
    }

    public void setLugar9(long lugar9) {
        Lugar9 = lugar9;
    }

    public int getIdModoJuego() {
        return IdModoJuego;
    }

    public void setIdModoJuego(int idModoJuego) {
        IdModoJuego = idModoJuego;
    }
}
