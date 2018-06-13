package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Estadisticas extends AppCompatActivity {
    public static EstadisticasClass respuesta;

    Button BtnRegreso;
    TextView TvVictorias;
    TextView TvDerrotas;
    TextView TvEmpates;
    long idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        TvVictorias=(TextView)findViewById(R.id.textViewVictoria);
        TvDerrotas=(TextView)findViewById(R.id.textViewDerrota);
        TvEmpates=(TextView)findViewById(R.id.textViewEmpate);
        //BtnRegreso=(Button)findViewById(R.id.BtnRegreso);

        Intent recibi_usuario = getIntent();
        Bundle datos_usuario= recibi_usuario.getExtras();

        if(datos_usuario != null)
        {
            idUsuario = Long.parseLong( datos_usuario.getString("IdUsuario"));
            ObtenerEstaditicasUsuario(idUsuario);
        }

    }

    public void ObtenerEstaditicasUsuario(long _idUsuario){

        final  String url_server = "http://10.10.16.125:1500/api/";

        Log.e("url", url_server);
        Gson gson  = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IConsumeApi service = retrofit.create(IConsumeApi .class);

        Call<EstadisticasClass> response = service.ObtenerEstadisticaUsuario(_idUsuario);
        response.enqueue(new Callback<EstadisticasClass>() {
            @Override
            public void onResponse(Call<EstadisticasClass> call, Response<EstadisticasClass> response) {
                respuesta=response.body();
                if(respuesta.getIdUsuario() > 0){
                    TvVictorias.setText(String.valueOf(respuesta.getVictorias()));
                    TvDerrotas.setText(String.valueOf(respuesta.getDerrotas()));
                    TvEmpates.setText(String.valueOf(respuesta.getEmpates()));

                }else {
                    Toast.makeText(getApplicationContext(), "Error al mostrar las estadísticas",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EstadisticasClass> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });


    }

}
