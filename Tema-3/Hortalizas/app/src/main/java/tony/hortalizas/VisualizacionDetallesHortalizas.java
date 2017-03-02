package tony.hortalizas;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tony on 13/02/2017.
 */
public class VisualizacionDetallesHortalizas extends AppCompatActivity {
    TextView tituloHortaliza, subtituloHortaliza;
    ImageView imagenHortaliza;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_hortaliza);
        tituloHortaliza = (TextView) findViewById(R.id.tituloDescripcionHortaliza);
        subtituloHortaliza = (TextView) findViewById(R.id.subtituloDescripcionHortaliza);
        imagenHortaliza = (ImageView) findViewById(R.id.imagenDescripcionHortaliza);

        Bundle bundle = this.getIntent().getExtras();
        imagenHortaliza.setImageResource(bundle.getInt("IMAGENES"));
        tituloHortaliza.setText(bundle.getString("TITULOS"));
        subtituloHortaliza.setText(bundle.getString("DESCRIPCIONES"));

    }
}
