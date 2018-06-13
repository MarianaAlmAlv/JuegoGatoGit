package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrarse extends AppCompatActivity {
    public static UsuarioClass respuestaR;

    Button registrar;
    EditText usuario;
    EditText pass;
    EditText confirmarPass;

    String nombre;
    long idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        registrar = (Button) findViewById(R.id.button_Registrar);
        usuario = (EditText) findViewById(R.id.editText_user);
        pass = (EditText) findViewById(R.id.editText_password);
        confirmarPass = (EditText) findViewById(R.id.editText_confirmPassword);


        registrar.setOnClickListener(registrarte);
    }

    View.OnClickListener registrarte= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registro_app();
        }
    };

    public void registro_app() {

        final  String url_server = getString(R.string.url_webApi);

        Log.e("url", url_server);
        Gson gson  = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IConsumeApi service = retrofit.create(IConsumeApi .class);


        if (usuario.getText().toString().trim().isEmpty() || pass.getText().toString().trim().isEmpty() || confirmarPass.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ha dejado campos vacios",
                    Toast.LENGTH_LONG).show();
        }else{
            if(pass.getText().toString().equals(confirmarPass.getText().toString())){

                UsuarioClass usu =  new UsuarioClass(0, usuario.getText().toString(), pass.getText().toString(), 0,1 );

                Call<UsuarioClass> response = service.RegistroUsuario(usu.getIdUsuario(),
                        usu.getNombreUsuario(), usu.getContra(), usu.getEstatus(), usu.getDispositivo());

                response.enqueue(new Callback<UsuarioClass>() {
                    @Override
                    public void onResponse(Call<UsuarioClass> call, Response<UsuarioClass> response) {
                        respuestaR=response.body();
                        if(respuestaR.getIdUsuario() > 0){

                            nombre=respuestaR.getNombreUsuario();
                            idUsuario=respuestaR.getIdUsuario();

                            Toast.makeText(getApplicationContext(), respuestaR.getNombreUsuario(), Toast.LENGTH_SHORT).show();
                            Intent principal=new Intent(Registrarse.this, MenuPrincipal.class);
                            principal.putExtra("IdUsuario", Long.toString(idUsuario) );

                            principal.putExtra("Nombre", nombre);

                            startActivity(principal);
                        }else {
                            Toast.makeText(getApplicationContext(), "Error al iniciar sesion",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioClass> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }


                });

            }
            else{
                Toast.makeText(getApplicationContext(), "Contraseñas diferentes",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
