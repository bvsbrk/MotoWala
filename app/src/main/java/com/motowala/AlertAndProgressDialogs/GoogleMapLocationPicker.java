package com.motowala.AlertAndProgressDialogs;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by bk on 19-06-2017.
 * It is created for Wheelo
 */

public class GoogleMapLocationPicker extends DialogFragment implements OnMapReadyCallback,
        View.OnClickListener, GoogleMap.OnMapClickListener {

    View layout;
    MapView mapView;
    GoogleMap googleMap;
    Button selectFromMapButton;
    List<Address> addresses;
    LocationManager locationManager;
    String provider;
    Criteria criteria;
    Location curentSelectedLocation;
    CameraUpdate cameraPosition;
    Geocoder geocoder;
    Address defaultAddress;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        defaultAddress = new Address(Locale.US);
        defaultAddress.setLocality("Nothing Provided");
        defaultAddress.setFeatureName("Nothing Provided");
        geocoder = new Geocoder(getActivity(), Locale.US);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        layout = inflater.inflate(R.layout.google_map_frag_dialog, null);
        mapView = (MapView) layout.findViewById(R.id.google_map_view);
        selectFromMapButton = (Button) layout.findViewById(R.id.select_from_google_map);
        selectFromMapButton.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getActivity());
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        /*
        *  Checking for location permission if permission is not there request permission and then
        *  proceed with writing code in onActivityResult
        *  If permission is already there proceed with else block
        *
        */
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            provider = locationManager.getBestProvider(criteria, true);
            Location location = getLastKnownNonNullLocation();

            /* If user clicks submit button without selecting location
             * then the default current location will be used as his selected location
             */

            // TODO: 20-06-2017  Getting null object error on location here handle it
            curentSelectedLocation = location;
            try {
                geocoder = new Geocoder(getActivity(), Locale.US);
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }


            googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
            cameraPosition = CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 17);
            googleMap.moveCamera(cameraPosition);
        }
        /* Permission request ends here */

    }

    @Override
    public void onClick(View view) {
        /* This is for getting location button */
        dismiss();
        ((WelcomeActivity) getActivity()).getUserCoordinates(curentSelectedLocation, addresses.get(0));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        selectFromMapButton.setText("SELECT THIS LOCATION");
        googleMap.clear();
        /*
        *  When user clicks on the map the currentLocation object gets updated
        *  since it has to be sent to welcome activity to
        *  continue the sign up work flow
        */
        curentSelectedLocation.setLatitude(latLng.latitude);
        curentSelectedLocation.setLongitude(latLng.longitude);

        MarkerOptions currentLocationMarker = new MarkerOptions();
        currentLocationMarker.position(latLng);
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            Address markerAddress = addresses.get(0);
            currentLocationMarker.title(markerAddress.getFeatureName() + "\n" + markerAddress.getLocality());
        }
        googleMap.addMarker(currentLocationMarker);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == 1) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                provider = locationManager.getBestProvider(criteria, true);
            }
        }
    }

    /*
     * Some times locationManager.getLastKnownLocation() returns null
     * We have to handle it by trying to get location with all providers
     * We define an external function for this
     */
    public Location getLastKnownNonNullLocation() {
        Location nonNullLocation = new Location(provider);
        Location bestLocation = null;
        nonNullLocation.setLongitude(17.381046);
        nonNullLocation.setLatitude(78.482992); // These two lines are for preventing null pointer exception
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bestLocation=locationManager.getLastKnownLocation(provider);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                bestLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                bestLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        return bestLocation==null?nonNullLocation:bestLocation;
    }
}
