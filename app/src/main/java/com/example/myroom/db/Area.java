package com.example.myroom.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhong on 2019/7/6.
 */
@Entity(tableName = "Areas",indices = {@Index("ParentId"),@Index("AreaLevel")})
public class Area {
    @NonNull
    @PrimaryKey
    public String AreaNo;
    @NonNull
    public String AreaName;
    @NonNull
    public String ParentId;
    @NonNull
    public Integer AreaLevel;

    @Override
    public int hashCode() {
        return Integer.valueOf(AreaNo);
    }

    @Override
    public boolean equals(Object obj) {
        return AreaNo.equals(((Area)obj).AreaNo);
    }
}
