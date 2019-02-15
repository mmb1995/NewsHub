package com.example.android.newshub.model.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Byline implements Parcelable
{

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("person")
    @Expose
    private List<Person> person = null;
    @SerializedName("organization")
    @Expose
    private Object organization;
    public final static Parcelable.Creator<Byline> CREATOR = new Creator<Byline>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Byline createFromParcel(Parcel in) {
            return new Byline(in);
        }

        public Byline[] newArray(int size) {
            return (new Byline[size]);
        }

    }
            ;

    protected Byline(Parcel in) {
        this.original = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.person, (Person.class.getClassLoader()));
        this.organization = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Byline() {
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public Object getOrganization() {
        return organization;
    }

    public void setOrganization(Object organization) {
        this.organization = organization;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(original);
        dest.writeList(person);
        dest.writeValue(organization);
    }

    public int describeContents() {
        return 0;
    }

}
