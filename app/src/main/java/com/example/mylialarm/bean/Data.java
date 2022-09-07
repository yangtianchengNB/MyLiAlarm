package com.example.mylialarm.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private String name;
    private int hour;
    private int min;

    public Data(String name, int hour, int min) {
        this.name = name;
        this.hour = hour;
        this.min = min;
    }

    public Data() {
    }

    protected Data(Parcel in) {
        name = in.readString();
        hour = in.readInt();
        min = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", hour=" + hour +
                ", min=" + min +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(hour);
        dest.writeInt(min);
    }
}
