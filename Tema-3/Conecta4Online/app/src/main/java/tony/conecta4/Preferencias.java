package tony.conecta4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;

/**
 * Created by Tony on 19/01/2017.
 */

public class Preferencias extends PreferenceActivity{
    public final static String PLAY_MUSIC_KEY = "actdes";
    public final static boolean PLAY_MUSIC_DEFAULT = true;
    /*public final static String PLAYER_INICIAL_KEY = "jugadorinicial";
    public final static String PLAYER_INICIAL_DEFAULT = "Persona";
    public final static String PLAYER_NAME_KEY = "nombre";
    public final static String PLAYER_NAME_DEFAULT = "Jugador";*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PrefFragment fragment = new PrefFragment();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();*/
    }
}
