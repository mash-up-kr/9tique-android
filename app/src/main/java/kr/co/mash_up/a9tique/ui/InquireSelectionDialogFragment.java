package kr.co.mash_up.a9tique.ui;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.mash_up.a9tique.R;

public class InquireSelectionDialogFragment extends DialogFragment {

    public static final String TAG = InquireSelectionDialogFragment.class.getSimpleName();
    public static final String PARAM_TITLE = "title";

    private String mTitle;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    Unbinder mUnbinder;

    public interface Callback {
        void onClickCallPhone();

        void onClickSendMessage();

        void onClickKakaoOpenChat();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static InquireSelectionDialogFragment newInstance(String title) {
        InquireSelectionDialogFragment fragment = new InquireSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(PARAM_TITLE);
        }
        setCancelable(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(callback == null) {
            try {
                callback = (Callback) getTargetFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + "must implement DialogListener");
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
        View view = inflater.inflate(R.layout.dialog_fragment_inquire, container);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle.setText(mTitle);
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
    }

    @OnClick(R.id.ll_call_phone)
    void onClickCallPhone() {
        if (callback != null) {
            callback.onClickCallPhone();
        }
        dismiss();
    }

    @OnClick(R.id.ll_send_message)
    void onClickSendMessage() {
        if (callback != null) {
            callback.onClickSendMessage();
        }
        dismiss();
    }

    @OnClick(R.id.ll_kakao_open_chat)
    void onClickKakaoOpenChat() {
        if (callback != null) {
            callback.onClickKakaoOpenChat();
        }
        dismiss();
    }

    @OnClick(R.id.iv_close)
    void onClickClose() {
        dismiss();
    }
}
