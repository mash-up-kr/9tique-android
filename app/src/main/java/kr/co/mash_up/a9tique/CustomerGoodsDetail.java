package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class CustomerGoodsDetail extends AppCompatActivity {
    private List<GoodsDetail> goodsDetailList = new ArrayList<>();
    private RecyclerView rvGoodsDetail;
    private LinearLayoutManager mLinearLayoutManager;
    private GoodsDetailAdapter goodsDetailAdapter;
    int CHECK_NUM = 0; // 찜하기 버튼 selected 유무 표시

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_goods_detail);

        // 고객 상품 상세 정보 툴바 (뒤로 가기, 찜하기 버튼)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 뒤로 가기 버튼
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 찜하기 버튼
        ImageButton btn_heart = (ImageButton) findViewById(R.id.btn_heart);
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CHECK_NUM == 0) {
                    btn_heart.setSelected(true);
                    CHECK_NUM = 1;
                }
                else {
                    btn_heart.setSelected(false);
                    CHECK_NUM = 0;
                }
            }
        });
        btn_heart.setImageResource(R.drawable.selector_button_zzim);

        // recycler view
        rvGoodsDetail = (RecyclerView) findViewById(R.id.list);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvGoodsDetail.setLayoutManager(mLinearLayoutManager);

        goodsDetailAdapter = new GoodsDetailAdapter(goodsDetailList);
        rvGoodsDetail.setAdapter(goodsDetailAdapter);

        prepareGoodsDetailData();
    }

    private void prepareGoodsDetailData() {
        GoodsDetail goodsDetail = new GoodsDetail(R.drawable.goods);
        goodsDetailList.add(goodsDetail);

        goodsDetailAdapter.notifyDataSetChanged();
    }
}
