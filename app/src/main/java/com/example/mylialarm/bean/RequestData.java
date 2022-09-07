package com.example.mylialarm.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RequestData implements Parcelable {
    private String code;
    private String message;
    private List<Data> data;

    public RequestData() {
    }

    public RequestData(String code, String message, List<Data> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected RequestData(Parcel in) {
        code = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<RequestData> CREATOR = new Creator<RequestData>() {
        @Override
        public RequestData createFromParcel(Parcel in) {
            return new RequestData(in);
        }

        @Override
        public RequestData[] newArray(int size) {
            return new RequestData[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(message);
        dest.writeTypedList(data);
    }
}
