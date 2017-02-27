package kr.co.mash_up.a9tique.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import kr.co.mash_up.a9tique.service.TokenRefreshService;
import kr.co.mash_up.a9tique.common.AccountManager;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestUser;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.products.ProductsActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;

    @BindView(R.id.fb_login)
    LoginButton mLoginButton;

    private Unbinder mUnbinder;
    private CallbackManager mCallbackManager;  // facebook
//    private SessionCallback mSessionCallback;  // kakao

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  자동 로그인, access token이 저장되 있으면 바로 메인으로 넘어간다.
        String accessToken = PreferencesUtils.getString(LoginActivity.this, Constants.PREF_ACCESS_TOKEN, "");
        String strUserLevel = PreferencesUtils.getString(LoginActivity.this, Constants.PREF_USER_LEVEL, "");
        Log.e(TAG, accessToken + " " + strUserLevel);

        if (strUserLevel != null && !"".equals(strUserLevel)) {
            AccountManager.getInstance().initAccountInformation(accessToken, strUserLevel);

            // 로그인 기록이 있으면 토큰 갱신
            startService(new Intent(getApplicationContext(), TokenRefreshService.class));
            redirectProductListActivity();
        } else {
//            mSessionCallback = new SessionCallback();
//            Session.getCurrentSession().addCallback(mSessionCallback);
//            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
//                setContentView(R.layout.activity_login);
//            }
            setContentView(R.layout.activity_login);
            mUnbinder = ButterKnife.bind(this);

            // 같은 폰에서 다른 id로 로그인하면??
            // 해당 이메일을 찾아서 회원가입 여부를 가려야한다, 이메일로 토큰을 찾자!
            mCallbackManager = CallbackManager.Factory.create();
            mLoginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
            mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    ProgressUtil.showProgressDialog(LoginActivity.this);

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            (object, response) -> {
                                String email = object.optString("email");
                                String userName = object.optString("name");
                                RequestUser requestUser = new RequestUser(userName, email, RequestUser.OauthType.FB);
                                BackendHelper.getInstance().login(requestUser, new ResultCallback<User>() {
                                    @Override
                                    public void onSuccess(@Nullable User user) {
                                        ProgressUtil.hideProgressDialog();
                                        AccountManager.getInstance().updateAccountInformation(LoginActivity.this, user);
                                        redirectProductListActivity();
                                    }

                                    @Override
                                    public void onFailure() {
                                        ProgressUtil.hideProgressDialog();
                                        // access token을 가지고 있으면서 api request 실패시 로그아웃
                                        SnackbarUtil.showMessage(LoginActivity.this, mClRoot, "로그인 실패", "", null);
                                        LoginManager.getInstance().logOut();
                                    }
                                });
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    // Fb 로그인 실패
                }

                @Override
                public void onError(FacebookException error) {
                    // Fb 로그인 실패
                }
            });
        }
    }

    /**
     * Facebook oauth login
     */
    @OnClick(R.id.btn_fb_login)
    public void onClickFbLogin() {
        mLoginButton.performClick();
    }

    /**
     * Oauth 로그인 결과 전달
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
//        if (mSessionCallback != null) {
//            Session.getCurrentSession().removeCallback(mSessionCallback);
//        }
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

    /**
     * 현재 화면을 종료하고 상품 리스트 화면으로 이동
     */
    private void redirectProductListActivity() {
        startActivity(new Intent(LoginActivity.this, ProductsActivity.class));
        finish();
    }
}