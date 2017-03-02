package tony.conecta4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int[][] arrayId = {{R.id.b00,R.id.b01,R.id.b02,R.id.b03,R.id.b04,R.id.b05,R.id.b06},
                               {R.id.b10,R.id.b11,R.id.b12,R.id.b13,R.id.b14,R.id.b15,R.id.b16},
                               {R.id.b20,R.id.b21,R.id.b22,R.id.b23,R.id.b24,R.id.b25,R.id.b26},
                               {R.id.b30,R.id.b31,R.id.b32,R.id.b33,R.id.b34,R.id.b35,R.id.b36},
                               {R.id.b40,R.id.b41,R.id.b42,R.id.b43,R.id.b44,R.id.b45,R.id.b46},
                               {R.id.b50,R.id.b51,R.id.b52,R.id.b53,R.id.b54,R.id.b55,R.id.b56}};
    private Game juego = new Game(Game.JUGADOR);
    Music musica;

    public void mostrarMensajeGanador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AppTheme));
        if (juego.getGanador() == "Jugador"){
            builder.setMessage("Has ganado!!!");
        }else if(juego.getGanador() == "Maquina"){
            builder.setMessage("Has perdido..");
        }
        builder.setTitle("Partida Terminada");
        builder.setPositiveButton("Nueva Partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this,About.class));
                return true;
            case R.id.sendmsg:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto Aplicación Conecta 4");
                intent.putExtra(Intent.EXTRA_TEXT, "Nueva aplicación Android");
                startActivity(intent);
                return true;
            case R.id.preferences:
                startActivity(new Intent(this,Preferencias.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("TURNO",juego.getTurno());
        savedInstanceState.putString("TABLERO",juego.tableroToString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        juego.setTurno(savedInstanceState.getInt("TURNO"));
        juego.stringToTablero(savedInstanceState.getString("TABLERO"));
        pintarTablero();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //musica.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean play = false;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(Preferencias.PLAY_MUSIC_KEY))
            play = sharedPreferences.getBoolean(Preferencias.PLAY_MUSIC_KEY,
                    Preferencias.PLAY_MUSIC_DEFAULT);
        if (play == true)
            Music.play(this, R.raw.musica);
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

                if (juego.getTurno() == juego.MAQUINA && juego.getEstado() != "Finalizado") {
                    int col = juego.comprobarMovimiento(j);
                    partida(col);
                }
            }
        }
    }


    private void partida(int j) {
        for (int k = 0; k < arrayId.length; k++)
            if ((k == (arrayId.length - 1) || !juego.isVacio(k + 1, j)) && juego.isVacio(k, j)) {
                if (juego.getEstado() == "En curso"){
                    juego.setFicha(k,j);
                    pintarFicha(k, j, juego.getTurno());

                    if (juego.comprobarJugada(k, j)) {
                        mostrarMensajeGanador();
                        juego.setEstado("Finalizado");
                    }
                    juego.cambiarTurno();
                }else if(juego.getEstado() == "Finalizado"){
                    mostrarMensajeGanador();
                }
                /*if (juego.getTurno() == juego.getTurnoJugador()) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        iv.setImageResource(R.drawable.c4_buttonrojoland);
                    else
                        iv.setImageResource(R.drawable.c4_buttonrojo);
                } else {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        iv.setImageResource(R.drawable.c4_buttonverdeland);
                    else
                        iv.setImageResource(R.drawable.c4_buttonverde);
                }*/
            }
    }

    public void pintarTablero(){
        for (int i = 0; i <arrayId.length; i++) {
            for (int j = 0; j < arrayId[i].length; j++) {
                pintarFicha(i, j,juego.getTablero()[i][j]);
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
    /*void pintarFicha(int i, int j, int turno) {
        ImageView iv = (ImageView) findViewById(arrayId[i][j]);
        if (turno == juego.getTurnoJugador()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                iv.setImageResource(R.drawable.c4_buttonrojoland);
            else
                iv.setImageResource(R.drawable.c4_buttonrojo);
        } else if (turno == juego.getTurnoMaquina()){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                iv.setImageResource(R.drawable.c4_buttonverdeland);
            else
                iv.setImageResource(R.drawable.c4_buttonverde);
        }
    }
    */
}

