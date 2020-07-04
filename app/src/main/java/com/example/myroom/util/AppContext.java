package com.example.myroom.util;

import android.content.Context;

/**
 * Created by zhong on 2017/6/23.
 */

public final class AppContext {
    private static Context appContext;

    private AppContext(){}

    public static synchronized void init(Context context) {
        if (ObjectUtils.isNull(AppContext.appContext)){
            AppContext.appContext = context.getApplicationContext();
        }
    }

    public static Context getContext() {
        return ObjectUtils.requireNonNull(AppContext.appContext,"please call appContext.init(this) in Application onCreate() method");
    }
}
