package com.example.torti_app.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.torti_app.Adapters.DeliveryAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.singletons.VolleyS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Delivery implements Parcelable {
    private Customer customer = null;
    private int pendingPayment;
    private int paymentId;
    private Delivery(Parcel in) {
        this.customer = in.readParcelable(Customer.class.getClassLoader());
        this.pendingPayment = in.readInt();
    }

    public Delivery (Customer customer, int pendingPayment, int paymentId) {
        this.customer = customer;
        this.pendingPayment = pendingPayment;
        this.paymentId = paymentId;
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
        dest.writeParcelable(this.customer, flags);
        dest.writeInt(this.pendingPayment);
        dest.writeInt(this.paymentId);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getPendingPayment() {
        return pendingPayment;
    }

    public void setPendingPayment(int pendingPayment) {
        this.pendingPayment = pendingPayment;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPaymentId() {
        return paymentId;
    }
}
