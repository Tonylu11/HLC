package tony.conecta4;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Tony on 19/01/2017.
 */

public class PrefFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
