package com.example.myroom.util;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by zhong on 2017/6/20.
 */

/**
 * Toast工具
 */
public class ToastUtils {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public static void show(@StringRes int resId) {
        show(resId, LENGTH_SHORT);
    }

    public static void show(@NonNull CharSequence text) {
        show(text, LENGTH_SHORT);
    }

    public static void show(@StringRes int resId, int duration) {
        show(AppContext.getContext().getString(resId), duration);
    }

    public static void show( @StringRes int resId, Object... args) {
        show(AppContext.getContext().getString(resId), args);
    }

    public static void show(String format, Object... args) {
        show(format, LENGTH_SHORT, args);
    }

    public static void show(@StringRes int resId, int duration, Object... args) {
        show(AppContext.getContext().getString(resId), duration, args);
    }

    public static void show(String format, int duration, Object... args) {
        show( String.format(format, args), duration);
    }

    public static void show(@NonNull CharSequence text, int duration) {
        Toast.makeText(AppContext.getContext(), text, duration).show();
    }

    private ToastUtils(){}
}
