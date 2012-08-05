package sedna.jogos.velha.negocio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

public class Music {

	private static MediaPlayer mp = null;

	// Para a execução da musica anterior e inicia a nova
	public static void play(Context context, int resource) {
		stop(context);

		// Inicia apenas se não estiver desabilitada no preferences
		SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(context);

		if (prefs.getBoolean("music", true)) {
			mp = MediaPlayer.create(context, resource);
			mp.setLooping(false);
			mp.start();
		}
	}

	// Para a execução da musica
	public static void stop(Context context) {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}
}
