package kr.co.mash_up.a9tique._old.ui.addeditproduct.categorysleleciton;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.data.MainCategory;

/**
 * Created by Dong on 2016-11-20.
 */

public class MainCategoryWithSubViewHolder extends MainCategoryWithoutSubViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    @BindView(R.id.tv_main_category_name)
    TextView mTvMainCategoryName;

    @BindView(R.id.iv_arrow_expand)
    ImageView mIvArrowExpand;

    public static MainCategoryWithSubViewHolder newInstance(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_with_sub_category_list, parent, false);
        return new MainCategoryWithSubViewHolder(itemView);
    }

    public MainCategoryWithSubViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull MainCategory mainCategory) {
        mTvMainCategoryName.setText(mainCategory.getName());
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                mIvArrowExpand.setRotation(ROTATED_POSITION);
            } else {
                mIvArrowExpand.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        RotateAnimation rotateAnimation;
        if (expanded) {  // rotate clockwise
            rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else {  // rotate counterclockwise
            rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        mIvArrowExpand.startAnimation(rotateAnimation);
    }
}
