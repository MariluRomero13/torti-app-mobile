package com.example.torti_app.Fragments;

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
import com.example.torti_app.Adapters.HistoryAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.History;
import com.example.torti_app.Models.PendingPayments;
import com.example.torti_app.Models.User;
import com.example.torti_app.R;
import com.example.torti_app.activities.HistoryDetailActivity;
import com.example.torti_app.singletons.VolleyS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, Data.api_url + "get-sales-history", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("HISTORY", response.toString());
                        if(response.length() > 0){
                            Gson g = new Gson();
                            Type t = new TypeToken<List<History>>(){}.getType();

                            List<History> historyList =
                                    g.fromJson(response.toString(), t);

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new HistoryAdapter(historyList));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HISTORY-ERROR", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map <String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + User.getToken(getContext()));
                return headers;
            }
        };

        VolleyS.getInstance(getContext()).getQueue().add(json);
        return rootView;
    }
}
