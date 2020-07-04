package com.example.myroom.app;

import android.app.Application;

import com.example.myroom.util.AppContext;

/**
 * Created by zhong on 2020/6/30.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this);
    }
}
