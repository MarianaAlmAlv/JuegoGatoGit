package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuPrincipal extends AppCompatActivity {

    public static long respuesta;
    public static int respuesta2;

    Button unoUno;
    //Button unoMaquina;
    Button Estadisticas;
    Button Salir;
    String usuario, idUsuario;

    long jugador1;
    long jugador2;


    long jugadorObtenido;
    int estatusObtenido;

    PartidaClass partidaObtenida;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Intent recibi_usuario = getIntent();
        Bundle datos_usuario= recibi_usuario.getExtras();

        if(datos_usuario != null)
        {
            usuario = datos_usuario.getString("Nombre");
            idUsuario = datos_usuario.getString("IdUsuario");
        }

        unoUno = (Button) findViewById(R.id.button_uno);
       // unoMaquina = (Button) findViewById(R.id.button_maquina);
        Estadisticas = (Button) findViewById(R.id.button_estadisticas);
        Salir = (Button) findViewById(R.id.button_salir);

        unoUno.setOnClickListener(iniciar);
        //unoMaquina.setOnClickListener(iniciar);
        Salir.setOnClickListener(salir);
        Estadisticas.setOnClickListener(estadistica);

        mSocket.connect();

    }

    View.OnClickListener iniciar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            jugador1 = Long.parseLong(idUsuario);

            app();


        }

    };

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.137.218:3000");
        } catch (URISyntaxException e) {}
    }

    public  void app()
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.10.16.125:1500/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                IConsumeApi service = retrofit.create(IConsumeApi.class);
                Call<Long> callSync = service.ObtenerJugador(1);

                Response<Long> response = null;
                try {
                    response = callSync.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(response.body() == null)
                {
                    jugador2 = 0;
                }
                else
                {
                    jugador2 = response.body();
                }


                Log.d("respuesta", String.valueOf(response.body()));


                if(jugador2 == 0)
                {
                    Log.d("respuesta", "entra en espera");

                    OkHttpClient.Builder httpClient2 = new OkHttpClient.Builder();
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl("http://10.10.16.125:1500/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient2.build())
                            .build();
                    IConsumeApi service2 = retrofit2.create(IConsumeApi.class);
                    Call<Integer> callSync2 = service2.CambiarEstatus(jugador1,1);

                    Response<Integer> response2 = null;
                    try {
                        response2 = callSync2.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("respuesta2", String.valueOf(response2.body()));

                    Intent myIntent = new Intent(MenuPrincipal.this, Espera.class);

                    myIntent.putExtra("IdUsuario", idUsuario);
                    myIntent.putExtra("NombreUsuario", usuario);

                    MenuPrincipal.this.startActivity(myIntent);

                }
                else
                {
                    Log.d("respuesta", "entra en partida");

                    OkHttpClient.Builder httpClient3 = new OkHttpClient.Builder();
                    Retrofit retrofit3 = new Retrofit.Builder()
                            .baseUrl("http://10.10.16.125:1500/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient3.build())
                            .build();
                    IConsumeApi service3 = retrofit3.create(IConsumeApi.class);
                    Call<Integer> callSync3 = service3.CambiarEstatus(jugador2,0);

                    Response<Integer> response3 = null;
                    try {
                        response3 = callSync3.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("respuesta3", String.valueOf(response3.body()));


                    /*
                     * Iniciar partida
                     **/

                    OkHttpClient.Builder httpClient4 = new OkHttpClient.Builder();
                    Retrofit retrofit4 = new Retrofit.Builder()
                            .baseUrl("http://10.10.16.125:1500/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient4.build())
                            .build();
                    IConsumeApi service4 = retrofit4.create(IConsumeApi.class);
                    Call<PartidaClass> callSync4 = service4.CrearPartida(jugador1, jugador2, 1);

                    Response<PartidaClass> response4 = null;
                    try {
                        response4 = callSync4.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("respuesta3", String.valueOf(response4.body()));

                    if(response4.body().getIdPartida() > 0)
                    {
                        /*
                         * Envia valores a pantalla espera
                         * */
                        mSocket.emit("envia-espera",
                                response4.body().getIdPartida()+"_"+response4.body().getJugador1()+"_"
                                        +response4.body().getJugador2()+"_"+response4.body().getTurno());

                        Intent myIntent2 = new Intent(MenuPrincipal.this, MainActivity.class);


                        myIntent2.putExtra("PartidaId",Long.toString(response4.body().getIdPartida()));
                        myIntent2.putExtra("Jugador1", Long.toString(response4.body().getJugador1()));
                        myIntent2.putExtra("Jugador2", Long.toString(response4.body().getJugador2()));
                        myIntent2.putExtra("TurnoOpnonente", Long.toString(response4.body().getTurno()));
                        myIntent2.putExtra("IdUsuario", idUsuario);
                        myIntent2.putExtra("NombreUsuario", usuario);


                        MenuPrincipal.this.startActivity(myIntent2);
                    }
                }
            }
        });

        thread.start();

    }

    View.OnClickListener salir= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Gson gson  = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.10.16.125:1500/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IConsumeApi service = retrofit.create(IConsumeApi .class);
            Call<String> response2 = service.ActualizarDispositivoUsuario(Long.parseLong(idUsuario),0);

            response2.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response2) {
                    String res =response2.body();

                    if(res == "1")
                    {
                        Log.d("resultado", res);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Error", t.toString());
                }


            });

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
            finishAffinity();
        }
    };


    View.OnClickListener estadistica=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent principal=new Intent(MenuPrincipal.this,Estadisticas.class);
            principal.putExtra("IdUsuario", idUsuario );
            startActivity(principal);
        }
    };



}
