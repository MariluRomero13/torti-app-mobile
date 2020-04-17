package com.example.torti_app.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.torti_app.Adapters.ProductAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.singletons.VolleyS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Parcelable {
    private int id;
    private String name;
    private double unit_price;
    private int quantity;

    private Product(Parcel in) {
        this.id= in.readInt();
        this.name = in.readString();
        this.unit_price = in.readDouble();
        this.quantity = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.unit_price);
        dest.writeInt(this.quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(double unit_price) {
        this.unit_price = unit_price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public static void getAll (final Context context, final RecyclerView recyclerView) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Data.api_url + "get-products", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type type = new TypeToken<List<Product>>(){}.getType();
                        List<Product> products = new Gson().fromJson(response.toString(), type);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new ProductAdapter(products));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + User.getToken(context));
                return headers;
            }
        };

        VolleyS.getInstance(context).getQueue().add(request);
    }
}
