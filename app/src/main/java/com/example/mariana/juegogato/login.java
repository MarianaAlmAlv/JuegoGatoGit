package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    public static UsuarioClass respuestaL;

    Button Iniciar;
    Button Registrarse;
    EditText Usuario;
    EditText Pass;
    ProgressBar barra_progreso;

    String nombre;
    long idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario = (EditText) findViewById(R.id.editText_user);
        Pass = (EditText) findViewById(R.id.editText_password);
        Iniciar = (Button) findViewById(R.id.button_Iniciar);
        Registrarse = (Button) findViewById(R.id.button_Registrate);
        barra_progreso = (ProgressBar) findViewById(R.id.progressBar_ingreso);
        barra_progreso.setVisibility(View.INVISIBLE);

        Iniciar.setOnClickListener(iniciar);

        Registrarse.setOnClickListener(registrarse);
    }


    View.OnClickListener registrarse= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent vistaRegistrarse=new Intent(login.this, Registrarse.class);

            startActivity(vistaRegistrarse);
        }
    };

    View.OnClickListener iniciar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Usuario.getText().toString().trim().isEmpty() || Pass.getText().toString().trim().isEmpty() ) {
                Toast.makeText(getApplicationContext(), "Debe ingresar usuario y contraseña",
                        Toast.LENGTH_SHORT).show();
            }else {
                barra_progreso.setVisibility(View.VISIBLE);
                acceso_app();
            }

        }
    };

    public void acceso_app(){

        final  String url_server = getString(R.string.url_webApi);

        Log.e("url", url_server);
        Gson gson  = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IConsumeApi service = retrofit.create(IConsumeApi .class);

        Call<UsuarioClass> response = service.InicioSesion(Usuario.getText().toString(),Pass.getText().toString());
        response.enqueue(new Callback<UsuarioClass>() {
            @Override
            public void onResponse(Call<UsuarioClass> call, Response<UsuarioClass> response) {

                if(response.isSuccessful()){
                    respuestaL=response.body();
                    if(respuestaL.getIdUsuario() > 0){
                        nombre=respuestaL.getNombreUsuario();
                        idUsuario=respuestaL.getIdUsuario();
                        Gson gson  = new GsonBuilder().setLenient().create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.10.16.125:1500/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IConsumeApi service = retrofit.create(IConsumeApi .class);
                        Call<String> response2 = service.ActualizarDispositivoUsuario(idUsuario,1);

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

                        Toast.makeText(getApplicationContext(),"Bienvenido " + respuestaL.getNombreUsuario(), Toast.LENGTH_SHORT).show();
                        Intent principal=new Intent(login.this,MenuPrincipal.class);
                        principal.putExtra("IdUsuario", Long.toString(idUsuario) );

                        principal.putExtra("Nombre", nombre);

                        startActivity(principal);
                    }else {
                        Toast.makeText(getApplicationContext(), "Error al iniciar sesion",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UsuarioClass> call, Throwable t) {
                Log.e("Error", t.toString());
            }


        });
        barra_progreso.setVisibility(View.INVISIBLE);


    }



}
