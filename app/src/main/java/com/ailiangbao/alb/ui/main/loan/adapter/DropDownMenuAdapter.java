package com.ailiangbao.alb.ui.main.loan.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.Collections;
import java.util.List;

public class DropDownMenuAdapter extends RecyclerView.Adapter<DropDownMenuAdapter.ItemViewHolder> {
    private TextView lastView;
    private List<ScreeningConditionEntity.KeyValueEntity> list;
    private OnDropDownMenuAdapterLisetener onDropDownMenuAdapterLisetener;

    public interface OnDropDownMenuAdapterLisetener {
        void onClick(String labelKey, String labelValue);
    }

    public void setOnDropDownMenuAdapterListener(OnDropDownMenuAdapterLisetener onDropDownMenuAdapterLisetener) {
        this.onDropDownMenuAdapterLisetener = onDropDownMenuAdapterLisetener;
    }

    @NonNull
    @Override
    public DropDownMenuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_all_loan_fragment_drop_down_menu, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DropDownMenuAdapter.ItemViewHolder holder, int position) {
        ScreeningConditionEntity.KeyValueEntity keyValueEntity = list.get(position);
        if (position == 0) {
            holder.tv.setTextColor(ResUtil.getColor(R.color.bt_red));
            lastView = holder.tv;
        } else {
            holder.tv.setTextColor(ResUtil.getColor(R.color.FF333333));
        }
        holder.tv.setText(keyValueEntity.getName());
    }

    @Override
    public int getItemCount() {
        return CollectionUtil.isEmpty(list) ? 0 : list.size();
    }

    public void setData(List<ScreeningConditionEntity.KeyValueEntity> list) {
        Collections.sort(list);
        this.list = list;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener
            , View.OnClickListener {

        private final TextView tv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv = itemView.findViewById(R.id.item_all_loan_fragment_drop_down_menu_tv);
        }

        @Override
        public void onClick(View v) {
            if (lastView == tv) return;
            lastView.setTextColor(ResUtil.getColor(R.color.FF333333));
            tv.setTextColor(ResUtil.getColor(R.color.bt_red));
            lastView = tv;
            if (onDropDownMenuAdapterLisetener != null) {
                int position = getAdapterPosition();
//                if (position != 0) {
                    ScreeningConditionEntity.KeyValueEntity keyValueEntity = list.get(position);
                    onDropDownMenuAdapterLisetener.onClick(keyValueEntity.getKey(), keyValueEntity.getName());
//                } else {
//                    onDropDownMenuAdapterLisetener.onClick("", "");
//                }
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    tv.setTextColor(ResUtil.getColor(R.color.bt_red));
//                } else {
//                    tv.setTextColor(ResUtil.getColor(R.color.FF333333));
//                }
        }
    }
}
