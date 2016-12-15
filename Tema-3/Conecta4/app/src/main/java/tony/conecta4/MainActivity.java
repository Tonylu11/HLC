package tony.conecta4;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("TURNO",juego.getTurno());
        savedInstanceState.putString("TABLERO",juego.tableroToString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getInt("TURNO");
        savedInstanceState.getString("TABLERO");
    }

    @Override
    protected void onPause() {
        super.onPause();
        musica.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musica.play(this,R.raw.musica);
    }

    public void pulsarFicha(View v){
        for(int i = 0; i < arrayId.length; i++)
            for (int j = 0; j < arrayId[i].length; j++)
                if (arrayId[i][j] == v.getId())
                    for (int k = 0; k < arrayId.length; k++)
                        if ((k == (arrayId.length -1) || !juego.isVacio(k+1,j)) && juego.isVacio(k,j)){
                            ImageView iv = (ImageView) findViewById(arrayId[k][j]);
                            juego.setFicha(k,j);
                            if (juego.getTurno() == juego.getTurnoJugador()){
                                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                                    iv.setImageResource(R.drawable.c4_buttonrojoland);
                                else
                                    iv.setImageResource(R.drawable.c4_buttonrojo);
                            }else {
                                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                                    iv.setImageResource(R.drawable.c4_buttonverdeland);
                                else
                                    iv.setImageResource(R.drawable.c4_buttonverde);
                            }
                            if (juego.comprobarJugada(k,j)) {
                                Toast toastGanada = Toast.makeText(getApplicationContext(), "Cuatro en raya", Toast.LENGTH_SHORT);
                                toastGanada.show();
                            }
                            juego.cambiarTurno();
                            return;
                        }
    }
}

