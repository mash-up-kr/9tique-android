package kr.co.mash_up.a9tique.ui.addeditproduct;

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

public class PictureSelectionDialogFragment extends DialogFragment {

    public static final String TAG = PictureSelectionDialogFragment.class.getSimpleName();
    public static final String PARAM_TITLE = "title";

    private String mTitle;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    Unbinder mUnbinder;

    public interface Callback {
        void onClickGalleryStart();

        void onClickCameraStart();
    }

    private Callback callback;

    public static PictureSelectionDialogFragment newInstance(String title) {
        PictureSelectionDialogFragment fragment = new PictureSelectionDialogFragment();
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

        try {
            callback = (Callback) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
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
        View view = inflater.inflate(R.layout.dialog_fragment_picture, container);
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

    @OnClick(R.id.ll_take_picture)
    void onClickTakePircute() {
        if (callback != null) {
            callback.onClickCameraStart();
        }
        dismiss();
    }

    @OnClick(R.id.ll_pick_picture)
    void onClickPickPicture() {
        if (callback != null) {
            callback.onClickGalleryStart();
        }
        dismiss();
    }

    @OnClick(R.id.iv_close)
    void onClickClose() {
        dismiss();
    }
}
