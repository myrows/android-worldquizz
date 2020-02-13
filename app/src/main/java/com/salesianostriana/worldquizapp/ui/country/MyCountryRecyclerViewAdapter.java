package com.salesianostriana.worldquizapp.ui.country;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salesianostriana.worldquizapp.R;
import com.salesianostriana.worldquizapp.countryDetails.DetailsActivity;
import com.salesianostriana.worldquizapp.ui.country.CountryFragment.OnListFragmentInteractionListener;
import com.salesianostriana.worldquizapp.model.Country;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyCountryRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final List<Country> mValues;
    private final List<Country> listcountriesFilter;
    private final OnListFragmentInteractionListener mListener;
    String flagUrl;
    Context ctx;

    public MyCountryRecyclerViewAdapter(List<Country> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        listcountriesFilter = new ArrayList<>(items);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_country, parent, false);

        ctx = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.imageCountry.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_transition_animation));

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_scale_animation));

        holder.txtName.setText(holder.mItem.getName());
        holder.txtCurrency.setText(holder.mItem.getCurrencies().get(0).getCode());
        holder.txtCapital.setText(holder.mItem.getCapital());
        holder.txtLanguage.setText(holder.mItem.getLanguages().get(0).getName());

        flagUrl = holder.mItem.getFlag();

        Glide
                .with(ctx)
                .load(holder.mItem.getFlag())
                .into(holder.imageCountry);

        //Icons country

        Glide
                .with(ctx)
                .load(R.drawable.ic_coins)
                .into(holder.imageCurrency);

        Glide
                .with(ctx)
                .load(R.drawable.ic_capital)
                .into(holder.imageCapital);

        Glide
                .with(ctx)
                .load(R.drawable.ic_language)
                .into(holder.imageLanguage);




        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Intent intent = new Intent(ctx, DetailsActivity.class);
                    intent.putExtra("nameCountry", holder.mItem.getName());
                    intent.putExtra("peopleCountry", (double)(holder.mItem.getPopulation()));
                    intent.putExtra("capitalCountry", holder.mItem.getCapital());
                    intent.putExtra("currencyCountry", holder.mItem.getCurrencies().get(0).getCode());
                    intent.putExtra("languageCountry", holder.mItem.getLanguages().get(0).getName());
                    ctx.startActivity(intent);
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override

        //Run on background thread
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Country> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(listcountriesFilter);
            }else {
                for (Country country : listcountriesFilter){
                    if(country.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(country);
                    }else if ((String.valueOf(country.getCurrencies().get(0).getCode())).toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(country);
                    }else if ((String.valueOf(country.getLanguages().get(0).getName())).toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(country);
                    }

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //Run on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues.clear();
            mValues.addAll((Collection<? extends Country>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageCountry;
        public final ImageView imageLanguage;
        public final ImageView imageCapital;
        public final ImageView imageCurrency;
        public final TextView txtName;
        public final TextView txtCurrency;
        public final TextView txtCapital;
        public final TextView txtLanguage;
        public final ConstraintLayout constraintLayout;
        public final CardView cardView;
        public Country mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageCountry = view.findViewById(R.id.imageViewCountry);
            imageLanguage = view.findViewById(R.id.imageViewLanguage);
            imageCapital = view.findViewById(R.id.imageViewCapital);
            imageCurrency = view.findViewById(R.id.imageViewCurrency);
            txtName = view.findViewById(R.id.textViewCountryName);
            txtCurrency = view.findViewById(R.id.textViewCurrency);
            txtCapital = view.findViewById(R.id.textViewCapital);
            txtLanguage = view.findViewById(R.id.textViewLanguage);
            constraintLayout = view.findViewById(R.id.constraintAnimation);
            cardView = view.findViewById(R.id.cardViewAnimation);
        }

    }
}
