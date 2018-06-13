package com.example.mariana.juegogato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.parser.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean PLAYER_X = true;
    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Button boton5;
    Button boton6;
    Button boton7;
    Button boton8;
    Button boton9;
    TextView tvInfo;
    TextView nombre;

    Button ButtonSalir;
    Button DobleTiro;
    Button Reemplazo;

    String Casilla;
    String Lugar;
    int TURN_COUNT;

    int[][] boardStatus = new int[3][3];
    String usuario, idUsuario, usuarioTira, jugador2, partidaId, jugador1;
    String turnoOpnonente;
    private FinalizaTurnoClass respuestaActualiza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton1 = (Button) findViewById(R.id.b00);
        boton2 = (Button) findViewById(R.id.b01);
        boton3 = (Button) findViewById(R.id.b02);
        boton4 = (Button) findViewById(R.id.b10);
        boton5 = (Button) findViewById(R.id.b11);
        boton6 = (Button) findViewById(R.id.b12);
        boton7 = (Button) findViewById(R.id.b20);
        boton8 = (Button) findViewById(R.id.b21);
        boton9 = (Button) findViewById(R.id.b22);
        tvInfo= (TextView) findViewById(R.id.tvInfo);
        nombre = (TextView) findViewById(R.id.nombreUsuario);
        ButtonSalir=(Button) findViewById(R.id.SalirJuego);
        Reemplazo = (Button) findViewById(R.id.reemplazo);
        DobleTiro = (Button) findViewById(R.id.dobleTiro) ;

        boton1.setOnClickListener(this);
        boton2.setOnClickListener(this);
        boton3.setOnClickListener(this);

        boton4.setOnClickListener(this);
        boton5.setOnClickListener(this);
        boton6.setOnClickListener(this);

        boton7.setOnClickListener( this);
        boton8.setOnClickListener(this);
        boton9.setOnClickListener(this);

        ButtonSalir.setOnClickListener(salirJuego);
        ButtonSalir.setEnabled(false);

        initializeBoardStatus();
        mSocket.on("casilla-agregada", onCasillaAgregada);

        Intent recibi_usuario = getIntent();
        Bundle datos_usuario= recibi_usuario.getExtras();

        if(datos_usuario != null)
        {
            usuario = datos_usuario.getString("NombreUsuario");
            idUsuario = datos_usuario.getString("IdUsuario");
            jugador1 =datos_usuario.getString("Jugador1");
            jugador2= datos_usuario.getString("Jugador2");
            partidaId =datos_usuario.getString("PartidaId");
            turnoOpnonente =datos_usuario.getString("TurnoOpnonente");
            tvInfo.setText(usuario);
        }
        if (jugador2.equals(idUsuario))
        {
            enableAllBoxes(false, null);
        }
        mSocket.connect();
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.137.218:3000");
        } catch (URISyntaxException e) {}
    }

    View.OnClickListener salirJuego= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent principal=new Intent(MainActivity.this,MenuPrincipal.class);
            principal.putExtra("IdUsuario", idUsuario );
            principal.putExtra("Nombre", usuario);
            startActivity(principal);
        }
    };

    @Override
    public void onClick(View view) {
        Log.d("Info", "Inside onClick");
        boolean resetButtonPressed = false;
        TURN_COUNT ++;
        String botonTiro;
        switch (view.getId()){
            case R.id.b00:
                if(PLAYER_X){
                    boton1.setText("X");
                    boardStatus[0][0] = 1;
                     botonTiro= "X_b00_";
                }
                else{
                    boton1.setText("0");
                    boardStatus[0][0] = 0;
                    botonTiro= "0_b00_";
                }
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 1, null), botonTiro);
                boton1.setEnabled(false);
                break;

            case R.id.b01:
                if(PLAYER_X){
                    boton2.setText("X");
                    boardStatus[0][1] = 1;
                    botonTiro="X_b01_";
                }
                else{
                    boton2.setText("0");
                    boardStatus[0][1] = 0;
                    botonTiro="0_b01_";
                }
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 2, null), botonTiro);
                boton2.setEnabled(false);
                break;

            case R.id.b02:
                if(PLAYER_X){
                    boton3.setText("X");
                    boardStatus[0][2] = 1;
                    botonTiro="X_b02_";
                }
                else{
                    boton3.setText("0");
                    boardStatus[0][2] = 0;
                    botonTiro="0_b02_";
                }
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 3, null), botonTiro);
                boton3.setEnabled(false);
                break;

            case R.id.b10:
                if(PLAYER_X){
                    boton4.setText("X");
                    boardStatus[1][0] = 1;
                    botonTiro= "X_b10_";
                }
                else{
                    boton4.setText("0");
                    boardStatus[1][0] = 0;
                    botonTiro= "0_b10_";
                }
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 4, null), botonTiro);
                boton4.setEnabled(false);
                break;

            case R.id.b11:
                if(PLAYER_X){
                    boton5.setText("X");
                    boardStatus[1][1] = 1;
                    botonTiro="X_b11_";
                }
                else{
                    boton5.setText("0");
                    boardStatus[1][1] = 0;
                    botonTiro="0_b11_";
                }
                boton5.setEnabled(false);
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 5, null), botonTiro);
                break;

            case R.id.b12:
                if(PLAYER_X){
                    boton6.setText("X");
                    boardStatus[1][2] = 1;
                    botonTiro="X_b12_";
                }
                else{
                    boton6.setText("0");
                    boardStatus[1][2] = 0;
                    botonTiro="0_b12_";
                }
                boton6.setEnabled(false);
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 6, null), botonTiro);
                break;

            case R.id.b20:
                if(PLAYER_X){
                    boton7.setText("X");
                    boardStatus[2][0] = 1;
                    botonTiro="X_b20_";

                }
                else{
                    boton7.setText("0");
                    boardStatus[2][0] = 0;
                    botonTiro="0_b20_";
                }
                boton7.setEnabled(false);
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 7, null), botonTiro);
                break;

            case R.id.b21:
                if(PLAYER_X){
                    boton8.setText("X");
                    boardStatus[2][1] = 1;
                    botonTiro= "X_b21_";
                }
                else{
                    boton8.setText("0");
                    boardStatus[2][1] = 0;
                    botonTiro="0_b21_";
                }
                boton8.setEnabled(false);
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 8, null), botonTiro);
                break;

            case R.id.b22:
                if(PLAYER_X){
                    boton9.setText("X");
                    boardStatus[2][2] = 1;
                    botonTiro="X_b22_";
                }
                else{
                    boton9.setText("0");
                    boardStatus[2][2] = 0;
                    botonTiro= "0_b22_";
                }
                boton9.setEnabled(false);
                turnoOpnonente= ActualizaPartida(new TurnoClass(Long.parseLong(idUsuario),Long.parseLong(partidaId), 9, null), botonTiro);
                break;



            default:
                break;

        }

    }

    private void initializeBoardStatus(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardStatus[i][j] = -1;
            }
        }

    }

    private void result(final String winner){
        Log.i("Datos", "Inside result");

        setInfo(winner);

        enableAllBoxes(false, null);
        Toast.makeText(getApplicationContext(), winner,
                            Toast.LENGTH_LONG).show();
        ButtonSalir.setEnabled(true);


    }


    private void setInfo(String text){
        tvInfo.setText(text);
    }



    private void checkWinner(){

        Log.i("Datos", "Inside checkWinner");

        //Horizontal --- rows
        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    result("Jugador X Ganador\n" + (i+1)+" horizontal");
                    break;
                }
                else if (boardStatus[i][0]==0) {
                    result("Jugador 0 Ganador\n" + (i+1)+" horizontal");
                    break;
                }
            }
        }

        //Vertical --- columns
        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    result("Jugador X Ganador\n" + (i+1)+" vertical");
                    break;
                }
                else if (boardStatus[0][i]==0) {
                    result("Jugador 0 Ganador\n" + (i+1)+" vertical");
                    break;
                }
            }
        }

        //First diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                result("Jugador X Ganador\n Diagonal");
            }
            else if (boardStatus[0][0]==0) {
                result("Jugador 0 Ganador\n Diagonal");
            }
        }

        //Second diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                result("Jugador X Ganador\n Diagonal");
            }
            else if (boardStatus[0][2]==0) {
                result("Jugador 0 Ganadorr\n Diagonal");
            }
        }



    }



    private Emitter.Listener onCasillaAgregada = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            Log.i("Status", "Entered the broadcast");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        Log.i("Casilla", data.getString("casilla"));
                        Log.i("Lugar", data.getString("lugar"));
                        if( data.getString("PartidaId").equals(partidaId)){
                            Lugar=data.getString("lugar");
                            Casilla = data.getString("casilla");

                            if(Long.parseLong(data.getString("idJugador"))>0) {
                                turnoOpnonente= data.getString("idJugador");
                            }
                            TURN_COUNT= Integer.parseInt( data.getString("turno"));
                            int estatus;
                            if(Casilla.equals("X"))
                            {
                                estatus=1;
                                PLAYER_X=false;
                            }else
                            {
                                estatus=0;
                                PLAYER_X=true;
                            }
                            switch (Lugar){
                                case "b00":
                                    boardStatus[0][0] = estatus;
                                    boton1.setText(Casilla);
                                    break;
                                case "b01":
                                    boardStatus[0][1] = estatus;
                                    boton2.setText(Casilla);
                                    break;
                                case "b02":
                                    boardStatus[0][2] = estatus;
                                    boton3.setText(Casilla);
                                    break;
                                case "b10":
                                    boardStatus[1][0] = estatus;
                                    boton4.setText(Casilla);
                                    break;
                                case "b11":
                                    boardStatus[1][1] = estatus;
                                    boton5.setText(Casilla);
                                    break;
                                case "b12":
                                    boardStatus[1][2] = estatus;
                                    boton6.setText(Casilla);
                                    break;
                                case "b20":
                                    boardStatus[2][0] = estatus;
                                    boton7.setText(Casilla);
                                    break;
                                case "b21":
                                    boardStatus[2][1] = estatus;
                                    boton8.setText(Casilla);
                                    break;
                                case "b22":
                                    boardStatus[2][2] = estatus;
                                    break;
                                default:
                                    break;

                            }
                            disabledButton(Lugar);
                            if(turnoOpnonente.equals(idUsuario)) {
                                enableAllBoxes(true, Lugar);
                            }else {
                                enableAllBoxes(false, Lugar );
                            }

                            if(PLAYER_X){
                                setInfo("Turno Jugador X Clave:"+ turnoOpnonente);
                            }
                            else {
                                setInfo("Turno Jugador 0 Clave:"+ turnoOpnonente);
                            }
                            if(TURN_COUNT==9){
                                result("Empate");
                            }
                            checkWinner();
                        }


                    } catch(JSONException e){
                        return;
                    }
                }
            });

        }
    };

    private void  disabledButton(String lugar)
    {
        switch (lugar){
            case "b00":
                boton1.setEnabled(false);
                break;
            case "b01":
                boton2.setEnabled(false);
                break;
            case "b02":
                boton3.setEnabled(false);
                break;
            case "b10":
                boton4.setEnabled(false);
                break;
            case "b11":
                boton5.setEnabled(false);
                break;
            case "b12":
                boton6.setEnabled(false);
                break;
            case "b20":
                boton7.setEnabled(false);
                break;
            case "b21":
                boton8.setEnabled(false);
                break;
            case "b22":
                boton9.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private void enableAllBoxes(boolean value, String lugar){

        boton1.setEnabled(value);
        boton2.setEnabled(value);
        boton3.setEnabled(value);
        boton4.setEnabled(value);
        boton5.setEnabled(value);
        boton6.setEnabled(value);
        boton7.setEnabled(value);
        boton8.setEnabled(value);
        boton9.setEnabled(value);

        if(lugar!=null) {
            disabledButton(lugar);
        }
    }





    public String ActualizaPartida(final TurnoClass turno, final String boton ) {
                final  String url_server = getString(R.string.url_webApi);

                Log.e("url", url_server);
                Gson gson  = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url_server)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IConsumeApi service = retrofit.create(IConsumeApi .class);
                Call<FinalizaTurnoClass> response = service.ActualizarPartida(turno.getIdJugador(), turno.getIdPartida(), turno.getMovimiento(), turno.getHabilidad());
                response.enqueue(new Callback<FinalizaTurnoClass>() {
                    @Override
                    public void onResponse(Call<FinalizaTurnoClass> call, Response<FinalizaTurnoClass> response) {
                        if(response.isSuccessful()) {
                            respuestaActualiza = response.body();
                               turnoOpnonente= Long.toString(respuestaActualiza.getIdJugador());
                                enableAllBoxes(false, null);
                            mSocket.emit("agrega-casilla", boton +TURN_COUNT+"_"+turnoOpnonente+"_"+turno.IdPartida);
                            PLAYER_X = !PLAYER_X;
                            if(PLAYER_X){
                                setInfo("Turno Jugador X");
                            }
                            else {
                                setInfo("Turno Jugador 0");
                            }
                            if(TURN_COUNT==9){
                                result("Empate");
                            }
                            checkWinner();
                        }


                    }

                    @Override
                    public void onFailure(Call<FinalizaTurnoClass> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }
                });

        return turnoOpnonente;
    }



}

