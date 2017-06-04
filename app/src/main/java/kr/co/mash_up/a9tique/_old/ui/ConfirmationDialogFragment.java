package kr.co.mash_up.a9tique._old.ui;

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

public class ConfirmationDialogFragment extends DialogFragment {

    public static final String TAG = ConfirmationDialogFragment.class.getSimpleName();
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_MESSAGE = "message";

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.tv_message)
    TextView mTvMessage;

    Unbinder mUnbinder;

    private String mTitle;
    private String mMessage;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static ConfirmationDialogFragment newInstance(String title, String message) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
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
        setCancelable(false);
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
        View view = inflater.inflate(R.layout.dialog_fragment_confirmation, container);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle.setText(mTitle);
        mTvMessage.setText(mMessage);
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

    @OnClick(R.id.btn_ok)
    void onClickOk(View view) {
        if (callback != null) {
            callback.onClickOk();
        }
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel(View view) {
        dismiss();
    }

    public interface Callback {
        void onClickOk();

        void onClickCancel();
    }
}
