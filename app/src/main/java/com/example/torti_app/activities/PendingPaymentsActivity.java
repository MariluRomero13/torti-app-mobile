package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app.Adapters.PendingPaymentsAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.Models.PendingPayments;
import com.example.torti_app.Models.User;
import com.example.torti_app.R;
import com.example.torti_app.singletons.VolleyS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingPaymentsActivity extends AppCompatActivity {

    private FloatingActionButton btnPagar;
    private RecyclerView recyclerView;
    private TextView txtTotal, txtDeposito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payments);
        this.boot();
    }

    @Override
    protected void onStart() {
        pendingPaymentLoad();
        super.onStart();
    }

    private void boot() {
        this.initializeElements();
        this.addListeners();
        this.pendingPaymentLoad();
    }

    private void initializeElements() {
        btnPagar = findViewById(R.id.btn_pagar);
        recyclerView = findViewById(R.id.rv_pendingpayments);
        txtTotal = findViewById(R.id.txt_total);
    }

    private void addListeners() {
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        PendingPaymentsActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_pending_payments,
                                (ViewGroup) findViewById(R.id.bottom_sheet_pp)
                        );
                txtDeposito = bottomSheetView.findViewById(R.id.txt_pago);

                bottomSheetView.findViewById(R.id.btn_depositar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDeposit();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void pendingPaymentLoad() {
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, Data.api_url + "get-pending-payment/" + Delivery.payment_id,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("pendingPaymentDetails").length() > 0) {
                        Gson g = new Gson();
                        Type t = new TypeToken<List<PendingPayments>>(){}.getType();

                        List<PendingPayments> pendingPaymentsList =
                                g.fromJson(response.getString("pendingPaymentDetails").toString(), t);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new PendingPaymentsAdapter(pendingPaymentsList));
                    }

                    txtTotal.setText(String.format("Total a pagar: %s",
                            response.getJSONArray("paymentData").getJSONObject(0).getDouble("to_pay")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PAYMENTSPENDING-ERROR", error.toString());
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

    private void setDeposit() {
        try {
            JSONObject params = new JSONObject();
            params.put("payment_id", Delivery.payment_id);
            params.put("newDeposit", txtDeposito.getText().toString());

            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, Data.api_url + "set-deposit", params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("DEPOSIT", response.toString());
                            try {
                                if (response.getInt("status") ==  0) {
                                    Toast.makeText(PendingPaymentsActivity.this, "Tu deposito se ha hecho con exito", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (response.getInt("status") ==  2) {
                                    Toast.makeText(PendingPaymentsActivity.this, "Haz ingresado una cantidad mayor a la que debe el cliente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PendingPaymentsActivity.this, "Tu deposito se ha hecho con exito, tu cuenta ha sido liquidada", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DEPOSIT-ERROR", error.toString());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
