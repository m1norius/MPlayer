package com.minorius.makakaplayer.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by minorius on 20.11.17.
 */

public class FragmentNavigator {

    private  int contId;
    private FragmentManager fragmentManager;

    public FragmentNavigator() {

    }

    public FragmentNavigator(int contId, FragmentManager fragmentManager) {
        this.contId = contId;
        this.fragmentManager = fragmentManager;
    }

    public FragmentNavigator setContId(int contId) {
        this.contId = contId;
        return this;
    }

    public FragmentNavigator setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }



    public void replaceFragment(Fragment fragment, boolean addToBackStack){
        handleError();
        FragmentTransaction ft = fragmentManager
                .beginTransaction()
                .replace(contId,fragment);

        fragmentManager.popBackStack();
        if(addToBackStack){
            ft.addToBackStack("");
        }
        ft.commit();

    }

    public void addFragment(Fragment fragment, boolean addToBackStack){
        handleError();
        FragmentTransaction ft = fragmentManager
                .beginTransaction()
                .add(contId,fragment);

        fragmentManager.popBackStack();
        if(addToBackStack){
            ft.addToBackStack("");
        }
        ft.commit();

    }


    private void handleError() {
        if (fragmentManager == null) {
            throw new  RuntimeException(new Throwable("Fragment manager can't be null"));
        }
        if (contId == 0) {
            throw new  RuntimeException(new Throwable("Set container ID"));
        }
    }
}
