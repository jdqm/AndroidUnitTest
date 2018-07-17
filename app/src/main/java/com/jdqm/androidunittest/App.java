package com.jdqm.androidunittest;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jdqm on 2018-7-17.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
