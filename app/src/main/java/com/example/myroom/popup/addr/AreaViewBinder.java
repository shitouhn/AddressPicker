package com.example.myroom.popup.addr;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myroom.R;
import com.example.myroom.db.Area;
import com.example.myroom.util.AssignmentUtil;
import com.example.myroom.util.ObjectUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;


/**
 * Created by zhong on 2019/7/8.
 */
public class AreaViewBinder extends ItemViewBinder<Area, AreaViewBinder.ViewHolder> {
    private OnAreaClickListener listener;

    public void setListener(OnAreaClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_area, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Area area) {
        holder.tvArea.setOnClickListener(v -> {
            if (ObjectUtils.nonNull(listener)) listener.onClickArea(area);
        });
        holder.render(area);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvArea;
        ViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.addr_area_name);
        }

        void render(Area area){
            AssignmentUtil.setText(tvArea,area.AreaName);
        }
    }
}
