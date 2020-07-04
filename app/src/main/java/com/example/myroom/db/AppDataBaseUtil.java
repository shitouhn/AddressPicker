package com.example.myroom.db;

import android.content.Context;


import com.example.myroom.R;
import com.example.myroom.util.AppContext;
import com.example.myroom.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.room.Room;

/**
 * Created by zhong on 2019/7/6.
 */
public class AppDataBaseUtil {
    private static AppDataBase appDataBase;
    public static AppDataBase getDataBase(){
        if (appDataBase == null){
            String dbName = "areas.db";
            copyDbIfNotExist(AppContext.getContext(),dbName);
            appDataBase = Room.databaseBuilder(
                    AppContext.getContext()
                    ,AppDataBase.class
                    ,dbName
            ).build();
        }
        return appDataBase;
    }

    private static void copyDbIfNotExist(Context context,String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        if ( !dbFile.exists() ) {
            try {
                // 防止上级目录不存在
                File parentDir = dbFile.getParentFile();
                if (!parentDir.exists() && !parentDir.mkdir()) {
                    LogUtils.e("Create \"" + parentDir.getAbsolutePath() + "\" fail!");
                }

                FileOutputStream out = new FileOutputStream(dbFile);
                InputStream in = context.getResources().openRawResource(R.raw.areas);

                byte[] buffer = new byte[1024];
                int readBytes;

                while ((readBytes = in.read(buffer)) != -1){
                    out.write(buffer, 0, readBytes);
                }

                in.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
