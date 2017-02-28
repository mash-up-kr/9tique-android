package kr.co.mash_up.a9tique.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.co.mash_up.a9tique.R;

/**
 * 로그인 버튼에 동일한 디자인을 입히기 위한 커스텀뷰
 */
public class LoginButton extends RelativeLayout {

    private RelativeLayout mRlRoot;
    private TextView mTvTitle;
    private ImageView mIvIcon;

    public LoginButton(Context context) {
        super(context);
        init();
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(attrs, defStyleAttr);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.btn_login, this, true);
        mRlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoginButton);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoginButton, defStyleAttr, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typeArray) {
        int bgResId = typeArray.getResourceId(R.styleable.LoginButton_lb_background, R.drawable.shape_btn_login_normal);
        mRlRoot.setBackgroundResource(bgResId);

        int fgResId = typeArray.getResourceId(R.styleable.LoginButton_lb_foreground, android.support.v7.appcompat.R.attr.selectableItemBackground);
        mRlRoot.setForeground(ContextCompat.getDrawable(getContext(), fgResId));

        int iconResId = typeArray.getResourceId(R.styleable.LoginButton_lb_icon, R.drawable.ic_logo);
        mIvIcon.setImageResource(iconResId);

        String strText = typeArray.getString(R.styleable.LoginButton_lb_text);
        mTvTitle.setText(strText);

        int textColor = typeArray.getColor(R.styleable.LoginButton_lb_textColor, 0);
        mTvTitle.setTextColor(textColor);

        typeArray.recycle();
    }

    public void setBackground(@DrawableRes int bgResId) {
        mRlRoot.setBackgroundResource(bgResId);
    }

    public void setIcon(@DrawableRes int iconResId) {
        mIvIcon.setImageResource(iconResId);
    }

    public void setText(String text) {
        mTvTitle.setText(text);
    }

    public void setTextColor(@ColorRes int color) {
        mTvTitle.setTextColor(ContextCompat.getColor(getContext(), color));
    }
}
