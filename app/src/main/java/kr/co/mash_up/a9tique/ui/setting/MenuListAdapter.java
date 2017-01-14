package kr.co.mash_up.a9tique.ui.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.data.Menu;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by CY on 2016. 11. 15..
 */
public class MenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_WITH_TEXT = 1;
    public static final int VIEW_TYPE_WITH_IMAGE = 2;

    private ArrayList<Menu> mMenus;
    private Context mContext;

    private OnItemClickListener<Menu> mOnItemClickListener;

    public void setData(ArrayList<Menu> menus) {
        mMenus.clear();
        for(Menu menu : menus){
            this.mMenus.add(menu);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Menu> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MenuListAdapter(@NonNull Context context) {
        this.mMenus = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mMenus.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_WITH_TEXT:
                return MenuWithTextViewHolder.newInstance(parent, mOnItemClickListener);
            case VIEW_TYPE_WITH_IMAGE:
                return MenuWithImageViewHolder.newInstance(parent, mOnItemClickListener);
            default:
                return MenuViewHolder.newInstance(parent, mOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Menu menu = mMenus.get(position);

        ((BaseViewHolder<Menu>) holder).bind(menu);
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }
}

