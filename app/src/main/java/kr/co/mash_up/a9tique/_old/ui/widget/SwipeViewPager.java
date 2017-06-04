package kr.co.mash_up.a9tique._old.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 스크롤을 막을 수 있는 ViewPager
 * http://stackoverflow.com/questions/36552181/disable-swipe-on-some-fragments-in-viewpager/36614266#36614266
 */
public class SwipeViewPager extends ViewPager {

    private boolean enabled;

    public SwipeViewPager(Context context) {
        super(context);
        init();
    }

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onTouchEvent(ev);
        }
//        return false;
        return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(ev);
        }else{
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
                // ignore move action
            }else{
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev);
                }
            }
            return false;
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (this.enabled) {
//            return super.onInterceptTouchEvent(ev);
//        }
//        return false;
//    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
