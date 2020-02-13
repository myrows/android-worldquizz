package com.salesianostriana.worldquizapp.countryDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.salesianostriana.worldquizapp.R;
import com.salesianostriana.worldquizapp.model.unsplash.Image;
import com.salesianostriana.worldquizapp.model.unsplash.Result;
import com.salesianostriana.worldquizapp.repository.UnsplashService;
import com.salesianostriana.worldquizapp.repository.retrofit.UnsplashGenerator;

import java.io.IOException;
import java.text.DecimalFormat;


import retrofit2.Call;
import retrofit2.Response;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class DetailsActivity extends AppCompatActivity /*implements ResultFragment.OnListFragmentInteractionListener*/ {

    UnsplashService service;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //getSupportActionBar().setTitle(getIntent().getExtras().get("nameCountry").toString());
        toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().get("nameCountry").toString());

        service = UnsplashGenerator.createService(UnsplashService.class);
        new ImageUnsplashAsyncTask(this).execute();
    }

    /*@Override
    public void onListFragmentInteraction(Result item) {

    }*/

    public class ImageUnsplashAsyncTask extends AsyncTask<Image, Void, Image> {

        DetailsActivity ctx;
        FlipperLayout flipperLayout;
        ImageView imageViewPeople;
        ImageView imageViewCurrency;
        ImageView imageViewCapital;
        ImageView imageViewLanguage;
        TextView txtPeople;
        TextView txtCurrency;
        TextView txtCapital;
        TextView txtLanguage;

        public ImageUnsplashAsyncTask(DetailsActivity ctx) {
            this.ctx = ctx;
        }

        @Override
        protected Image doInBackground(Image... images) {

            Call<Image> call = service.getImagesUnsplash(getIntent().getExtras().get("nameCountry").toString(), "1");
            Response<Image> response = null;

            try {
                response = call.execute();

                if (response.isSuccessful()) {
                    return response.body();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Image image) {

            flipperLayout = findViewById(R.id.flipper);
            imageViewPeople = findViewById(R.id.imageViewPeopleD);
            imageViewCurrency = findViewById(R.id.imageViewCurrencyD);
            imageViewCapital = findViewById(R.id.imageViewCapitalD);
            imageViewLanguage = findViewById(R.id.imageViewLanguageD);
            txtPeople = findViewById(R.id.textViewPeopleD);
            txtCurrency = findViewById(R.id.textViewCurrencyD);
            txtCapital = findViewById(R.id.textViewCapitalD);
            txtLanguage = findViewById(R.id.textViewLanguageD);



            for (int i = 0; i < image.getResults().size(); i++) {
                FlipperView view = new FlipperView(ctx);
                view.setImageUrl(image.getResults().get(i).getUrls().getRegular());
                view.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                flipperLayout.addFlipperView(view);
            }

            Glide
                    .with(ctx)
                    .load(R.drawable.ic_people)
                    .into(imageViewPeople);

            txtPeople.setText((new DecimalFormat("#.##").format((double)getIntent().getExtras().get("peopleCountry")/1000000))+" M");

            Glide
                    .with(ctx)
                    .load(R.drawable.ic_coins)
                    .into(imageViewCurrency);

            txtCurrency.setText(getIntent().getExtras().get("currencyCountry").toString());

            Glide
                    .with(ctx)
                    .load(R.drawable.ic_capital)
                    .into(imageViewCapital);

            txtCapital.setText(getIntent().getExtras().get("capitalCountry").toString());

            Glide
                    .with(ctx)
                    .load(R.drawable.ic_language)
                    .into(imageViewLanguage);

            txtLanguage.setText(getIntent().getExtras().get("languageCountry").toString());

        }
    }
}
