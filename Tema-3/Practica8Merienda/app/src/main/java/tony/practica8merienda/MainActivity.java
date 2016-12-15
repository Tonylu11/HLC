package tony.practica8merienda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    ImageView imagen;
    TextView texto;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen = (ImageView) findViewById(R.id.imagenEstado);
        texto = (TextView) findViewById(R.id.textoEstado);
        boton = (Button) findViewById(R.id.botonComer);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagen.setImageResource(R.drawable.lleno);
                texto.setText(R.string.lleno);
            }
        });
    }
}
