package tony.conecta4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Tony on 02/03/2017.
 */

public class PartidaOnline extends AppCompatActivity{
    private Game juego = new Game(Game.JUGADOR);
    ArrayList<String> arrayListaMovimientos = new ArrayList<String>();
    int lastTurn = 1;
    String idPartida = "";
    String jugador = "";
    int estado;
    int contador = 0;
    int contadorAux = 0;
    Context myContext;
    String ip = "192.168.1.20";
    private int[][] arrayId = {{R.id.b00,R.id.b01,R.id.b02,R.id.b03,R.id.b04,R.id.b05,R.id.b06},
            {R.id.b10,R.id.b11,R.id.b12,R.id.b13,R.id.b14,R.id.b15,R.id.b16},
            {R.id.b20,R.id.b21,R.id.b22,R.id.b23,R.id.b24,R.id.b25,R.id.b26},
            {R.id.b30,R.id.b31,R.id.b32,R.id.b33,R.id.b34,R.id.b35,R.id.b36},
            {R.id.b40,R.id.b41,R.id.b42,R.id.b43,R.id.b44,R.id.b45,R.id.b46},
            {R.id.b50,R.id.b51,R.id.b52,R.id.b53,R.id.b54,R.id.b55,R.id.b56}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext = this;
        idPartida = getIntent().getStringExtra("ID_GAME");
        jugador = getIntent().getStringExtra("JUGADOR");
        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        verJugadas("http://" + ip + "/bdconecta4/pages/moves.php?game=" + idPartida, "move");
                        comprobarEstadoPartida();
                        //Log.d("estado",String.valueOf(estado));
                        if(estado == 2)
                            contador++;
                        if(contador == 1) {
                            if (lastTurn == 1)
                                lastTurn = 2;
                            else
                                lastTurn = 1;
                            juego.setGanador(String.valueOf(lastTurn));
                            mostrarMensajeGanador();
                        }
                        if (estado == -1){
                            contadorAux++;
                        }
                        Log.d("jugador",jugador);
                        Log.d("contador",String.valueOf(contadorAux));

                        if (contadorAux == 1 && jugador == "2"){
                            partidaAbandonada();
                            Log.d("jugador","hola hola");
                        }
                    }
                });
            }
        }, 100, 100);
    }


    private void verJugadas(String url,final String tag) {
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(response));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName(tag);
                    arrayListaMovimientos.clear();
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);
                        pintarFicha(Integer.parseInt(element.getAttribute("x")),Integer.parseInt(element.getAttribute("y")),Integer.parseInt(element.getAttribute("color")));
                        juego.setFicha(Integer.parseInt(element.getAttribute("x")),Integer.parseInt(element.getAttribute("y")));
                        juego.setTurno(Integer.parseInt(element.getAttribute("color")));
                    }
                    if(nodes.getLength() != 0) {
                        Element element = (Element) nodes.item(nodes.getLength() - 1);
                        lastTurn = Integer.parseInt(element.getAttribute("color"));
                        if (lastTurn == 1)
                            lastTurn = 2;
                        else
                            lastTurn = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //something happened, treat the error.
            }
        });
        queue.add(request);
    }

    public void partidaAbandonada(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        builder.setMessage("El oponente ha abandonado la partida..");
        builder.setTitle("Abandono");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PartidaOnline.this.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        builder.setMessage("Si abandonas, no se guardará la partida..");
        builder.setTitle("¿Seguro?");
        builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Abandonar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/disableGame.php?id=" + idPartida+"&estado=-1", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //something happened, treat the error.
                    }
                });
                queue.add(request);
                PartidaOnline.this.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void pulsarFicha(View v) {
        for (int i = 0; i < arrayId.length; i++) {
            for (int j = 0; j < arrayId[i].length; j++) {
                if (v.getId() == arrayId[i][j]) {
                    if (juego.getEstado() != "Finalizado")
                        partida(j);
                    else
                        mostrarMensajeGanador();
                }
            }
        }
    }


    private void partida(int j) {
        for (int k = 0; k < arrayId.length; k++) {
            if ((k == (arrayId.length - 1) || !juego.isVacio(k + 1, j)) && juego.isVacio(k, j)) {
                if (juego.getEstado() == "En curso") {
                    if (lastTurn != Integer.parseInt(jugador)) {
                        Toast.makeText(myContext, "No es tu turno", Toast.LENGTH_SHORT).show();
                    } else {
                        juego.setFicha(k, j);
                        pintarFicha(k, j, lastTurn);
                        RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                        StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/move.php?game=" +
                                idPartida + "&x=" + k + "&y=" + j + "&color=" + lastTurn, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //something happened, treat the error.
                            }
                        });
                        queue.add(request);

                    }
                    if (juego.comprobarJugadaOnline(k, j)) {
                        mostrarMensajeGanador();
                        juego.setEstado("Finalizado");
                        RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                        StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/disableGame.php?id=" + idPartida +"&estado=2", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {}
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {}
                        });
                        queue.add(request);
                    }
                    juego.cambiarTurno();
                } else if (juego.getEstado() == "Finalizado") {
                    mostrarMensajeGanador();
                    RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                    StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/disableGame.php?id=" + idPartida +"&estado=2", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {}
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    });
                    queue.add(request);
                }
            }
        }
    }

    void pintarFicha(int i, int j, int turno) {
        ImageView iv = (ImageView) findViewById(arrayId[i][j]);
        if (turno == juego.getTurnoJugador()) {
            iv.setImageResource(R.drawable.c4_buttonrojo);
        } else if (turno == juego.getTurnoMaquina()){
            iv.setImageResource(R.drawable.c4_buttonverde);
        }
    }

    public void mostrarMensajeGanador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AppTheme));
        if (juego.getGanador() == "1"){
            builder.setMessage("El Jugador 1 ha ganado!!!");
        }else if(juego.getGanador() == "2"){
            builder.setMessage("El Jugador 2 ha ganado!!!");
        }
        builder.setTitle("Partida Terminada");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void comprobarEstadoPartida(){
        RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
        StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/getState.php?id=" + idPartida, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(response));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("game");
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);
                        estado = Integer.parseInt(element.getAttribute("estado"));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //something happened, treat the error.
            }
        });

        queue.add(request);
    }
}
