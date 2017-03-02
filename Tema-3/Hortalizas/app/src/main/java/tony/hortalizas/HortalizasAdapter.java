package tony.hortalizas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Tony on 06/02/2017.
 */
public class HortalizasAdapter extends ArrayAdapter<String>{
    private Activity context;
    private final String[] titulos;
    private final String[] descripciones;
    private final int[] imagenes;
    public HortalizasAdapter(Activity context, String[] titulos, String[] descripciones, int[] imagenes){
        super(context,R.layout.item_hortaliza,titulos);
        this.context = context;
        this.titulos = titulos;
        this.descripciones = descripciones;
        this.imagenes = imagenes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_hortaliza, null, true);
        TextView nombre = (TextView) item.findViewById(R.id.nombreHortaliza);
        ImageView imagen = (ImageView) item.findViewById(R.id.imagenHortaliza);
        TextView descripcionHortaliza = (TextView) item.findViewById(R.id.subtituloHortaliza);
        nombre.setText(titulos[position]);
        descripcionHortaliza.setText(descripciones[position]);
        imagen.setImageResource(imagenes[position]);
        return item;
    }
}
