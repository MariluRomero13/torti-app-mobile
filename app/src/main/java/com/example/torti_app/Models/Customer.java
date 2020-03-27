package com.example.torti_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {
    private String name = null;
    private String maternalName = null;
    private String paternalName = null;

    private Customer(Parcel in) {
        this.name = in.readString();
        this.maternalName = in.readString();
        this.paternalName = in.readString();
    }

    public Customer(String name, String maternalName, String paternalName) {
        this.name = name;
        this.maternalName = maternalName;
        this.paternalName = paternalName;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.maternalName);
        dest.writeString(this.paternalName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaternalName() {
        return maternalName;
    }

    public void setMaternalName(String maternalName) {
        this.maternalName = maternalName;
    }

    public String getPaternalName() {
        return paternalName;
    }

    public void setPaternalName(String paternalName) {
        this.paternalName = paternalName;
    }
}
