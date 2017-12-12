package com.minorius.makakaplayer;

import android.app.Application;
import android.util.Log;

import com.minorius.makakaplayer.di.component.DaggerPlayerComponent;
import com.minorius.makakaplayer.di.component.PlayerComponent;

/**
 * Created by minorius on 17.11.17.
 */

public class App extends Application {

    public static int selectedSound  = -1;
    public static int selectedPositionArtistByClick = -1;
    public static int currentArtistWhichPlayed = -1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void myLog(Object s){
        Log.d("myLog", ": "+s);
    }


}
