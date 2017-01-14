package kr.co.mash_up.a9tique;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.ui.products.SellerProductListActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private SessionCallback mSessionCallback;

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      자동 로그인, access token이 저장되 있으면 바로 메인으로 넘어간다.
        String token = PreferencesUtils.getString(LoginActivity.this, Constants.PREF_ACCESS_TOKEN, "");
        String userLevel = PreferencesUtils.getString(LoginActivity.this, Constants.PREF_USER_LEVEL, "");
        //Todo: token 만료일 확인하고 서버로 token refresh request.

        if (userLevel != null && !"".equals(userLevel)) {
            redirectProductListActivity(userLevel);
        } else {
            mSessionCallback = new SessionCallback();
            Session.getCurrentSession().addCallback(mSessionCallback);
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                setContentView(R.layout.activity_login);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSessionCallback != null) {
            Session.getCurrentSession().removeCallback(mSessionCallback);
        }
    }

    /**
     * Kakao Talk Login session callback
     */
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d(TAG, "onSessionOpened");
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            Log.d(TAG, "onSessionOpenFailed");
            setContentView(R.layout.activity_login);
        }
    }

    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, KaKaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /**
     * 판매자, 유저인지 확인하고 분기 시킨다.
     */
    private void redirectProductListActivity(String userLevel) {
        switch (userLevel) {
            case "USER":
                //Todo: start user product list activity
                startActivity(new Intent(this, SellerProductListActivity.class));
                break;
            case "SELLER":
                startActivity(new Intent(this, SellerProductListActivity.class));
                break;
        }
        finish();
    }
}