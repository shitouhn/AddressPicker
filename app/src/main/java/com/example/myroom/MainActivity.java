package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myroom.databinding.ActivityMainBinding;
import com.example.myroom.db.AppDataBaseUtil;
import com.example.myroom.db.Area;
import com.example.myroom.db.AreaDao;
import com.example.myroom.popup.addr.AddressPopupWindow;
import com.example.myroom.util.ToastUtils;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    private AreaDao dao;
    private AddressPopupWindow addrWindow;
    private Map<Integer, Area> areaMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = viewBinding.getRoot();
        setContentView(root);
        dao = AppDataBaseUtil.getDataBase().areaDao();
        viewBinding.queryButton.setOnClickListener(this::onButtonClick);
        addrWindow = new AddressPopupWindow(this);
        addrWindow.setOnSelectedAreaListener(this::onSelectedArea);
    }


    private void onButtonClick(View view){
        addrWindow.show(view);
    }

    private void onSelectedArea(Map<Integer, Area> areaMap) {
        if (areaMap == null || areaMap.isEmpty()) {
            ToastUtils.show("无选择数据！");
            return;
        }
        this.areaMap = areaMap;
        Iterator<Map.Entry<Integer,Area>> iterator = areaMap.entrySet().iterator();
        String addressName = "";
        while (iterator.hasNext()){
            if (addressName.isEmpty()) addressName = iterator.next().getValue().AreaName;
            else addressName += "\n" + iterator.next().getValue().AreaName;
        }

        viewBinding.addrSelected.setText(addressName);
        ToastUtils.show(addressName);
    }
}