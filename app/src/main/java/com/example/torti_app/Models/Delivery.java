package com.example.torti_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Delivery implements Parcelable {
    private Customer customer = null;
    private String date = null;
    private Delivery(Parcel in) {
        this.date = in.readString();
        this.customer = in.readParcelable(Customer.class.getClassLoader());
    }

    public Delivery (String date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel in) {
            return new Delivery(in);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
