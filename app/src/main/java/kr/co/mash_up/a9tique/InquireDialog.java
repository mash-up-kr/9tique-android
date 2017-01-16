package kr.co.mash_up.a9tique;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InquireDialog extends Dialog {

    public InquireDialog(Context context) {
        super(context);
    }

    @Nullable @BindView(R.id.tv_inquire_title)
    TextView tvInquireTitle;
    @Nullable @BindView(R.id.tv_inquire_phone)
    TextView tvInquirePhone;
    @Nullable @BindView(R.id.tv_inquire_message)
    TextView tvInquireMessage;
    @OnClick(R.id.tv_inquire_phone)
    public void inquirePhoneClick(View view) {
        Toast.makeText(getContext(), "전화 문의", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.tv_inquire_message)
    public void inquireMessageClick(View view){
        Toast.makeText(getContext(), "메시지 문의", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btn_x)
    public void btnXClick(View view) {
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_inquire);
        ButterKnife.bind(this);

        tvInquireTitle.setText("문의하기");
        tvInquirePhone.setText("010-0000-0000");
        tvInquireMessage.setText("문자메시지");
    }
}
