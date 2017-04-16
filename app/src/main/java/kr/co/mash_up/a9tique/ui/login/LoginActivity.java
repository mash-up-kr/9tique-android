package kr.co.mash_up.a9tique.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.common.AccountManager;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique.common.eventbus.RxEventBus;
import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestUser;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.service.TokenRefreshService;
import kr.co.mash_up.a9tique.ui.products.ProductsActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

//    private SessionCallback mSessionCallback;  // kakao

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mSessionCallback != null) {
//            Session.getCurrentSession().removeCallback(mSessionCallback);
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
        if (loginFragment == null){
            loginFragment = LoginFragment.newInstance();
            initFragment(loginFragment);
        }

        // Create the presenter
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_login, fragment, LoginFragment.TAG)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
        super.onActivityResult(requestCode, resultCode, data);

        // fb Oauth 로그인 결과 Fragment로 전달
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
        loginFragment.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Kakao Talk Login session callback
     */
//    private class SessionCallback implements ISessionCallback {
//
//        @Override
//        public void onSessionOpened() {
//            Log.d(TAG, "onSessionOpened");
//            redirectSignupActivity();
//        }
//
//        @Override
//        public void onSessionOpenFailed(KakaoException exception) {
//            if (exception != null) {
//                Logger.e(exception);
//            }
//            Log.d(TAG, "onSessionOpenFailed");
//            setContentView(R.layout.activity_login);
//
//            // 로그아웃시에도 나오는지 확인 -> 로그아웃시에는 안나온다.
//            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
//        }
//    }

    /**
     * 현재화면을 종료하고 회원가입 화면으로 이동
     */
//    protected void redirectSignupActivity() {
//        final Intent intent = new Intent(this, KaKaoSignupActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
//        finish();
//    }
}