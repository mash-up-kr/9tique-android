package kr.co.mash_up.a9tique.ui.setting;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.util.KeyboardUtils;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

/**
 * Created by CY on 2017. 1. 10..
 */

public class SellerRegistrationDialogFragment extends DialogFragment {

    public static final String TAG = SellerRegistrationDialogFragment.class.getSimpleName();
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_MESSAGE = "message";

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.et_authentication_code)
    EditText mEtAuthenticationCode;

    Unbinder mUnbinder;

    private String mTitle;
    private String mMessage;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static SellerRegistrationDialogFragment newInstance(String title, String message) {
        SellerRegistrationDialogFragment fragment = new SellerRegistrationDialogFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(PARAM_TITLE);
            mMessage = getArguments().getString(PARAM_MESSAGE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (callback == null) {
            try {
                callback = (Callback) getTargetFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement DialogListener");
            }
        }
    }

    @Override
    public void onDetach() {
        if (callback != null) {
            callback = null;
        }
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_seller_registration, container);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle.setText(mTitle);
        mEtAuthenticationCode.setHint(mMessage);
        mEtAuthenticationCode.setOnKeyListener((view1, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        onClickOk(view1);
                        return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        window.setLayout((int) (size.x * 0.95), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        KeyboardUtils.showKeyboard(getActivity(), getView());
    }

    @OnClick(R.id.btn_ok)
    void onClickOk(View view) {
        KeyboardUtils.hideKeyboard(getActivity(), getView());

        // 길이 체크. ex) 589d4c2f-qa&Hu
        String authenticationCode = mEtAuthenticationCode.getText().toString();
        if (authenticationCode.length() < 14) {
            Toast.makeText(getContext(), "인증코드가 너무 짧아요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(authenticationCode.length() > 14){
            Toast.makeText(getContext(), "인증코드가 너무 길어요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (callback != null) {
            callback.onClickOk(authenticationCode);
        }
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel(View view) {
        KeyboardUtils.hideKeyboard(getActivity(), getView());
        dismiss();
    }

    public interface Callback {
        void onClickOk(String authenticationCode);

        void onClickCancel();
    }
}
