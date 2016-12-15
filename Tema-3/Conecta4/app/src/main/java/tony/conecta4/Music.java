package tony.conecta4;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Tony on 28/11/2016.
 */

public class Music {
    private static MediaPlayer mediaPlayer;

    public static void play(Context context, int id) {
        if (mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(context, id);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();
    }
    public static void stop() {
        if (mediaPlayer!=null){
             /*mediaPlayer.release();
            mediaPlayer=null;*/
            mediaPlayer.pause();
        }
    }
}
