package com.example.mariana.juegogato;

import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IConsumeApi {
    //@FormUrlEncoded
    @GET("Identidad/InicioSesion")
    //Call<UsuarioClass> InicioSesion(@Field("NombreUsuario")  String user, @Field("Contra") String pwd);
    Call<UsuarioClass> InicioSesion(@Query("NombreUsuario") String user, @Query("Contra") String pwd);

    @FormUrlEncoded
    @POST("Partida/ActualizarPartida")
    Call<FinalizaTurnoClass> ActualizarPartida(@Field("IdJugador")long idJugador,@Field("IdPartida") long idPartida,@Field("Movimiento") int movimiento,@Field("IdPartida") Nullable habilidad);

    @FormUrlEncoded
    @POST("Identidad/RegistroUsuario")
    Call<UsuarioClass> RegistroUsuario(@Field("IdUsuario") long id,
                                       @Field("NombreUsuario") String nombreUsuario,
                                       @Field("Contra") String contra,
                                       @Field("Estatus") int estatus,
                                       @Field("Dispositivo") int dispositivo);

    @GET("ModoJuego/ObtenerJugador")
    Call<Long> ObtenerJugador(@Query("Dispositivo") int dispositivo);

    @POST("Identidad/CambiarEstatus")
    Call<Integer> CambiarEstatus(@Query("IdUsuario") long usuarioId, @Query("Estatus") int estatus);

    @POST("Partida/CrearPartida")
    Call<PartidaClass> CrearPartida(@Query("Jugador1") long jugador1,
                                    @Query("Jugador2") long jugador2,
                                    @Query("ModoJuego") int modoJuego);

    @GET("Estadisticas/ObtenerEstadisticaUsuario")
    Call<EstadisticasClass> ObtenerEstadisticaUsuario(@Query("UsuarioId") long IdUsuario);

    @POST("Identidad/ActualizarDispositivoUsuario")
    Call<String> ActualizarDispositivoUsuario(@Query("IdUsuario") long idUsuario, @Query("Dispositivo") int dispositivo);

}
