package com.example.myroom.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.StrictMode;

/**
 * Created by zhong on 2017/6/20.
 */

/**
 * 检测一些程序异常，发现有违规情况则记录 log 并使程序强行退出。
 */
public class StrictModeUtils {

    public static void detect(Context context){
        if (!isApkDebugable(context)) return;

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
    //检测是否是调试模式
    private static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        } catch (Exception e) {

        }

        return false;
    }
}
