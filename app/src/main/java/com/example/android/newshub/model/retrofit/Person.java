package com.example.android.newshub.model.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person implements Parcelable
{

    @SerializedName("firstname")
    @Expose
    private Object firstname;
    @SerializedName("middlename")
    @Expose
    private Object middlename;
    @SerializedName("lastname")
    @Expose
    private Object lastname;
    @SerializedName("qualifier")
    @Expose
    private Object qualifier;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    public final static Parcelable.Creator<Person> CREATOR = new Creator<Person>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return (new Person[size]);
        }

    }
            ;

    protected Person(Parcel in) {
        this.firstname = ((Object) in.readValue((Object.class.getClassLoader())));
        this.middlename = ((Object) in.readValue((Object.class.getClassLoader())));
        this.lastname = ((Object) in.readValue((Object.class.getClassLoader())));
        this.qualifier = ((Object) in.readValue((Object.class.getClassLoader())));
        this.title = ((Object) in.readValue((Object.class.getClassLoader())));
        this.role = ((String) in.readValue((String.class.getClassLoader())));
        this.organization = ((String) in.readValue((String.class.getClassLoader())));
        this.rank = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Person() {
    }

    public Object getFirstname() {
        return firstname;
    }

    public void setFirstname(Object firstname) {
        this.firstname = firstname;
    }

    public Object getMiddlename() {
        return middlename;
    }

    public void setMiddlename(Object middlename) {
        this.middlename = middlename;
    }

    public Object getLastname() {
        return lastname;
    }

    public void setLastname(Object lastname) {
        this.lastname = lastname;
    }

    public Object getQualifier() {
        return qualifier;
    }

    public void setQualifier(Object qualifier) {
        this.qualifier = qualifier;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstname);
        dest.writeValue(middlename);
        dest.writeValue(lastname);
        dest.writeValue(qualifier);
        dest.writeValue(title);
        dest.writeValue(role);
        dest.writeValue(organization);
        dest.writeValue(rank);
    }

    public int describeContents() {
        return 0;
    }

}
