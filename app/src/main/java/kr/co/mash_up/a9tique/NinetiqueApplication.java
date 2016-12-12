package kr.co.mash_up.a9tique;

import android.app.Application;

/**
 * Created by Dong on 2016-12-04.
 */

public class NinetiqueApplication extends Application {

    private static NinetiqueApplication instance;

    public static NinetiqueApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
