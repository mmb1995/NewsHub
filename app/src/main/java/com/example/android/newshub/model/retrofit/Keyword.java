package com.example.android.newshub.model.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keyword implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("major")
    @Expose
    private Object major;
    public final static Parcelable.Creator<Keyword> CREATOR = new Creator<Keyword>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Keyword createFromParcel(Parcel in) {
            return new Keyword(in);
        }

        public Keyword[] newArray(int size) {
            return (new Keyword[size]);
        }

    }
            ;

    protected Keyword(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.rank = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.major = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Keyword() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Object getMajor() {
        return major;
    }

    public void setMajor(Object major) {
        this.major = major;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(value);
        dest.writeValue(rank);
        dest.writeValue(major);
    }

    public int describeContents() {
        return 0;
    }

}