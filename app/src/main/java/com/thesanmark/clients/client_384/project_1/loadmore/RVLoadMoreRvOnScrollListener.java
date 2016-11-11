package com.thesanmark.clients.client_384.project_1.loadmore;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class RVLoadMoreRvOnScrollListener extends RecyclerView.OnScrollListener {


    public static final int LOAD_MORE_IDLE = 1;

    public static final int LOAD_MORE_IMMEDIATE = 2;

    @IntDef({LOAD_MORE_IDLE, LOAD_MORE_IMMEDIATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMore {
    }


    private int lastVisibleItemPosition;


    private int mLoadMoreMode = LOAD_MORE_IMMEDIATE;


    private boolean enable = true;

    private boolean isLoading = false;

    private LoadMoreFooter loadMoreFooter;

    private RecyclerView.LayoutManager layoutManager;

    private RVLoadMoreRvOnScrollListener() {
        super();
    }

    public RVLoadMoreRvOnScrollListener(LoadMoreFooter loadMoreFooter) {
        this.loadMoreFooter = loadMoreFooter;
    }

    public RVLoadMoreRvOnScrollListener setFooter(LoadMoreFooter loadMoreFooter) {
        this.loadMoreFooter = loadMoreFooter;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        initLayoutManager(recyclerView);
        int position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

        if (enable && !isLoading && layoutManager.getChildCount() > 0) {
            if (mLoadMoreMode == LOAD_MORE_IMMEDIATE) {
                if (position != lastVisibleItemPosition
                        && position == (layoutManager.getItemCount() - 1)) {
                    loadMoreFooter.onLoadMore();
                    onLoadMore(recyclerView);
                    isLoading = true;
                }
            } else if (mLoadMoreMode == LOAD_MORE_IDLE) {
                if (position == (layoutManager.getItemCount() - 1)) {
                    loadMoreFooter.onPrepareLoad();
                    onPrepareLoad(recyclerView);
                }

                if (lastVisibleItemPosition == (layoutManager.getItemCount() - 1) && position == lastVisibleItemPosition - 1) {
                    loadMoreFooter.onLoadCancel();
                    onLoadCancel(recyclerView);
                }
            }
        }
        Log.e("listener:", lastVisibleItemPosition + "===" + position);
        lastVisibleItemPosition = position;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        initLayoutManager(recyclerView);

        if (enable
                && !isLoading
                && mLoadMoreMode == LOAD_MORE_IDLE
                && layoutManager.getChildCount() > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItemPosition == (layoutManager.getItemCount() - 1)) {
            loadMoreFooter.onLoadMore();
            onLoadMore(recyclerView);
            isLoading = true;
        }
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        if (layoutManager == null)
            layoutManager = recyclerView.getLayoutManager();
    }


    public RVLoadMoreRvOnScrollListener setLoadMoreMode(@LoadMore int loadMoreMode) {
        mLoadMoreMode = loadMoreMode;
        return this;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void loadCompleted() {
        isLoading = false;
        loadMoreFooter.onLoadCompleted();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);


    public abstract void onPrepareLoad(RecyclerView recyclerView);


    public abstract void onLoadCancel(RecyclerView recyclerView);
}
