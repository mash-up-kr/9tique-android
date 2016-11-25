package com.sangcomz.fishbun.adapter;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sangcomz.fishbun.R;
import com.sangcomz.fishbun.bean.GalleryHeaderItem;
import com.sangcomz.fishbun.bean.ImageBean;
import com.sangcomz.fishbun.bean.PickedImageBean;
import com.sangcomz.fishbun.define.Define;
import com.sangcomz.fishbun.ui.picker.PickerController;
import com.sangcomz.fishbun.util.SquareTextView;
import com.sangcomz.fishbun.util.TimeUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class PickerGridAdapter
        extends RecyclerView.Adapter<PickerGridAdapter.ViewHolder> {
    private static final int TYPE_HEADER = Integer.MIN_VALUE;

    private ArrayList<PickedImageBean> pickedImageBeans = new ArrayList<>();
    private ImageBean[] imageBeans;
    private PickerController pickerController;

    private ArrayList<Object> mDatas;

    public class ViewHolderImage extends ViewHolder {

        ImageView imgPhoto;
        SquareTextView txtPickCount;

        public ViewHolderImage(View view) {
            super(view);
            imgPhoto = (ImageView) view.findViewById(R.id.img_thum);
            txtPickCount = (SquareTextView) view.findViewById(R.id.txt_pick_count);
        }
    }

    public class ViewHolderHeader extends ViewHolder {

        TextView tvDate;

        public ViewHolderHeader(View view) {
            super(view);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public PickerGridAdapter(ImageBean[] imageBeans,
                             ArrayList<PickedImageBean> pickedImageBeans, PickerController pickerController) {
//        this.imageBeans = imageBeans;
        this.pickerController = pickerController;
        this.pickedImageBeans = pickedImageBeans;


        mDatas = insertHeader(Arrays.asList(imageBeans));
    }

    private ArrayList<Object> insertHeader(List<ImageBean> imageBeens) {
        ArrayList<Object> objects = new ArrayList<>();

        String lastHeader = "";
        for (int i = 0; i < imageBeens.size(); i++) {
            ImageBean current = imageBeens.get(i);

            String header = String.format("%s.%s", TimeUtils.timestampToYear(current.getDateTaken()),
                    TimeUtils.timestampToMonth(current.getDateTaken()));

            if (!TextUtils.equals(lastHeader, header)) {
                objects.add(new GalleryHeaderItem(header));
                lastHeader = header;
            }
            objects.add(current);
        }

        return objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            return new ViewHolderHeader(view);
        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thum_item, parent, false);
        return new ViewHolderImage(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderHeader) {
            final ViewHolderHeader vh = (ViewHolderHeader) holder;
            GalleryHeaderItem headerItem = (GalleryHeaderItem) mDatas.get(position);
            vh.tvDate.setText(headerItem.getDate());

//            vh.header.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pickerController.takePicture(saveDir);
//                }
//            });
        }

        if (holder instanceof ViewHolderImage) {
            final ViewHolderImage vh = (ViewHolderImage) holder;

            final ImageBean imageBean = (ImageBean) mDatas.get(position);
            final String imgPath = imageBean.getImgPath();

            if (!imageBean.isInit()) {
                imageBean.setIsInit(true);
                for (int i = 0; i < pickedImageBeans.size(); i++) {
                    if (imgPath.equals(pickedImageBeans.get(i).getImgPath())) {
                        imageBean.setImgOrder(i + 1);
                        pickedImageBeans.get(i).setImgPosition(position);
                        break;
                    }
                }
            }


            if (imageBean.getImgOrder() != -1) {
                vh.txtPickCount.setVisibility(View.VISIBLE);
                if (Define.ALBUM_PICKER_COUNT == 1)
                    vh.txtPickCount.setText("");
                else
                    vh.txtPickCount.setText(String.valueOf(imageBean.getImgOrder()));
            } else
                vh.txtPickCount.setVisibility(View.GONE);


            if (imgPath != null && !imgPath.equals("")) {
                Picasso
                        .with(vh.imgPhoto.getContext())
                        .load(new File(imgPath))
                        .fit()
                        .centerCrop()
                        .into(vh.imgPhoto);
            }


            vh.imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vh.txtPickCount.getVisibility() == View.GONE &&
                            Define.ALBUM_PICKER_COUNT > pickedImageBeans.size()) {
                        vh.txtPickCount.setVisibility(View.VISIBLE);
                        pickedImageBeans.add(new PickedImageBean(pickedImageBeans.size() + 1, imgPath, position));

                        pickerController.setToolbarTitle(pickedImageBeans.size());
                        if (Define.IS_AUTOMATIC_CLOSE
                                && Define.ALBUM_PICKER_COUNT == pickedImageBeans.size()) {
                            pickerController.finishActivity(pickedImageBeans);
                        }

                        if (Define.ALBUM_PICKER_COUNT == 1)
                            vh.txtPickCount.setText("");
                        else
                            vh.txtPickCount.setText(String.valueOf(pickedImageBeans.size()));

                        imageBean.setImgOrder(pickedImageBeans.size());

                    } else if (vh.txtPickCount.getVisibility() == View.VISIBLE) {
                        pickerController.setRecyclerViewClickable(false);
                        pickedImageBeans.remove(imageBean.getImgOrder() - 1);
                        if (Define.ALBUM_PICKER_COUNT != 1)
                            setOrder(Integer.valueOf(vh.txtPickCount.getText().toString()) - 1);
                        else
                            setOrder(0);
                        imageBean.setImgOrder(-1);
                        vh.txtPickCount.setVisibility(View.GONE);
                        pickerController.setToolbarTitle(pickedImageBeans.size());
                    } else {
                        Snackbar.make(v, Define.MESSAGE_LIMIT_REACHED, Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position) instanceof GalleryHeaderItem) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }


    private void setOrder(int removePosition) {
        for (int i = removePosition; i < pickedImageBeans.size(); i++) {
            if (pickedImageBeans.get(i).getImgPosition() != -1) {
                imageBeans[pickedImageBeans.get(i).getImgPosition()]
                        .setImgOrder(i + 1);

                notifyItemChanged(pickedImageBeans.get(i).getImgPosition()); //if don't use header
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pickerController.setRecyclerViewClickable(true);
            }
        }, 500);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        public ViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }
    }


    public ImageBean[] getImageBeans() {
        return imageBeans;
    }


    public void addImage(String path) {
        ArrayList<ImageBean> al = new ArrayList<>();
        Collections.addAll(al, imageBeans);
        al.add(0, new ImageBean(-1, path));

        imageBeans = al.toArray(new ImageBean[al.size()]);

        for (int i = 0; i < pickedImageBeans.size(); i++)
            pickedImageBeans.get(i).setImgPosition(pickedImageBeans.get(i).getImgPosition() + 1);

        notifyDataSetChanged();

        pickerController.setAddImagePath(path);
    }

    public Object getData(int position) {
        return mDatas.get(position);
    }
}