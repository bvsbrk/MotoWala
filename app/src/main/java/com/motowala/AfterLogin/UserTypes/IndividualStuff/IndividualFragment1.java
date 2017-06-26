package com.motowala.AfterLogin.UserTypes.IndividualStuff;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bk on 15-06-2017.
 * It is created for Wheelo
 */

public class IndividualFragment1 extends Fragment {
    View v;
    MaterialSpinner carBrands;
    MaterialSpinner carModels;
    MaterialSpinner carCategories;
    MyProgressDialog dialog;
    CircleImageView userIcon2;
    TextView userName2, userType2;
    List<String> carBrandsNames;
    List<String> carModelNames;
    List<String> carCategoryNames;
    EditText userMobileNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.welcome_individual_fragment_1, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialog = new MyProgressDialog(getActivity(), "Please wait...", "Fetching Models...");
        dialog.show();
        carBrands = (MaterialSpinner) v.findViewById(R.id.carBrands);
        carModels = (MaterialSpinner) v.findViewById(R.id.carModels);
        carCategories = (MaterialSpinner) v.findViewById(R.id.carCategories);
        userIcon2 = (CircleImageView) v.findViewById(R.id.userIcon2);
        userName2 = (TextView) v.findViewById(R.id.userName2);
        userType2 = (TextView) v.findViewById(R.id.userType2);
        userMobileNumber = (EditText) v.findViewById(R.id.userMobile2);
        ((WelcomeActivity) getActivity()).setUserName(userName2);
        ((WelcomeActivity) getActivity()).setImageIcon(userIcon2);
        ((WelcomeActivity) getActivity()).setUserType(userType2);
        carBrandsNames = ((WelcomeActivity) getActivity()).getCarTitles();
        carBrandsNames.add(0, "CAR BRAND");
        carBrands.setItems(carBrandsNames);
        dialog.dismiss();
        carBrands.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String selectedCarBrand = carBrandsNames.get(position);
                if (!selectedCarBrand.equals("CAR BRAND")) {
                    setCarModels(selectedCarBrand);
                }
            }
        });
        carModels.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                setCategory(position);
            }
        });
    }

    public void setCarModels(final String carBrand) {
        dialog.show();
        String url = "http://www.wheelo.co/cars_drop_down/cars.php?car=" + carBrand;
        url = url.replace(" ", "%20");
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        carModelNames = new ArrayList<>();
        carCategoryNames = new ArrayList<>();
        carModelNames.add(0, "CAR MODEL");
        carCategoryNames.add(0, "CAR CATEGORY");
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray(carBrand);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        carModelNames.add(object.getString("model"));
                        carCategoryNames.add(object.getString("category"));
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
                Toast.makeText(getActivity(), "Error occured\nPlease try again", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void setCategory(int position) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("CAR CATEGORY");
        categories.add(carCategoryNames.get(position));
        carCategories.setItems(categories);
    }

    public List<String> getSelectedOptions(Context context) {
        List<String> list = new ArrayList<>();
        int brandNumber = carBrands.getSelectedIndex();
        int modelNumber = carModels.getSelectedIndex();
        int categoryNumber = carCategories.getSelectedIndex();
        String mobile = userMobileNumber.getText().toString();
        if (brandNumber == 0 || modelNumber == 0 || categoryNumber == 0 || brandNumber == -1 || modelNumber == -1 || categoryNumber == -1) {
            Toast.makeText(context, "Please Select a valid car...", Toast.LENGTH_SHORT).show();
        } else if (mobile.length() == 0) {
            Toast.makeText(context, "Please provide valid mobile number...", Toast.LENGTH_SHORT).show();
        } else {

            /*String carBrand=carBrandsNames.get(brandNumber);  This is not needed any way */
            String carModel=carModelNames.get(modelNumber);
            String carCategory=carCategoryNames.get(categoryNumber);
            list.add(0,carModel+" "+carCategory);
            list.add(1,mobile);
            return list;
        }
        return list;
    }
}
