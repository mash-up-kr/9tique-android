package kr.co.mash_up.a9tique.ui.addeditproduct;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dong on 2017-01-05.
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacingSize;

    public SpacingItemDecoration(int spacingSize) {
        this.spacingSize = spacingSize;
    }

    @Override
    public void getItemOffsets(Rect outRect,  //viewholder에 바인딩된 사각형을 받아옴
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.right = spacingSize;
    }
}
