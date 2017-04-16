package kr.co.mash_up.a9tique.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.AccountManager;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestUser;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.service.TokenRefreshService;
import kr.co.mash_up.a9tique.ui.products.ProductsActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

/**
 * Created by Dong on 2017-04-16.
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.fb_login)
    LoginButton mLoginButton;

    private CallbackManager mCallbackManager;  // facebook

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //  자동 로그인, access token이 저장되 있으면 바로 메인으로 넘어간다.
        String accessToken = PreferencesUtils.getString(getActivity(), Constants.PREF_ACCESS_TOKEN, "");
        String strUserLevel = PreferencesUtils.getString(getActivity(), Constants.PREF_USER_LEVEL, "");

        if (strUserLevel != null && !"".equals(strUserLevel)) {
            AccountManager.getInstance().initAccountInformation(accessToken, strUserLevel);

            // 로그인 기록이 있으면 토큰 갱신
            // Todo: 개선 필요. 무한 루프 위험
            getActivity().startService(new Intent(getActivity().getApplicationContext(), TokenRefreshService.class));
            redirectProductListActivity();
        }
    }

    @Override
    public void initView(View rootView) {
//            mSessionCallback = new SessionCallback();
//            Session.getCurrentSession().addCallback(mSessionCallback);
//            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
//                setContentView(R.layout.activity_login);
//            }

        // 같은 폰에서 다른 id로 로그인하면??
        // 해당 이메일을 찾아서 회원가입 여부를 가려야한다, 이메일로 토큰을 찾자!
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProgressUtil.showProgressDialog(getActivity());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (object, response) -> {
                            String email = object.optString("email");
                            String userName = object.optString("name");
                            RequestUser requestUser = new RequestUser(userName, email, RequestUser.OauthType.FB);
                            BackendHelper.getInstance().login(requestUser, new ResultCallback<User>() {
                                @Override
                                public void onSuccess(@Nullable User user) {
                                    ProgressUtil.hideProgressDialog();
                                    AccountManager.getInstance().updateAccountInformation(getActivity(), user);
                                    redirectProductListActivity();
                                }

                                @Override
                                public void onFailure() {
                                    ProgressUtil.hideProgressDialog();
                                    // access token을 가지고 있으면서 api request 실패시 로그아웃
                                    SnackbarUtil.showMessage(getActivity(), getView(), "로그인 실패", "", null);
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
                Log.d(TAG, "fb login cancel");
            }

            @Override
            public void onError(FacebookException error) {
                // Fb 로그인 실패
                Log.d(TAG, "fb login error");
                Log.e(TAG, error.getMessage());
                error.printStackTrace();
            }
        });
    }

    /**
     * Facebook oauth login
     */
    @OnClick(R.id.lb_fb_login)
    public void onClickFbLogin() {
        mLoginButton.performClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);  // fb Oauth 로그인 결과 전달
    }

    /**
     * 현재 화면을 종료하고 상품 리스트 화면으로 이동
     */
    private void redirectProductListActivity() {
        startActivity(new Intent(getActivity(), ProductsActivity.class));
        getActivity().finish();
    }
}
