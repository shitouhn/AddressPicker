package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.myroom.databinding.ActivityMainBinding;
import com.example.myroom.db.AppDataBaseUtil;
import com.example.myroom.db.Area;
import com.example.myroom.db.AreaDao;
import com.example.myroom.util.LogUtils;
import com.example.myroom.util.ToastUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    private AreaDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = viewBinding.getRoot();
        setContentView(root);
        dao = AppDataBaseUtil.getDataBase().areaDao();
        viewBinding.queryButton.setOnClickListener(this::onButtonClick);
    }


    private void onButtonClick(View view){
        Disposable disposable = AppDataBaseUtil.getDataBase().areaDao().getProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(areas -> {
                    ToastUtils.show("大小" + areas.size());
                    String name = "";
                    for (Area area :areas) {
                        name += area.AreaName + "\n";
                    }
                    LogUtils.d(name);
                });
    }
}