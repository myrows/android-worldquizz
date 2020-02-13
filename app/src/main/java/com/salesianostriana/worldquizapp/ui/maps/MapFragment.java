package com.salesianostriana.worldquizapp.ui.maps;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salesianostriana.worldquizapp.model.Country;
import com.salesianostriana.worldquizapp.repository.CountryService;
import com.salesianostriana.worldquizapp.repository.retrofit.ServiceGenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context context;
    CountryService service;
    List<Country> listCountries;
    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = super.onCreateView(inflater, container, savedInstanceState);
        v.setClipToOutline(true);
        service = ServiceGenerator.createService(CountryService.class);
        new CountriesAsyncTask().execute();

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        for (Country country: listCountries) {
            if(country.getLatlng().size()==0){

            }else {
                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(country.getLatlng().get(0), country.getLatlng().get(1)))
                        .title(country.getTranslations().getEs()+"#"+country.getCapital()+"#"+new DecimalFormat("#.##").format(((double)country.getPopulation())/1000000)+"#"+country.getLanguages().get(0).getName())
                        .snippet(country.getFlag())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
            }
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(LayoutInflater.from(getActivity()));
        mMap.setInfoWindowAdapter(adapter);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.0, -4.0)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4f));
    }

    public class CountriesAsyncTask extends AsyncTask<List<Country>, Void, List<Country>> {

        @Override
        protected List<Country> doInBackground(List<Country>... lists) {

            Call<List<Country>> call = service.getAllCountries();
            Response<List<Country>> response = null;
            try{
                response = call.execute();
                if(response.isSuccessful()){
                    return response.body();
                }
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            listCountries = new ArrayList<>(countries);
            getMapAsync(MapFragment.this);
        }
    }
}