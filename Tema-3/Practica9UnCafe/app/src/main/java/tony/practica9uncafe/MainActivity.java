package tony.practica9uncafe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int aux, precio;
    TextView contador;
    EditText nombreCafe;
    CheckBox cremaCBox, chocolateCBox;
    Button botonDisminuir, botonIncrementar, botonEnviar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = (TextView) findViewById(R.id.contador);
        nombreCafe = (EditText)findViewById(R.id.nombreCafe);
        cremaCBox = (CheckBox) findViewById(R.id.cremaCheckBox);
        chocolateCBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        botonDisminuir = (Button) findViewById(R.id.botonMenos);
        botonIncrementar = (Button) findViewById(R.id.botonMas);
        botonEnviar = (Button) findViewById(R.id.enviar);

        botonIncrementar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementar(v);
            }
        });
        botonDisminuir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementar(v);
            }
        });
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = contador.getText().toString();
                aux = Integer.parseInt(valor);
                if(aux>0){
                    precio=aux*2;
                    Context contexto = getApplicationContext();
                    CharSequence textoTostada= "Nombre: "+ nombreCafe.getText().toString() + "\n" +
                            "¿Añadir crema? " + comprobarCheckBox(cremaCBox) + "\n" +
                            "¿Añadir chocolate? " + comprobarCheckBox(chocolateCBox) + "\n" +
                            "Cantidad: " + contador.getText().toString() + "\n" +
                            "Precio: " + precio + "€\n" +
                            "Gracias!";
                    Toast toast = Toast.makeText(contexto, textoTostada, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private String comprobarCheckBox(CheckBox cb) {
        if (cb.isChecked())
            return "Sí";
        return "No";
    }

    public void incrementar(View view){
        aux = Integer.parseInt(contador.getText().toString());
        aux++;
        contador.setText(String.valueOf(aux));

    }
    public void decrementar(View view){
        aux = Integer.parseInt(contador.getText().toString());
        if (aux == 0) {
            return;
        }
        aux--;
        contador.setText(String.valueOf(aux));
    }
}
