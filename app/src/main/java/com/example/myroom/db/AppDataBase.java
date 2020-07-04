package com.example.myroom.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by zhong on 2019/7/6.
 */
@Database(entities = {Area.class},version = 1,exportSchema=false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract AreaDao areaDao();
}
