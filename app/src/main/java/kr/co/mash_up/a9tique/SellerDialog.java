package kr.co.mash_up.a9tique;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CY on 2017. 1. 10..
 */

public class SellerDialog extends Dialog {

    public SellerDialog(Context context) {
        super(context);
    }

    @Nullable @BindView(R.id.dlg_title)
    TextView tvDlgSellerTitle;
    @Nullable @BindView(R.id.dlg_edittxt)
    EditText etDlgSeller;
    @Nullable @BindView(R.id.dlg_cancel)
    Button btnDlgSellerCancel;
    @Nullable @BindView(R.id.dlg_agree)
    Button btnDlgSellerAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seller_dialog);
        ButterKnife.bind(this);

        btnDlgSellerAgree.setText("AGREE");
        btnDlgSellerCancel.setText("CANCEL");

        tvDlgSellerTitle.setText("인증코드 입력");
        etDlgSeller.setHint("발급된 인증코드를 입력해 주세요.");

        btnDlgSellerCancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });
        btnDlgSellerAgree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getContext(), SellerActivity.class);
                getContext().startActivity(intent);
                dismiss();
                return false;
            }
        });
    }
}
