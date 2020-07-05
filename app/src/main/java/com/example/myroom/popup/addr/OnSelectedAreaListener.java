package com.example.myroom.popup.addr;

import com.example.myroom.db.Area;

import java.util.Map;

/**
 * Created by zhong on 2019/7/15.
 */
public interface OnSelectedAreaListener {
    void onSelected(Map<Integer, Area> selected);
}
