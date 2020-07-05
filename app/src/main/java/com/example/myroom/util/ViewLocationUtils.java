package com.example.myroom.util;

import android.view.View;

/**
 * Created by zhong on 2018/10/23.
 */
public class ViewLocationUtils {
    public static int getHeightOnScreen(View view){
        int[] size = new int[2];
        view.getLocationOnScreen(size);
        int h = size[1] + view.getHeight();
        return AppContext.getScreenHeight() - h;
    }
}
