package kr.co.mash_up.a9tique.ui;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;

/**
 * Created by seokjunjeong on 2017. 7. 11..
 */

public class ImageViewAdapter extends PagerAdapter {
    private ArrayList<String> mImages;

    public ImageViewAdapter() {
        mImages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_view_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_event_content);
        Glide.with(container.getContext()).load(mImages.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }
}
