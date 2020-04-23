package com.example.torti_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.torti_app.Adapters.DeliveryAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.Models.User;
import com.example.torti_app.R;
import com.example.torti_app.activities.MapSaleActivity;
import com.example.torti_app.singletons.VolleyS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment
        implements DeliveryAdapter.OnDeliveryClickListener {
    private RecyclerView recyclerView = null;

    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_delivery, container, false);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.getAllDeliveries();
        return rootView;
    }

    @Override
    public void onStart() {
        this.getAllDeliveriesWithoutSale();
        super.onStart();
    }

    private void getAllDeliveriesWithoutSale () {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Data.api_url + "get-routes-without-sale",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Hola 1", response.toString());
                List<Delivery> deliveries = new ArrayList<>();
                for (int i = 0; i < response.length() ; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        Delivery.payment_id = data.getInt("payment_id");
                        Customer customer = new Customer(data.getInt("id"), data.getString("customer"));
                        deliveries.add(new Delivery(
                                customer, data.getInt("pending_payment"), data.getInt("payment_id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new DeliveryAdapter(deliveries, DeliveryFragment.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map <String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + User.getToken(getContext()));
                return headers;
            }
        };

        VolleyS.getInstance(getContext()).getQueue().add(request);
    }

    private void getAllDeliveries () {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Data.api_url + "routes-by-employee",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Hola 2", response.toString());
                List<Delivery> deliveries = new ArrayList<>();
                for (int i = 0; i < response.length() ; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        Delivery.payment_id = data.getInt("payment_id");
                        Customer customer = new Customer(data.getInt("id"), data.getString("customer"));
                        deliveries.add(new Delivery(
                                customer, data.getInt("pending_payment"), data.getInt("payment_id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new DeliveryAdapter(deliveries, DeliveryFragment.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map <String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + User.getToken(getContext()));
                return headers;
            }
        };

        VolleyS.getInstance(getContext()).getQueue().add(request);
    }

    @Override
    public void onDeliveryClick(Delivery delivery) {
        Intent intent = new Intent(getContext(), MapSaleActivity.class);
        intent.putExtra("delivery", delivery);
        startActivity(intent);
    }
}
