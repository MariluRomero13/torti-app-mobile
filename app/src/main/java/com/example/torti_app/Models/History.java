package com.example.torti_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private String date = null;
    private Customer customer = null;

    private History(Parcel in) {
        this.date = in.readString();
        this.customer = in.readParcelable(Customer.class.getClassLoader());
    }

    public History(String date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeParcelable(this.customer, flags);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
