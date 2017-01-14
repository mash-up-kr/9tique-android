package kr.co.mash_up.a9tique.ui.setting.inquire;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.ui.addeditproduct.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.data.Menu;
import kr.co.mash_up.a9tique.ui.setting.MenuListAdapter;

public class InquireActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.rv_inquire)
    RecyclerView mRvInquire;

    @BindDimen(R.dimen.setting_list_item_margin)
    int itemSpacingSize;

    private MenuListAdapter mMenuListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_inquire;
    }

    @Override
    public void initView() {
        setupToolbar();
        setupList();
    }

    @Override
    public void initFragment(Fragment fragment) {
        // Do nothing
    }

    @UiThread
    private void setupList() {
        mMenuListAdapter = new MenuListAdapter(InquireActivity.this);
        mMenuListAdapter.setOnItemClickListener((setting, position) -> {
            switch (position) {
                case 0:
                    openKakaoOpenChat();
                    break;
                case 1:
                    dialPhoneNumber();
                    break;
                case 2:
                    sendEmail();
                    break;
            }
        });
        mRvInquire.setLayoutManager(new LinearLayoutManager(InquireActivity.this));
        mRvInquire.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM));
        mRvInquire.setAdapter(mMenuListAdapter);
        prepareMenuData();
    }

    private void prepareMenuData() {
        ArrayList<Menu> menus = new ArrayList<>(7);
        menus.add(new Menu("카카오톡 문의하기", MenuListAdapter.VIEW_TYPE_NORMAL));
        menus.add(new Menu("전화 문의하기", MenuListAdapter.VIEW_TYPE_NORMAL));
        menus.add(new Menu("이메일 문의하기", MenuListAdapter.VIEW_TYPE_NORMAL));
        mMenuListAdapter.setData(menus);
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("문의하기");
    }

    /**
     * 뒤로가기 네비게이션 버튼 클릭시 호출되는 콜백 메소드
     *
     * @return 이벤트 처리 여부
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return true;
    }

    private void dialPhoneNumber() {
        //Todo: phone number 리소스로 분리
        String phoneNumber = "010-5582-8778";

        Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL);
        dialPhoneIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (dialPhoneIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialPhoneIntent);
        } else {
            //Todo: show message - 전화 app이 없습니다.. 설치해주세요
        }
    }

    private void sendEmail() {
        //Todo: 문의하기 내용 채우기

        String emailAddress = "dojun8778@gmail.com";
        String subject = "문의하기";
        String content = "I'm email body";

        Intent emailIntent = ShareCompat.IntentBuilder
                .from(InquireActivity.this)
                .setType("application/txt")
                .addEmailTo(emailAddress)
                .setSubject(subject)
                .setText(content)
                .setChooserTitle("Send Email")
                .createChooserIntent();
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            //Todo: show message - email app이 없습니다.. 설치해주세요
        }
    }

    private void openKakaoOpenChat() {
        //Todo: 리소스로 분리
        String openChatLink = "https://open.kakao.com/o/sFyjFYq";

        Intent intent = new Intent();
        intent.setData(Uri.parse(openChatLink));
        startActivity(intent);
    }
}
