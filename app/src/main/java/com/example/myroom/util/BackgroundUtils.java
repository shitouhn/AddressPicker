package com.example.myroom.util;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by zhong on 2019/6/24.
 */
public class BackgroundUtils {
    public static StateListDrawable getItemDefaultBackground(){
        return getItemDefaultBackground(0xFFE4E4E4,0xFFFFFFFF);
    }

    public static StateListDrawable getItemDefaultBackground(int pressedColor, int normalColor){
        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(pressedColor);

        GradientDrawable normal = new GradientDrawable();
        normal.setColor(normalColor);

        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_pressed},pressed);
        sld.addState(new int[]{-android.R.attr.state_pressed},normal);

        return sld;
    }
}
