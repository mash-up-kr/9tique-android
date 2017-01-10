package kr.co.mash_up.a9tique;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import kr.co.mash_up.a9tique.common.AccountManager;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.remote.RequestUser;
import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.products.SellerProductListActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;

/**
 * 유효한 세션이 있다는 검증 후
 * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
 */
public class KaKaoSignupActivity extends AppCompatActivity {

    public static final String TAG = KaKaoSignupActivity.class.getSimpleName();

    AccountManager mAccountManager;

    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountManager = AccountManager.getInstance();
        requestMe();
    }

    protected void showSignup() {
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failded to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(KaKaoSignupActivity.this, getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "onSessionClosed");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                Toast.makeText(KaKaoSignupActivity.this, "카카오톡 회원가입이 필요합니다", Toast.LENGTH_SHORT).show();
//                showSignup();
                Log.d(TAG, "onNotSignedUp");
            }

            //Todo: 유저 정보로 이메일을 사용하려면 따로 입력받아서 보내야 한다.
            @Override
            public void onSuccess(UserProfile userProfile) {
                // 앱연결과정에서 발급하는 고유아이디
                Logger.d("UserProfile: " + userProfile);
                //Todo: AccountManager 사용하는 로직으로 변경
                mAccountManager.setKakaoId(String.valueOf(userProfile.getId()));

                // request access token
                RequestUser requestUser = new RequestUser(
                        mAccountManager.getKakaoId(),
                        RequestUser.Type.KAKAO);

                BackendHelper.getInstance().login(requestUser, new ResultCallback<User>() {

                    @Override
                    public void onSuccess(User user) {
                        PreferencesUtils.putString(KaKaoSignupActivity.this, Constants.PREF_ACCESS_TOKEN, user.getAccessToken());
                        PreferencesUtils.putString(KaKaoSignupActivity.this, Constants.PREF_USER_LEVEL, user.getUserLevel());

                        //Todo: 셋팅화면으로 옮겨야함
                        BackendHelper.getInstance().registerSeller(new ResultCallback<User>() {
                            @Override
                            public void onSuccess(User user) {
                                PreferencesUtils.putString(KaKaoSignupActivity.this, Constants.PREF_ACCESS_TOKEN, user.getAccessToken());
                                PreferencesUtils.putString(KaKaoSignupActivity.this, Constants.PREF_USER_LEVEL, user.getUserLevel());

                                //Todo: redirect
                            }

                            @Override
                            public void onFailure() {
                                //Todo: redirect, 어쩌지...?
                            }
                        });

                        redirectProductListActivity(user.getUserLevel());
                    }

                    @Override
                    public void onFailure() {
                        redirectLoginActivity();
                    }
                });
            }
        });
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /**
     * 판매자, 유저인지 확인하고 분기 시킨다.
     */
    private void redirectProductListActivity(String userLevel) {
        switch (userLevel){
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
