package tony.proyecto1_marcadorbaloncesto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int resultadoLocal,resultadoVis;
    private TextView marcadorLocal,marcadorVis;
    private Button boton1Local,boton2Local,boton3Local,botonAumentarLocal,botonDisminuirLocal,boton1Vis,boton2Vis,boton3Vis,botonAumentarVis,botonDisminuirVis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        marcadorLocal = (TextView) findViewById(R.id.marcadorLocal);
        marcadorVis = (TextView) findViewById(R.id.marcadorVis);

        boton1Local = (Button) findViewById(R.id.boton1Local);
        boton2Local = (Button) findViewById(R.id.boton2Local);
        boton3Local = (Button) findViewById(R.id.boton3Local);
        botonAumentarLocal = (Button) findViewById(R.id.aumentarButtonLocal);
        botonDisminuirLocal = (Button) findViewById(R.id.disminuirButtonLocal);

        boton1Vis = (Button) findViewById(R.id.boton1Visitante);
        boton2Vis = (Button) findViewById(R.id.boton2Visitante);
        boton3Vis = (Button) findViewById(R.id.boton3Visitante);
        botonAumentarVis = (Button) findViewById(R.id.aumentarButtonVis);
        botonDisminuirVis = (Button) findViewById(R.id.disminuirButtonVis);

        boton1Local.setOnClickListener(this);
        boton2Local.setOnClickListener(this);
        boton3Local.setOnClickListener(this);
        botonAumentarLocal.setOnClickListener(this);
        botonDisminuirLocal.setOnClickListener(this);
        boton1Vis.setOnClickListener(this);
        boton2Vis.setOnClickListener(this);
        boton3Vis.setOnClickListener(this);
        botonAumentarVis.setOnClickListener(this);
        botonDisminuirVis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resultadoLocal = Integer.parseInt(marcadorLocal.getText().toString());
        resultadoVis = Integer.parseInt(marcadorVis.getText().toString());
        switch (v.getId()){
            case R.id.boton1Local:
                resultadoLocal += 1;
                break;
            case R.id.boton2Local:
                resultadoLocal += 2;
                break;
            case R.id.boton3Local:
                resultadoLocal += 3;
                break;
            case R.id.aumentarButtonLocal:
                resultadoLocal += 1;
                break;
            case R.id.disminuirButtonLocal:
                if (resultadoLocal > 0){
                    resultadoLocal -= 1;
                }
                break;
            case R.id.boton1Visitante:
                resultadoVis += 1;
                break;
            case R.id.boton2Visitante:
                resultadoVis += 2;
                break;
            case R.id.boton3Visitante:
                resultadoVis += 3;
                break;
            case R.id.aumentarButtonVis:
                resultadoVis += 1;
                break;
            case R.id.disminuirButtonVis:
                if (resultadoVis > 0){
                    resultadoVis -= 1;
                }
                break;
        }
        marcadorLocal.setText(Integer.toString(resultadoLocal));
        marcadorVis.setText(Integer.toString(resultadoVis));
    }
}
