package kr.co.mash_up.a9tique.ui.products;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;

//Todo: 이벤트 내용 바인딩
public class SellerProductListEventFragment extends BaseFragment {

    @BindView(R.id.iv_event_content)
    ImageView ivEvent;

    @BindView(R.id.tv_event_content)
    TextView tvEvent;

    public SellerProductListEventFragment() {
        // Required empty public constructor
    }

    public static SellerProductListEventFragment newInstance() {
        SellerProductListEventFragment fragment = new SellerProductListEventFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM_TITLE, paramTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParamTitle = getArguments().getString(ARG_PARAM_TITLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_product_list_event;
    }

    @Override
    public void initView(View rootView) {
        ivEvent.setImageResource(R.mipmap.ic_launcher);
    }
}
