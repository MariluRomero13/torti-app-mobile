package com.example.torti_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app.Adapters.ProductAdapter;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.Models.Product;
import com.example.torti_app.Models.User;
import com.example.torti_app.R;
import com.example.torti_app.Util.BottomSheetDialogSale;
import com.example.torti_app.activities.PendingPaymentsActivity;
import com.example.torti_app.singletons.VolleyS;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
public class SaleFragment extends Fragment
        implements View.OnClickListener{

    private RecyclerView recyclerView = null;
    private TextView txvPendingPayment = null;
    private Button btnRegisterSale = null, btnCreditSale = null;
    private Delivery delivery = null;
    private RecyclerView.Adapter adapter = null;
    private boolean isCashPayment = true;
    private JSONArray details = null;
    private float total = 0;
    private BottomSheetDialogSale dialogSale;

    public SaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.delivery = getArguments().getParcelable("delivery");
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sale, container, false);

        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.txvPendingPayment = rootView.findViewById(R.id.txvPendingPayment);
        this.btnRegisterSale = rootView.findViewById(R.id.btnRegisterSale);
        this.btnCreditSale = rootView.findViewById(R.id.btnCreditSale);
        this.btnRegisterSale.setOnClickListener(this);
        this.btnCreditSale.setOnClickListener(this);
        this.txvPendingPayment.setOnClickListener(this);

        if(this.delivery != null && this.delivery.getPendingPayment() == 1) {
            this.txvPendingPayment.setVisibility(View.VISIBLE);
        }

        Product.getAll(getContext(), this.recyclerView);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == this.txvPendingPayment) {
            startActivity(new Intent(getContext(), PendingPaymentsActivity.class));
            return;
        }

        this.adapter = this.recyclerView.getAdapter();
        if (this.adapter instanceof ProductAdapter) {
            if (((ProductAdapter)this.adapter).products.isEmpty()) {
                showToast("No hay productos");
                return;
            }

            this.details = new JSONArray();
            for (Product product : ((ProductAdapter)this.adapter).products) {
                if (product.getQuantity() == 0) continue;
                this.total += product.getQuantity() * product.getUnitPrice();
                JSONObject object = new JSONObject();
                try {
                    object.put("product_id", product.getId());
                    object.put("quantity", product.getQuantity());
                    details.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (this.details.length() == 0) {
                showToast("Escoja al menos un producto");
                return;
            }

            this.dialogSale = new BottomSheetDialogSale();

            if(v == this.btnRegisterSale) {
                this.isCashPayment = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isCashPayment", this.isCashPayment);
                this.dialogSale.setArguments(bundle);
            } else if (v == this.btnCreditSale) {
                this.isCashPayment = false;
            }

            if(getActivity() != null && !this.dialogSale.isAdded())
                this.dialogSale.show(getActivity().getSupportFragmentManager(), "Dialog");
        }
    }

    private void storeData (JSONArray details, String deposit) {
        if (this.delivery == null) return;
        String route = "save-sale";
        JSONObject sale = new JSONObject();
        try {
            if (deposit != null) {
                route = "save-pending-payment";
                sale.put("deposit", Float.valueOf(deposit));
            }
            sale.put("customer_id", this.delivery.getCustomer().getId());
            sale.put("details", details);
            Log.e("sale", sale.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Data.api_url + route,
                    sale, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("SALE::", response.toString());
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Data.load = true;
                            getActivity().finish();
                            showToast("Venta realizada correctamente");
                            dialogSale.dismiss();
                            ((ProductAdapter)adapter).clear();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SALE-ERROR::", error.toString());
                    //This indicates error on authentication
                    if (error instanceof AuthFailureError
                            || (error.networkResponse != null && error.networkResponse.statusCode == 401)){
                        showToast("Error auth");
                    }
                    else if(error.networkResponse != null && error.networkResponse.statusCode == 400){
                        showToast("Hubo un error");
                    }
                    else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        showToast("Verifique su conexión a internet");
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        showToast("Error en el servidor");
                    } else if (error instanceof NetworkError) {
                        showToast("Verifique su conexión a internet");
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                   Map<String, String> headers = new HashMap<>();
                   headers.put("Authorization", "bearer " + User.getToken(getContext()));
                    return headers;
                }
            };
            VolleyS.getInstance(getContext()).getQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showToast (String message) {
        try {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void action (View v, String deposit) {
        if(this.isCashPayment){
            storeData(this.details, null);
        } else {
            if(deposit == null || deposit.trim().length() == 0 || deposit.equals("0")) {
                showToast("Por favor ingrese un monto");
                return;
            } else if ( Float.parseFloat(deposit) > this.total) {
                showToast("El depósito sobrepasa el total de la venta");
                return;
            } else if (Float.parseFloat(deposit) == this.total) {
                showToast("El depósito es igual al total");
                return;
            }

            storeData(this.details, deposit);
        }
    }
}
