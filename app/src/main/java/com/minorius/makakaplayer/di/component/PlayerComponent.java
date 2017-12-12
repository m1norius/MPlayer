package com.minorius.makakaplayer.di.component;

import com.minorius.makakaplayer.di.module.PlayerModule;
import com.minorius.makakaplayer.modul.artist.MainActivity;

import dagger.Component;

/**
 * Created by minorius on 22.11.17.
 */
@Component(modules = PlayerModule.class)
public interface PlayerComponent {
    void inject(MainActivity mainActivity);
}
