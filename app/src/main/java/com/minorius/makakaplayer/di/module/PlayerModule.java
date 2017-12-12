package com.minorius.makakaplayer.di.module;

import android.media.MediaPlayer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by minorius on 17.11.17.
 */

@Module
public class PlayerModule {


    @Provides
    MediaPlayer provideMediaPlayer(){
        return new MediaPlayer();
    }
}
