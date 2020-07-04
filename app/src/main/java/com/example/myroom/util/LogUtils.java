package com.example.myroom.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by zhong on 2017/7/2.
 */

/**
 * 日志输出
 */
public class LogUtils {
    private static final String TAG = "LogUtils";
    private static boolean DEBUG = false;
    private static boolean RUN_FLAG = false;//运行标志

    public static void d(String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.d(TAG ,msg + "\n" + getStandardInfo());
        }
    }

    public static void d(String tag, String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.d(TAG , tag + ": " + msg + "\n" + getStandardInfo());
        }
    }

    public static void e(String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.e(TAG , msg + "\n" + getStandardInfo());
        }
    }

    public static void e(String tag, String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.e(TAG, tag + ": " + msg + "\n" + getStandardInfo());
        }
    }

    public static void i(String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.i(TAG, msg + "\n" + getStandardInfo());
        }
    }

    public static void i(String tag, String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.i(TAG, tag + ": " + msg + "\n" + getStandardInfo());
        }
    }

    public static void w(String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.w(TAG, msg + "\n" + getStandardInfo());
        }
    }

    public static void w(String tag, String msg){
        if (!RUN_FLAG){
            setDebugState();//按需初始化
        }

        if (DEBUG) {
            Log.w(TAG, tag + ": " + msg + "\n" + getStandardInfo());
        }
    }

    /**
     * 获取调式StackTrace信息
     * @return
     */
    private static String getStandardInfo(){
        StackTraceElement e = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder("at ")
                .append(e.getClassName()).append(".")
                .append(e.getMethodName()).append("(")
                .append(e.getFileName()).append(":")
                .append(e.getLineNumber()).append(")");
        return sb.toString();
    }

    /**
     * 检测是否运行过isApkDebugable()方法，运行过就忽略
     */
    private static void setDebugState(){
        DEBUG = isApkDebugable(AppContext.getContext());
        RUN_FLAG = true;
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @param context 上下文
     * @return 是否debug模式
     * @author SHANHY
     * @date   2015-8-7
     */
    private static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
