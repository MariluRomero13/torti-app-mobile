package com.example.torti_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private int id;
    private String customer;
    private Double total;

    public History(int id, String customer, Double total) {
        this.id = id;
        this.customer = customer;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel d, int i) {
        d.writeInt(id);
        d.writeString(customer);
        d.writeDouble(total);
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        public History createFromParcel(Parcel parcel) {
            return new History(parcel);
        }

        public History[] newArray(int size) {
            return new History[size];
        }
    };

    private History(Parcel d) {
        id = d.readInt();
        customer = d.readString();
        total = d.readDouble();
    }
}
