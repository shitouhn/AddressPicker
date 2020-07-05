package com.example.myroom.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zhong on 2017/6/23.
 */

public final class AppContext {
    private static Context appContext;
    private static int screenHeight;//屏幕高度
    private static int screenWidth;//屏幕宽度

    private AppContext(){}

    public static synchronized void init(Context context) {
        if (ObjectUtils.isNull(AppContext.appContext)){
            AppContext.appContext = context.getApplicationContext();
        }
    }

    public static Context getContext() {
        return ObjectUtils.requireNonNull(AppContext.appContext,"please call appContext.init(this) in Application onCreate() method");
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getContext().getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        if (screenWidth == 0) {
            screenWidth = DensityUtils.getScreenWidth();
        }
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        if (screenHeight == 0){
            screenHeight = DensityUtils.getScreenHeight();
        }
        return screenHeight;
    }
}
