package kr.co.mash_up.a9tique._old.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class RecyclerViewEmptySupport extends RecyclerView {

    @Nullable
    private View mEmptyView;
    private boolean mHeader;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter<?> oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        checkIfEmpty();
    }

    protected void checkIfEmpty() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null && mEmptyView != null) {
            boolean emptyViewVisible;
            if (mHeader) {
                emptyViewVisible = adapter.getItemCount() == 1;
            } else {
                emptyViewVisible = adapter.getItemCount() == 0;
            }

            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            RecyclerViewEmptySupport.this.setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.mEmptyView = emptyView;
        checkIfEmpty();
    }

    public boolean isHeader() {
        return mHeader;
    }

    public void setHeader(boolean header) {
        mHeader = header;
    }
}
