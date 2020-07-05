package com.example.myroom.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by zhong on 2019/5/18.
 *
 * Android 版本检测工具
 */


public class AndroidVersionUtil {

    /**
     * Android 7.0及以上 返回true ,否则false
     * @return
     */
    public static boolean geqAndroid_7_0(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Android 6.0及以上 返回true ,否则false
     * @return
     */
    public static boolean geqAndroid_6_0(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Android 5.0及以上 返回true ,否则false
     * @return
     */
    public static boolean geqAndroid_5_0(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Android 4.4及以上 返回true ,否则false
     * @return
     */
    public static boolean geqAndroid_4_4(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 获取App的版本号
     * @return 版本号字串
     */
    public static String getVersionName() {
        try {
            Context context = AppContext.getContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
