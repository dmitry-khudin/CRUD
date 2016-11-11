package com.thesanmark.clients.client_384.project_1.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class RvLoadMoreAdapter extends RecyclerView.Adapter {
    protected static final int FOOTER_VIEWTYPE = 10086;

    private boolean hasFooter = false;
    private View footerView;
    private RVLoadMoreRvOnScrollListener listener;



    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        if (listener != null) listener.setEnable(hasFooter);
        notifyDataSetChanged();
    }


    public void setLoadMoreListener(RecyclerView recyclerView, RVLoadMoreRvOnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
        this.listener = listener;
        listener.setEnable(isHasFooter());
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEWTYPE)
            return new FootHolder(footerView == null ? defaultFooter(parent) : footerView);
        else return onRvCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(hasFooter && position == getItemCount() - 1))
            onRvBindViewHolder(holder, position);
    }

    @Override
    public final int getItemCount() {
        int rvItemCount = getRvItemCount();
        return rvItemCount == 0 ? 0 : getRvItemCount() + (hasFooter ? 1 : 0);
    }

    @Override
    public final int getItemViewType(int position) {
        if (hasFooter && position == getItemCount() - 1)
            return FOOTER_VIEWTYPE;
        else
            return getRvItemViewType(position);
    }

    public class FootHolder extends RecyclerView.ViewHolder {

        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    private View defaultFooter(ViewGroup parent) {
        TextView footerTv = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        footerTv.setText("Footer");
        return footerTv;
    }


    public abstract int getRvItemCount();


    public abstract int getRvItemViewType(int position);


    public abstract RecyclerView.ViewHolder onRvCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onRvBindViewHolder(RecyclerView.ViewHolder holder, int position);

}
