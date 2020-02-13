package com.salesianostriana.worldquizapp.ui.country;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.salesianostriana.worldquizapp.MyUserEntityRecyclerViewAdapter;
import com.salesianostriana.worldquizapp.R;
import com.salesianostriana.worldquizapp.model.Country;
import com.salesianostriana.worldquizapp.repository.CountryService;
import com.salesianostriana.worldquizapp.repository.retrofit.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CountryFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    CountryService service;
    boolean ordenAsc = false;
    List<Country> filterCountries;
    MenuItem itemFilter;
    int countCurrency = 0;
    int countLanguage = 0;
    MyCountryRecyclerViewAdapter adapter;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CountryFragment newInstance(int columnCount) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            service = ServiceGenerator.createService(CountryService.class);
            new CountriesAsyncTask().execute();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Country item);
    }

    //[START] Toolbar opciones


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.country_menu, menu);
        MenuItem itemFilter = menu.findItem(R.id.searchViewFilter);
        SearchView searchView = (SearchView) itemFilter.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.filtroCountry:

                break;

            case R.id.filterCurrency:
                countCurrency++;

                if(countCurrency%2 != 0){
                    item.setChecked(true);
                    Toasty.info(context, "Ordenado por moneda", Toasty.LENGTH_SHORT).show();
                    Collections.sort(filterCountries, new ComparatorCurrency());
                    recyclerView.setAdapter(new MyCountryRecyclerViewAdapter(filterCountries, mListener));

                }else if(countCurrency%2 == 0){
                    item.setChecked(false);
                    new CountriesAsyncTask().execute();
                }


                break;

            case R.id.filterLanguage:
                countLanguage++;

                if(countLanguage%2 != 0){
                    item.setChecked(true);
                    Toasty.info(context, "Ordenado por idioma", Toasty.LENGTH_SHORT).show();
                    Collections.sort(filterCountries, new ComparatorLanguage());
                    recyclerView.setAdapter(new MyCountryRecyclerViewAdapter(filterCountries, mListener));

                }else if(countLanguage%2 == 0){
                    item.setChecked(false);
                    new CountriesAsyncTask().execute();
                }



                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Comparators
    public class ComparatorCurrency implements Comparator<Country>{

        @Override
        public int compare(Country o1, Country o2) {
            return (String.valueOf(o1.getCurrencies().get(0).getCode())).compareTo(String.valueOf(o2.getCurrencies().get(0).getCode()));
        }
    }

    public class ComparatorLanguage implements Comparator<Country>{

        @Override
        public int compare(Country o1, Country o2) {
            return o1.getLanguages().get(0).getName().compareTo(o2.getLanguages().get(0).getName());
        }
    }

    public class CountriesAsyncTask extends AsyncTask<List<Country>, Void, List<Country>>{

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
            Collections.sort(countries);

            adapter = new MyCountryRecyclerViewAdapter(countries, mListener);
            recyclerView.setAdapter(adapter);

            filterCountries = new ArrayList<>(countries) ;

        }

    }
}
