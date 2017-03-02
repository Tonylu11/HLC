package tony.conecta4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class OnlineActivity extends AppCompatActivity {
    private String jugada = "";
    private ArrayList<String> listaPartidas = new ArrayList<String>();
    private ArrayList<String> idListaPartidas = new ArrayList<String>();
    private ListView mostrarPartidas;
    private Button botonAddJuego;
    private Context myContext;
    private String ip = "192.168.1.20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_partida);
        myContext = this;
        botonAddJuego = (Button) findViewById(R.id.botonAddPartida);
        mostrarPartidas = (ListView) findViewById(R.id.listaPartidas);

        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run(){
                        accederBD("http://" + ip + "/bdconecta4/pages/games.php",mostrarPartidas);
                    }
                });
            }
        }, 2000, 2000);

        mostrarPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                StringRequest request = new StringRequest("http://" + ip + "/bdconecta4/pages/disableGame.php?id=" + idListaPartidas.get(position)+"&estado=1", new Response.Listener<String>() {
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
                Intent intent = new Intent(myContext, PartidaOnline.class);
                intent.putExtra("ID_GAME", idListaPartidas.get(position));
                intent.putExtra("JUGADOR", "2");
                startActivity(intent);
            }
        });

        botonAddJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = VolleySingleton.getInstance(myContext).getRequestQueue();
                String url = "http://" + ip + "/bdconecta4/pages/start.php";
                StringRequest request = new StringRequest(url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            DocumentBuilder db = dbf.newDocumentBuilder();
                            InputSource is = new InputSource();
                            is.setCharacterStream(new StringReader(response));
                            Document doc = db.parse(is);
                            NodeList nodes = doc.getElementsByTagName("game");
                            String id = "";
                            for (int i = 0; i < nodes.getLength(); i++) {
                                Element element = (Element) nodes.item(i);
                                id = element.getAttribute("id");
                            }
                            Intent intent = new Intent(myContext,PartidaOnline.class);
                            intent.putExtra("ID_GAME", id);
                            intent.putExtra("JUGADOR", "1");
                            startActivity(intent);
                        }catch(Exception e){
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
        });


    }
    public void accederBD(String url, final ListView lista){
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest request = new StringRequest(url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(response));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("game");
                    listaPartidas.clear();
                    idListaPartidas.clear();
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);
                        jugada = "Partida: " + element.getAttribute("id");
                        listaPartidas.add(i, jugada);
                        idListaPartidas.add(i, element.getAttribute("id"));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(myContext, android.R.layout.simple_list_item_1,listaPartidas);
                lista.setAdapter(adaptador);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        queue.add(request);
    }
}
