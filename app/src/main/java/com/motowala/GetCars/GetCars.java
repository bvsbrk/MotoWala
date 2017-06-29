package com.motowala.GetCars;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koteswarao on 29-06-2017.
 * ${CLASS}
 */

public class GetCars {
    int timeOutCount = 0;
    private List<String> carModelNames;

    public List<String> getCarTitles(final MyProgressDialog dialog, final Context context) {
        dialog.show();
        final RequestQueue queue = Volley.newRequestQueue(context);
        final List<String> list = new ArrayList<>();
        JsonArrayRequest carBrands = new JsonArrayRequest("http://wheelo.co/cars_drop_down/get_cars.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getString(i));
                        Log.d("Tag", response.getString(i));
                    }
                    queue.stop();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.stop();
                dialog.dismiss();
                Toast.makeText(context, "Retrying...", Toast.LENGTH_SHORT).show();
                getCarTitles(dialog, context);
            }
        });
        queue.add(carBrands);
        Log.d("List returned", "Yes");
        return list;
    }

    public void setCarTitlesForCustomer(final MyProgressDialog dialog, final Context context, final MaterialSpinner spinner) {
        dialog.show();
        final RequestQueue queue = Volley.newRequestQueue(context);
        final List<String> list = new ArrayList<>();
        JsonArrayRequest carBrands = new JsonArrayRequest("http://wheelo.co/cars_drop_down/get_cars.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.add(0, "CAR BRAND");
                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getString(i));
                        Log.d("Tag", response.getString(i));
                    }
                    queue.stop();
                    dialog.dismiss();
                    spinner.setItems(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.stop();
                dialog.dismiss();
                Toast.makeText(context, "Retrying...", Toast.LENGTH_SHORT).show();
                if (timeOutCount < 2) getCarTitles(dialog, context);
                else Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(carBrands);
    }

    public void setCarModels(final String carBrand, final MyProgressDialog dialog, final MaterialSpinner carModels, final Context context) {
        dialog.show();
        String url = "http://www.wheelo.co/cars_drop_down/cars.php?car=" + carBrand;
        url = url.replace(" ", "%20");
        final RequestQueue queue = Volley.newRequestQueue(context);
        carModelNames = new ArrayList<>();
        carModelNames.add(0, "CAR MODEL");
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray(carBrand);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        carModelNames.add(object.getString("model"));
                    }
                    carModels.setItems(carModelNames);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(context, "Retrying...", Toast.LENGTH_SHORT).show();
                if (timeOutCount < 2) setCarModels(carBrand, dialog, carModels, context);
                else Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

}
