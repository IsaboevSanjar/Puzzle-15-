package uz.isytem.puzzle15.core.app;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;

public class App extends Application {
    private static MediaPlayer audioPlayer;
    private static Context appContext;
    private static int lastMediaPoint = 0_000;//in millisecond

    public static void startMedia() {
        audioPlayer = MediaPlayer.create(appContext, R.raw.main_music);
        if (lastMediaPoint <= audioPlayer.getDuration() - 2_000) {
            audioPlayer.seekTo(lastMediaPoint);
        }
        audioPlayer.setLooping(true);
        if (audioPlayer != null) {
            audioPlayer.start();
        }
    }

    public static void stopMedia() {
        if (audioPlayer != null) {
            lastMediaPoint = audioPlayer.getCurrentPosition();
            audioPlayer.release();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MemoryHelper.init(this);
        appContext = this;
        //audioPlayer=null;
        if (MemoryHelper.getHelper().getSoundState()) {
            startMedia();
        } else {
            stopMedia();
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopMedia();
        audioPlayer = null;
    }
}
