package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app.Adapters.HistorySaleAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.Models.History;
import com.example.torti_app.Models.HistorySale;
import com.example.torti_app.Models.User;
import com.example.torti_app.R;
import com.example.torti_app.singletons.VolleyS;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryDetailActivity extends AppCompatActivity {

    private FloatingActionButton btnOk;
    private History history = null;
    private RecyclerView recyclerView;
    private TextView txtTotalPagado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        this.boot();
    }

    private void boot() {
        this.getHistorySale();
        this.inicializedElements();
        this.getHistorySaleLoad();
    }

    private void inicializedElements() {
        recyclerView = findViewById(R.id.rv_historyDetail);
        txtTotalPagado = findViewById(R.id.txt_totalPagado);
    }

    private void getHistorySale() {
        Bundle obj = getIntent().getExtras();
        if(obj != null)
        {
            history = obj.getParcelable("histories");
            Log.d("HISTORIES", history.getCustomer());
        }
    }

    private void getHistorySaleLoad() {
        Log.d("HISTORIESDETAILS", String.valueOf(history.getId()));
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, Data.api_url + "sale-details/" + history.getId(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("HISTORIESDETAILS", response.toString());
                try {
                    if(response.getString("sale_details").length() > 0) {
                        Gson g = new Gson();
                        Type t = new TypeToken<List<HistorySale>>(){}.getType();

                        List<HistorySale> historySaleList =
                                g.fromJson(response.getString("sale_details").toString(), t);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new HistorySaleAdapter(historySaleList));
                    }

                    txtTotalPagado.setText(String.format("Total pagado: %s",
                            response.getJSONArray("total").getJSONObject(0).getDouble("total")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HISTORIESDETAILS-ERROR", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map <String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + User.getToken(getApplicationContext()));
                return headers;
            }
        };

        VolleyS.getInstance(this).getQueue().add(json);
    }


}
