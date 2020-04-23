package com.example.torti_app.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.torti_app.Data;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.R;
import com.example.torti_app.singletons.VolleyS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    GoogleMap map;
    private Delivery delivery = null;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null) {
            this.delivery = getArguments().getParcelable("delivery");
        }


        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                styleMap(googleMap);
                if (delivery != null) {
                    int customerId = delivery.getCustomer().getId();
                    getLocation(customerId);
                }
            }
        });
        return rootView;
    }

    private void getLocation (int customerId) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                Data.api_url + "customer-location/" + customerId,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LatLng latLng = null;
                Log.e("si" ,"entro");
                if(response.length() == 0) {
                    latLng = new LatLng(25.5528341, -103.3926577);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng);
                    if (getContext() != null) {
                        BitmapDrawable bitmapDrawable =
                                (BitmapDrawable) ContextCompat.getDrawable(getContext(), R.drawable.shop_icon);
                        if (bitmapDrawable != null) {
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                                    Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                            ));
                        }
                    }
                    map.addMarker(markerOptions);
                } else {
                for (int i = 0; i < response.length() ; i++) {
                    try {
                        JSONObject location = response.getJSONObject(i);
                        double latitude = location.getDouble("latitude");
                        double longitude = location.getDouble("longitude");
                        latLng = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng);
                        if (getContext() != null) {
                            BitmapDrawable bitmapDrawable =
                                    (BitmapDrawable) ContextCompat.getDrawable(getContext(), R.drawable.shop_icon);
                            if (bitmapDrawable != null) {
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                                        Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                                ));
                            }
                        }
                        map.addMarker(markerOptions);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                }

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError
                        || (error.networkResponse != null && error.networkResponse.statusCode == 401)){

                }
                else if(error.networkResponse != null && error.networkResponse.statusCode == 400){
                    showToast("Petición errónea");

                }
                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the request has either time out or there is no connection
                    showToast("Por favor verifique su conexión a internet");
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    showToast("Error en el servidor");
                } else if (error instanceof NetworkError) {
                    showToast("Por favor verifique su conexión a internet");
                }
            }
        });
        VolleyS.getInstance(getContext()).getQueue().add(request);
    }

    private void showToast (String message) {
        try {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleMap(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));
            if (!success) {
                Log.e("MapActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapActivity", "Can't find style. Error: ", e);
        }

    }
}
