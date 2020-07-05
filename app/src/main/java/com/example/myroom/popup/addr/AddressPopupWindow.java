package com.example.myroom.popup.addr;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myroom.R;
import com.example.myroom.db.AppDataBaseUtil;
import com.example.myroom.db.Area;
import com.example.myroom.util.BackgroundUtils;
import com.example.myroom.util.DensityUtils;
import com.example.myroom.util.LogUtils;
import com.example.myroom.util.ObjectUtils;
import com.example.myroom.view.AreaView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhong on 2019/7/7.
 */
public class AddressPopupWindow extends PopupWindow {
    private AreaView areaView;
    RecyclerView recyclerView;
    MultiTypeAdapter adapter = new MultiTypeAdapter();
    ArrayList<Object> items = new ArrayList<>();
    Disposable disposable;
    Map<Integer, Area> areaMap = new TreeMap<>();
    private OnSelectedAreaListener selectedAreaLinstener;

    public AddressPopupWindow(Context context){
        super( LayoutInflater.from(context).inflate(R.layout.address_popup_window,null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true );
        initView();
    }

    private void initView() {
        setBackgroundDrawable(new ColorDrawable());

        View root = getContentView();
        TextView tvClose = root.findViewById(R.id.address_close);
        tvClose.setOnClickListener(v -> dismiss());
        areaView = root.findViewById(R.id.address_selected);
        recyclerView= root.findViewById(R.id.address_recycler_view);
        adapter.setItems(items);
        AreaViewBinder areaViewBinder = new AreaViewBinder();
        areaViewBinder.setListener(this::onClickAreaListener);
        adapter.register(Area.class,areaViewBinder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    public void setOnSelectedAreaListener(OnSelectedAreaListener listener){
        selectedAreaLinstener = listener;
    }

    public void show(View anchor){
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (items.isEmpty() && areaMap.isEmpty()){
            disposable = AppDataBaseUtil.getDataBase().areaDao().getProvince()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areas -> {
                        disposable.dispose();
                        items.addAll(areas);
                        adapter.notifyDataSetChanged();
                        super.showAsDropDown(anchor,xoff,yoff,gravity);
                    });
        } else {
            super.showAsDropDown(anchor,xoff,yoff,gravity);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (items.isEmpty() && areaMap.isEmpty()){
            disposable = AppDataBaseUtil.getDataBase().areaDao().getProvince()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areas -> {
                        disposable.dispose();
                        items.addAll(areas);
                        adapter.notifyDataSetChanged();
                        super.showAtLocation(parent, gravity, x, y);
                    });
        } else {
            super.showAtLocation(parent, gravity, x, y);
        }
    }

    private void removeSelected(Area area){
        if (area.AreaLevel <= areaMap.size()){
            int removeId = area.AreaLevel - 1;
            ArrayList<View> views = new ArrayList<>();
            for (int i = removeId; i < areaView.getChildCount(); i++) {
                views.add(areaView.getChildAt(i));
            }
            for (View v : views) {
                areaView.removeView(v);
                Area a = (Area) v.getTag();
                areaMap.remove(a.AreaLevel);
            }
            views.clear();
        }
    }

    private void onClickAreaListener(Area area){
        Area selected = null;
        int count = areaView.getChildCount();
        //检测哪个是选中的Area
        for (int i = 0; i < count; i++) {
            View view = areaView.getChildAt(i);
            if (view.isSelected()){
                selected = (Area) view.getTag();
                break;
            }
        }

        //选中的Area与列表显示的数据一样，不需要更新，直接返回
        if (selected != null && selected.AreaNo.equals(area.AreaNo)){
            return;
        }
        removeSelected(area);

        areaMap.put(area.AreaLevel,area);
        if (areaView.getChildCount() > 1){
            View lastChild = areaView.getChildAt(areaView.getChildCount() - 1);
            Area tagArea = (Area) lastChild.getTag();
            if ("0".equals(tagArea.AreaNo)){
                areaView.removeView(lastChild);
            }
        }

        TextView tvItem = newTextView(area);
        areaView.addView(tvItem);

        disposable = AppDataBaseUtil.getDataBase().areaDao().getAreaByParentId(area.AreaNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(areas -> {
                    disposable.dispose();
                    if (areas == null || areas.isEmpty()){
                        if (ObjectUtils.nonNull(selectedAreaLinstener)){
                            selectedAreaLinstener.onSelected(areaMap);
                        }
                        dismiss();
                        return;
                    }
                    Area tempArea = new Area();
                    tempArea.AreaNo = "0";
                    tempArea.ParentId = area.AreaNo;
                    Area tArea = areas.get(0);
                    tempArea.AreaLevel = tArea.AreaLevel;
                    LogUtils.d("请选择 = " + area.AreaNo);
                    switch (area.AreaLevel){
                        case 1:
                            tempArea.AreaName = "请选择市/区";
                            break;
                        case 2:
                            tempArea.AreaName = "请选择区/县";
                            break;
                        case 3:
                            tempArea.AreaName = "请选择乡/镇";
                            break;
                    }
                    TextView next = newTextView(tempArea);
                    areaView.addView(next);
                    items.clear();
                    items.addAll(areas);
                    recyclerView.getLayoutManager().scrollToPosition(0);
                    adapter.notifyDataSetChanged();
                });
    }

    private TextView newTextView(Area area){
        TextView tvItem = new TextView(areaView.getContext());
        tvItem.setText(area.AreaName);
        ColorStateList csl = areaView.getContext().getResources().getColorStateList(R.color.addr_text_color);
        tvItem.setTextColor(csl);
        int paddingTop = DensityUtils.dp2px(12);
        int paddingStart = DensityUtils.dp2px(25);
        int paddingEnd = DensityUtils.dp2px(15);
        tvItem.setPadding(paddingStart,paddingTop,paddingEnd,paddingTop);
        tvItem.setBackground(BackgroundUtils.getItemDefaultBackground());
        tvItem.setTag(area);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        tvItem.setLayoutParams(params);

        tvItem.setOnClickListener( v -> {
            Area a = (Area) v.getTag();
            disposable = AppDataBaseUtil.getDataBase().areaDao().getAreaByParentId(a.ParentId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areas -> {
                        disposable.dispose();
                        items.clear();
                        items.addAll(areas);
                        recyclerView.getLayoutManager().scrollToPosition(0);
                        adapter.notifyDataSetChanged();
                        v.setSelected(true);
                        int count = areaView.getChildCount();
                        for (int i = 0; i < count; i++) {
                            View tv = areaView.getChildAt(i);
                            if (tv != v) tv.setSelected(false);
                        }
                    });
        });
        return tvItem;
    }
}
