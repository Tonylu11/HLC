package tony.conecta4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by Tony on 28/11/2016.
 */

public class Inicio extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return super.onTouchEvent(event);

    }
}
