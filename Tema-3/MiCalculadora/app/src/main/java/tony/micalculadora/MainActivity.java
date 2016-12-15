package tony.micalculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton0,
            botonSuma, botonResta, botonPunto, botonDivision, botonMultiplicacion, botonIgual, botonC, botonSigno;
    private TextView texto;
    private boolean loperando1, loperando2, lNuevoNumero;
    private double resultado, operando1, operando2;
    private String operacion;

    private void cargarComponentes() {
        boton0 = (Button)findViewById(R.id.boton0);
        boton1 = (Button)findViewById(R.id.boton1);
        boton2 = (Button)findViewById(R.id.boton2);
        boton3 = (Button)findViewById(R.id.boton3);
        boton4 = (Button)findViewById(R.id.boton4);
        boton5 = (Button)findViewById(R.id.boton5);
        boton6 = (Button)findViewById(R.id.boton6);
        boton7 = (Button)findViewById(R.id.boton7);
        boton8 = (Button)findViewById(R.id.boton8);
        boton9 = (Button)findViewById(R.id.boton9);
        botonSuma = (Button)findViewById(R.id.botonSumar);
        botonResta = (Button)findViewById(R.id.botonRestar);
        botonDivision = (Button)findViewById(R.id.botonDividir);
        botonMultiplicacion = (Button)findViewById(R.id.botonMultiplicar);
        botonPunto = (Button)findViewById(R.id.botonPunto);
        botonIgual = (Button)findViewById(R.id.botonIgual);
        botonC = (Button)findViewById(R.id.botonC);
        botonSigno = (Button)findViewById(R.id.botonSigno);

        texto = (TextView)findViewById(R.id.texto);

    }

    private void mostrarResultado() {
        switch (operacion) {
            case "+":
                resultado = operando1+operando2;
                break;
            case "-":
                resultado = operando1-operando2;
                break;
            case "*":
                resultado = operando1*operando2;
                break;
            case "/":
                resultado = operando1/operando2;
                break;
            default:
                break;
        }
        texto.setText(Double.toString(resultado));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarComponentes();

        loperando1 = loperando2 = false;
        lNuevoNumero = true;
        texto.setText("0");

        boton0.setOnClickListener(this);
        boton1.setOnClickListener(this);
        boton2.setOnClickListener(this);
        boton3.setOnClickListener(this);
        boton4.setOnClickListener(this);
        boton5.setOnClickListener(this);
        boton6.setOnClickListener(this);
        boton7.setOnClickListener(this);
        boton8.setOnClickListener(this);
        boton9.setOnClickListener(this);
        botonSuma.setOnClickListener(this);
        botonResta.setOnClickListener(this);
        botonDivision.setOnClickListener(this);
        botonMultiplicacion.setOnClickListener(this);
        botonPunto.setOnClickListener(this);
        botonC.setOnClickListener(this);
        botonSigno.setOnClickListener(this);
        botonIgual.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String entrada = (String)((Button)v).getText();
        switch (v.getId()) {
            case R.id.botonRestar:
            case R.id.botonSumar:
            case R.id.botonDividir:
            case R.id.botonMultiplicar:
            case R.id.botonIgual:
                lNuevoNumero = true;
                loperando1 = false;
                if (loperando2) {
                    operando2 = Double.parseDouble(texto.getText().toString());
                    mostrarResultado();
                    operando1 = resultado;
                } else {
                    operando1 = Double.parseDouble(texto.getText().toString());
                    loperando2 = true;
                }
                operacion = entrada;
                break;
            case R.id.botonC:
                texto.setText("0");
                lNuevoNumero = true;
                operando1 = operando2 = 0.0;
                loperando1 = loperando2 = false;
                break;
            case R.id.botonSigno:
                resultado = Double.parseDouble(texto.getText().toString()) * -1;
                texto.setText(Double.toString(resultado));
                break;
            case R.id.botonPunto:
                if (texto.getText().toString().indexOf(".") < 0)
                    texto.setText(texto.getText() + ".");
                break;
            default:
                if (lNuevoNumero) {
                    texto.setText(entrada);
                    lNuevoNumero = false;
                } else {
                    if (texto.getText().length() < 8)
                        texto.setText(texto.getText() + entrada);
                }
        }
    }
}