package kr.co.mash_up.a9tique.ui.addeditproduct;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 특정한 방향에만 여백을 주는 Decoration
 */
public class OrientationSpacingItemDecoration extends RecyclerView.ItemDecoration {

    public enum Orientation {
        RIGHT,
        LEFT,
        BOTTOM,
        TOP
    }

    private final int spacingSize;
    private Orientation mOrientation;
    private boolean header;

    public OrientationSpacingItemDecoration(int spacingSize, Orientation orientation, boolean header) {
        this.spacingSize = spacingSize;
        mOrientation = orientation;
        this.header = header;
    }

    public OrientationSpacingItemDecoration(int spacingSize, Orientation orientation) {
        this.spacingSize = spacingSize;
        this.mOrientation = orientation;
        this.header = false;
    }

    @Override
    public void getItemOffsets(Rect outRect,  //viewholder에 바인딩된 사각형을 받아옴
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0 || !header) {
            switch (mOrientation) {
                case RIGHT:
                    outRect.right = spacingSize;
                    break;
                case LEFT:
                    outRect.left = spacingSize;
                    break;
                case BOTTOM:
                    outRect.bottom = spacingSize;
                    break;
                case TOP:
                    outRect.top = spacingSize;
                    break;
            }
        }
    }
}
