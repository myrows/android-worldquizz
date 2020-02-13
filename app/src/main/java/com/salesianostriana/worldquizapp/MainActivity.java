package com.salesianostriana.worldquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.salesianostriana.worldquizapp.model.Country;
import com.salesianostriana.worldquizapp.ui.country.CountryFragment;

import com.salesianostriana.worldquizapp.model.UserEntity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements CountryFragment.OnListFragmentInteractionListener, IRankingListener {


    private Country item;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    /*private CountryService service;*/
    Toolbar toolbar;
    private ImageView imagenPerfilToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        imagenPerfilToolbar = findViewById(R.id.imagenPerfil);

        Glide.with(this).load(account.getPhotoUrl()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(imagenPerfilToolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Worldmap");


        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_countries, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Al cargar el app se cargan todos los paises en un repositorio
       /* service = ServiceGenerator.createService(CountryService.class);
        Call<List<Country>> call = service.getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    CountryRepository countryRepository = CountryRepository.getInstance();
                    countryRepository.setListCountry(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(MainActivity.this, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public void onListFragmentInteraction(Country item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //Iniciar quizz
            case R.id.buttonGoQuizz:
                Intent i = new Intent(MainActivity.this, QuizzActivity.class);
                startActivityForResult(i,10001);
                break;

            case R.id.menu_logout:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                break;

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onclickRanking(UserEntity u) {

    }
}
