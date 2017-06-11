package kr.co.mash_up.a9tique.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.ui.main.contents.ContentsFragment;
import kr.co.mash_up.a9tique.ui.main.home.HomeFragment;
import kr.co.mash_up.a9tique.util.ui.FragmentUtil;

/**
 * Created by seokjunjeong on 2017. 6. 4..
 */

public class MainActivity extends AppCompatActivity {
    private int mFragmentContentId;
    private HomeFragment mHomeFragment;
    private ContentsFragment mContentsFragment;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mFragmentContentId = R.id.contentFrame;
        mHomeFragment = new HomeFragment();
        mContentsFragment = new ContentsFragment();

        FragmentUtil.addFragment(this, mFragmentContentId, mHomeFragment);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        hideSlidingMenu();
        mSlidingUpPanelLayout.setFadeOnClickListener(view -> hideSlidingMenu());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.tv_home).setOnClickListener(mOnClickListener);
        toolbar.findViewById(R.id.tv_contents).setOnClickListener(mOnClickListener);
        toolbar.findViewById(R.id.iv_drawer).setOnClickListener(mOnClickListener);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(this, mFragmentContentId, fragment);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_home: {
                    replaceFragment(mHomeFragment);
                }
                break;
                case R.id.tv_contents: {
                    replaceFragment(mContentsFragment);
                }
                break;
                case R.id.iv_drawer: {
                    showSlidingMenu();
                }
                break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mSlidingUpPanelLayout != null &&
                (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)) {
            hideSlidingMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void hideSlidingMenu(){
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void showSlidingMenu(){
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

    }
}
