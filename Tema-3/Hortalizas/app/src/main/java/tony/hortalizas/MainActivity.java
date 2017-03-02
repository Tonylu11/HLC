package tony.hortalizas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] titulosHortalizas = {"Brócoli","Alcachofa","Calabacín","Guisante","Haba","Maíz","Champiñon"};
        final String[] subtitulosHortalizas = {
                "El brócoli es una hortaliza de la familia de las coles. " +
                "Su consumo aumenta constantemente, ya que es un alimento sano y " +
                "que admite múltiples preparaciones.",


                "La alcachofa es una inflorescencia inmadura de color verde o morado." +
                " Se consume de muy diversas formas y su sabor es muy apreciado.",


                "Se trata de una de las plantas comestibles más antiguas y probablemente de las primeras" +
                " cultivadas. Se presenta en una vaina de color verde (claro u oscuro, según variedad), más o menos comprimida," +
                " en muchos casos cilíndrica y puntiaguda en sus dos extremos.",

                "La planta de los calabacines tiene un aspecto frondoso y se consumen sus frutos cuando están tiernos." +
                        " Sus flores masculinas se consumen fritas cuando se hallan todavía en capullo.",
                "El haba es una hortaliza comestible, sus semillas" +
                " y sus vainas pueden cocinarse de muy distintas formas," +
                " desde hervidas o como puré hasta como sopa de verano. " +
                "Incluso sus hojas superiores pueden ser utilizadas a modo de espinacas.",
                "El maíz dulce se comercializa bien en forma de ‘mazorca’ o bien como granos sueltos," +
                " frescos en conserva o congelados.",
                "El champiñón es un hongo formado por un sombrero de forma semiesférica" +
                        " o plana y pie cilíndrico, normalmente blanco." +
                        "El champiñón se consume tanto fresco como en conserva," +
                        " crudo o cocinado, formando parte de ensaladas, frito o asado y como guarnición en muchos platos" +
                        " e incluso se elaboran salsas."};
        final int[] imagenesHortalizas = {R.drawable.brocoli,R.drawable.alcachofa,R.drawable.calabacin,R.drawable.guisante,R.drawable.haba,R.drawable.maiz,R.drawable.champ};
        HortalizasAdapter adapter = new HortalizasAdapter(this, titulosHortalizas, subtitulosHortalizas, imagenesHortalizas);
        ListView listView = (ListView) findViewById(R.id.hortalizasList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,VisualizacionDetallesHortalizas.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IMAGENES",imagenesHortalizas[position]);
                bundle.putString("TITULOS",titulosHortalizas[position]);
                bundle.putString("DESCRIPCIONES",subtitulosHortalizas[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
