package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerGoodsDetailActivity extends AppCompatActivity {
    private List<CustomerGoodsDetail> customerGoodsDetailList = new ArrayList<>();
    private CustomerGoodsDetailAdapter customerGoodsDetailAdapter;
    int CHECK_NUM = 0;

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;
    @BindView(R.id.btn_zzim)
    ImageButton btnZzim;
    @BindView(R.id.goodsImage)
    ImageView ivGoodsImage;
    @BindView(R.id.recyclerview)
    RecyclerView rvCustomerGoodsDetail;

    @OnClick(R.id.btn_back)
    public void ibtnToolbarBackClick(View view) {
        finish();
    }
    @OnClick(R.id.btn_zzim)
    public void ibtnZzimClick(View view) {
        if(CHECK_NUM == 0) {
            btnZzim.setSelected(false);
            CHECK_NUM = 1;
        }
        else {
            btnZzim.setSelected(true);
            CHECK_NUM = 0;
        }
        btnZzim.setImageResource(R.drawable.selector_button_zzim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_goods_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivGoodsImage.setImageResource(R.drawable.goods);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCustomerGoodsDetail.setLayoutManager(linearLayoutManager);

        customerGoodsDetailAdapter = new CustomerGoodsDetailAdapter(customerGoodsDetailList);
        rvCustomerGoodsDetail.setAdapter(customerGoodsDetailAdapter);

        prepareGoodsDetailData();
    }

    private void prepareGoodsDetailData() {
        CustomerGoodsDetail customerGoodsDetail = new CustomerGoodsDetail();
        customerGoodsDetailList.add(customerGoodsDetail);

        customerGoodsDetailAdapter.notifyDataSetChanged();
    }
}
