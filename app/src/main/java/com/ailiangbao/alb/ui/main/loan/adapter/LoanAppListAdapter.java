package com.ailiangbao.alb.ui.main.loan.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ailiangbao.alb.ui.base.view.HLoanAppItemView;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.List;

public class LoanAppListAdapter extends RecyclerView.Adapter<LoanAppListAdapter.ItemViewHolder> {
    private static final int HEAD = 0;
    private static final int NORMAL = 1;
    private List<LoanEntity> list;
    private View headView;
    private OnHeadClickListener onHeadClickListener;

    public interface OnHeadClickListener {
        void onClick();
    }

    public void setOnHeadClickListener(OnHeadClickListener onHeadClickListener) {
        this.onHeadClickListener = onHeadClickListener;
    }

    public void addMoreList(List<LoanEntity> list) {
        if (!CollectionUtil.isEmpty(this.list)) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }
    }

    public void setList(List<LoanEntity> list) {
        this.list = list;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case HEAD:
                view = headView;
                break;
            case NORMAL:
                view = new HLoanAppItemView(viewGroup.getContext());
                break;
            default:
                break;
        }
        return new ItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEAD:
                break;
            case NORMAL:
                LoanEntity loansBean = list.get(position - (headView == null ? 0 : 1));
                ((HLoanAppItemView) itemViewHolder.itemView).setData(loansBean);
                if (position == getItemCount() - 1) {
                    ((HLoanAppItemView) itemViewHolder.itemView).showBottomLine();
                } else {
                    ((HLoanAppItemView) itemViewHolder.itemView).hideBottomLine();
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (headView == null ? 0 : 1) + (CollectionUtil.isEmpty(list) ? 0 : list.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null) {
            return HEAD;
        } else {
            return NORMAL;
        }
//        return super.getItemViewType(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ItemViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case HEAD:
                    itemView.setOnClickListener(this);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (onHeadClickListener != null) {
                onHeadClickListener.onClick();
            }
        }
    }
}
