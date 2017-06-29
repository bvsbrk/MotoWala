package com.motowala.AfterLogin.CustomerSignedUp.NavFragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.Config;
import com.motowala.CustomerDatabases.CustomerDatabase;
import com.motowala.GetCars.GetCars;
import com.motowala.R;
import com.motowala.WriteToFirebase.WriteToFirebase;

/**
 * Created by bk on 24-06-2017.
 * It is created for Wheelo
 */

public class AddCar extends Fragment {
    View layout;
    MaterialSpinner carBrands;
    MaterialSpinner carModels;
    GetCars cars;
    Button addCarButton;
    Config config;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.cust_nav_add_car, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        config = new Config(getActivity());
        carBrands = (MaterialSpinner) layout.findViewById(R.id.carBrands);
        carModels = (MaterialSpinner) layout.findViewById(R.id.carModels);
        addCarButton = (Button) layout.findViewById(R.id.nav_add_car_button);
        cars = new GetCars();
        final MyProgressDialog dialog = new MyProgressDialog(getActivity(), "Fetching cars...", "Please wait...");
        cars.setCarTitlesForCustomer(dialog, getActivity(), carBrands);
        cars.setCarModels("Audi", dialog, carModels, getActivity());
        carBrands.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                cars.setCarModels(item.toString(), dialog, carModels, getActivity());
            }
        });
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCarModel = carModels.getText().toString();
                boolean isValidCar = performCheck(selectedCarModel);
                if (isValidCar && !selectedCarModel.equals("CAR MODEL")) {
                    WriteToFirebase firebase = new WriteToFirebase(getActivity());
                    firebase.addNewCarToCustomer(carModels.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "You already own this car", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean performCheck(String selectedCar) {
        CustomerDatabase database = new CustomerDatabase(getActivity(),
                config.databaseName,
                config.databaseNewVersion,
                config.databaseOldVersion,
                config.carTablesName);
        String[] cols = {config.carsTableCarColumn};
        String[] selArgs = {selectedCar};
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(config.carTablesName,
                cols,
                config.carsTableCarColumn + "=?",
                selArgs, null,
                null, null);
        return cursor.getCount() == 0;
    }
}
