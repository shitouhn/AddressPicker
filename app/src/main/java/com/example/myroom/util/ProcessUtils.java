package com.example.myroom.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by zhong on 2017/7/29.
 */

public class ProcessUtils {

    /**
     * 获取指定进程名称
     * @param context
     * @param pid
     * @return
     */
    public static String getProcessName(Context context, int pid) {
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = mActivityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo p : processInfoList) {
            if (p.pid == pid) {
                return p.processName;
            }
        }
        return null;
    }

    /**
     * 当前应用进程
     * @return true 当前应用进程
     */
    public static boolean isAppOwnProcess(Context context){
        String packageName = context.getPackageName();
        String processName = getProcessName(context,android.os.Process.myPid());
        return ObjectUtils.equals(processName,packageName);
    }
}
