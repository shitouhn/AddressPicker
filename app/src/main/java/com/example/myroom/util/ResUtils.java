package com.example.myroom.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;


/**
 * Created by zhong on 2017/6/23.
 */

public class ResUtils {
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(AppContext.getContext(),resId);
    }

    public static int getColor( @ColorRes int resId) {
        return ContextCompat.getColor(AppContext.getContext(),resId);
    }

    public static float getDimension(@DimenRes int resId){
        return getResources().getDimension(resId);
    }

    public static View getView(@LayoutRes int resId){
        return LayoutInflater.from(AppContext.getContext()).inflate(resId,null);
    }

    public static View getView(Context context, @LayoutRes int resId){
        return LayoutInflater.from(context).inflate(resId,null);
    }

    public static View getView(@LayoutRes int resId, ViewGroup parent, boolean attachToParent){
        return LayoutInflater.from(AppContext.getContext()).inflate(resId,parent,attachToParent);
    }

    public static View getView(Context context, @LayoutRes int resId, ViewGroup parent, boolean attachToParent){
        return LayoutInflater.from(context).inflate(resId,parent,attachToParent);
    }

    public static String[] getStringArray(@ArrayRes int resId){
        return getResources().getStringArray(resId);
    }

    public static int[] getIntArray(@ArrayRes int resId){
        return getResources().getIntArray(resId);
    }

    public static CharSequence[] getTextArray(@ArrayRes int resId){
        return getResources().getTextArray(resId);
    }

    public static Resources getResources() {
        return AppContext.getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return AppContext.getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return AppContext.getContext().getAssets();
    }
}
