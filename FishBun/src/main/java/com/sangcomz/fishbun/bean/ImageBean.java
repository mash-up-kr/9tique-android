package com.sangcomz.fishbun.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2014-12-22.
 */
public class ImageBean implements Parcelable {
    int imgOrder;
    String imgPath;
    long dateTaken;
    boolean isInit = false;

    public ImageBean(int imgOrder, String imgPath) {
        this.imgOrder = imgOrder;
        this.imgPath = imgPath;
    }

    public ImageBean(int imgOrder, String imgPath, long dateTaken) {
        this.imgOrder = imgOrder;
        this.imgPath = imgPath;
        this.dateTaken = dateTaken;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(int imgOrder) {
        this.imgOrder = imgOrder;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setIsInit(boolean isInit) {
        this.isInit = isInit;
    }


    protected ImageBean(Parcel in) {
        imgOrder = in.readInt();
        imgPath = in.readString();
        isInit = (boolean) in.readValue(Boolean.class.getClassLoader());
        dateTaken = in.readLong();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imgOrder);
        parcel.writeString(imgPath);
        parcel.writeValue(isInit);
        parcel.writeLong(dateTaken);
    }


    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

//
//    public void readFromParcel(Parcel parcel) {
//        imgOrder = parcel.readInt();
//        imgPath = parcel.readString();
//        isInit = parcel.readValue();
//    }
}
