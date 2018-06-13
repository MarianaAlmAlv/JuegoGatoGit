package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Espera extends AppCompatActivity {

    String PartidaId, Jugador1, Jugador2, TurnoOpnonente, nombreUsuario, idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera);
        Intent recibi_usuario = getIntent();
        Bundle datos_usuario= recibi_usuario.getExtras();
        if(datos_usuario != null)
        {
            nombreUsuario = datos_usuario.getString("NombreUsuario");
            idUsuario = datos_usuario.getString("IdUsuario");
        }

        mSocket.on("salir-espera", onSalirEspera);
        mSocket.connect();
    }


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.137.218:3000");
        } catch (URISyntaxException e) {}
    }

    private Emitter.Listener onSalirEspera = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try{
                PartidaId=data.getString("PartidaId");
                if(data.getString("Jugador2").equals((idUsuario)))
                {
                    Jugador1 =data.getString("Jugador1");
                    Jugador2 = data.getString("Jugador2");
                    TurnoOpnonente= data.getString("TurnoOpnonente");

                    Intent principal=new Intent(Espera.this,MainActivity.class);
                    principal.putExtra("PartidaId", PartidaId );
                    principal.putExtra("Jugador1", Jugador1);
                    principal.putExtra("Jugador2", Jugador2 );
                    principal.putExtra("TurnoOpnonente", TurnoOpnonente);
                    principal.putExtra("IdUsuario", idUsuario);
                    principal.putExtra("NombreUsuario", nombreUsuario);

                    startActivity(principal);
                }

            } catch(JSONException e){
                return;
            }
            Log.i("Status", "Entered the broadcast");
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });*/

        }
    };
}
