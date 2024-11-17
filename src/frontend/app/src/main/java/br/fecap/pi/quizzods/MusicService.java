package br.fecap.pi.quizzods;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import br.fecap.pi.quizzods.R;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa o MediaPlayer com a música de fundo
        mediaPlayer = MediaPlayer.create(this, R.raw.musica); // Substitua "background_music" pelo nome do seu arquivo
        mediaPlayer.setLooping(true); // Faz a música repetir
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Começa a tocar a música
        }
        return START_STICKY; // Faz o serviço continuar rodando até ser explicitamente parado
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release(); // Libera os recursos do MediaPlayer
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Este serviço não será vinculado a componentes
    }
}

