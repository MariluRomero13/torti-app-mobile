package com.example.torti_app.Models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app.Data;
import com.example.torti_app.activities.DeliveryHistoryActivity;
import com.example.torti_app.singletons.VolleyS;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private static String token;

    public static String getToken(Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        User.token = p.getString("token", null);
        return token;
    }

    public static void setToken(Context context, String token) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("token", token);
        edit.apply();
        User.token = token;
    }

    public static void login(final Context context, final Activity activity, String username, String password) {
        try {
            JSONObject params = new JSONObject();
            params.put("username", username);
            params.put("password", password);

            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, Data.api_url + "login", params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("LOGIN::", response.toString());
                            try {
                                String token = response.getString("token");
                                setToken(context, token);
                                Log.d("TOKEN::", getToken(context));
                                activity.startActivity(new Intent(context, DeliveryHistoryActivity.class));
                                activity.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("LOGIN-ERROR::", error.toString());
                }
            });

            VolleyS.getInstance(context).getQueue().add(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
