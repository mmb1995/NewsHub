package com.example.android.newshub.model.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline implements Parcelable
{

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private String kicker;
    @SerializedName("content_kicker")
    @Expose
    private Object contentKicker;
    @SerializedName("print_headline")
    @Expose
    private Object printHeadline;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("seo")
    @Expose
    private Object seo;
    @SerializedName("sub")
    @Expose
    private Object sub;
    public final static Parcelable.Creator<Headline> CREATOR = new Creator<Headline>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Headline createFromParcel(Parcel in) {
            return new Headline(in);
        }

        public Headline[] newArray(int size) {
            return (new Headline[size]);
        }

    }
            ;

    protected Headline(Parcel in) {
        this.main = ((String) in.readValue((String.class.getClassLoader())));
        this.kicker = ((String) in.readValue((String.class.getClassLoader())));
        this.contentKicker = ((Object) in.readValue((Object.class.getClassLoader())));
        this.printHeadline = ((Object) in.readValue((Object.class.getClassLoader())));
        this.name = ((Object) in.readValue((Object.class.getClassLoader())));
        this.seo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.sub = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Headline() {
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public Object getContentKicker() {
        return contentKicker;
    }

    public void setContentKicker(Object contentKicker) {
        this.contentKicker = contentKicker;
    }

    public Object getPrintHeadline() {
        return printHeadline;
    }

    public void setPrintHeadline(Object printHeadline) {
        this.printHeadline = printHeadline;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getSeo() {
        return seo;
    }

    public void setSeo(Object seo) {
        this.seo = seo;
    }

    public Object getSub() {
        return sub;
    }

    public void setSub(Object sub) {
        this.sub = sub;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(main);
        dest.writeValue(kicker);
        dest.writeValue(contentKicker);
        dest.writeValue(printHeadline);
        dest.writeValue(name);
        dest.writeValue(seo);
        dest.writeValue(sub);
    }

    public int describeContents() {
        return 0;
    }

}
