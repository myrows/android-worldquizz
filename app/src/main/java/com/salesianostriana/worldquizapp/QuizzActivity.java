package com.salesianostriana.worldquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Response;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.salesianostriana.worldquizapp.model.Country;
import com.salesianostriana.worldquizapp.model.Quiz;
import com.salesianostriana.worldquizapp.repository.CountryService;
import com.salesianostriana.worldquizapp.repository.retrofit.ServiceGenerator;
import com.salesianostriana.worldquizapp.service.QuizGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static android.graphics.BlendMode.COLOR;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    CountryService service;
    Quiz quiz;
    int quizzPoints;
    TextView questionTitle;
    Button optionOne, optionTwo, optionThree;
    ProgressBar progressBar;
    int listPosition = 0;
    String loggedUserId;

    private LottieAnimationView acierto;
    private LottieAnimationView fallo;

    ImageView imageViewFlag;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference usersRef = db.collection("users");

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Si, quiero salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", null);
        builder.setMessage("¿Está seguro que desea salir?¡Su progreso será borrado!");
        builder.setTitle(R.string.app_name);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        quizzPoints = 0;

        listPosition = 0;

        questionTitle = findViewById(R.id.textViewQuestion);
        optionOne = findViewById(R.id.buttonResponse1);
        optionOne.setBackgroundResource(R.drawable.custom_quizz_button);
        optionTwo = findViewById(R.id.buttonResponse2);
        optionThree = findViewById(R.id.buttonResponse3);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);


        imageViewFlag = findViewById(R.id.imageViewFlagCustom);

        //ImageButtons
        /*btnImage = findViewById(R.id.buttonResponseCustom);
        btnImage1 = findViewById(R.id.buttonResponse2Custom);
        btnImage2 = findViewById(R.id.buttonResponse3Custom);*/

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        optionOne.setOnClickListener(this);
        optionTwo.setOnClickListener(this);
        optionThree.setOnClickListener(this);

        acierto = findViewById(R.id.animacionAcierto);
        acierto.setVisibility(View.GONE);
        fallo = findViewById(R.id.animacionFallo);
        fallo.setVisibility(View.GONE);


        /*btnImage.setOnClickListener(this);
        btnImage1.setOnClickListener(this);
        btnImage2.setOnClickListener(this);*/

        service = ServiceGenerator.createService(CountryService.class);
        new CountriesAsyncTask().execute();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonResponse1:

                //v.setBackground(R.drawable.custom_quizz_button_amarillo);

                if ((boolean) optionOne.getTag() == true) {

                    acierto.setVisibility(View.VISIBLE);
                    acierto.setProgress(0);
                    acierto.playAnimation();

                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {
                            acierto.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);
                    quizzPoints += 1;

                } else {

                    fallo.setVisibility(View.VISIBLE);
                    fallo.setProgress(0);
                    fallo.playAnimation();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {

                            fallo.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);


                }

                break;

            case R.id.buttonResponse2:
                if ((boolean) optionTwo.getTag() == true) {

                    acierto.setVisibility(View.VISIBLE);
                    acierto.setProgress(0);
                    acierto.playAnimation();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {

                            acierto.setVisibility(View.GONE);
                            acierto.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);
                    quizzPoints += 1;

                } else {

                    fallo.setVisibility(View.VISIBLE);
                    fallo.setProgress(0);
                    fallo.playAnimation();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {

                            fallo.setVisibility(View.GONE);
                            fallo.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);


                }


                break;
            case R.id.buttonResponse3:
                if ((boolean) optionThree.getTag() == true) {

                    acierto.setVisibility(View.VISIBLE);
                    acierto.setProgress(0);
                    acierto.playAnimation();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {

                            acierto.setVisibility(View.GONE);
                            acierto.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);
                    quizzPoints += 1;


                } else {
                    fallo.setVisibility(View.VISIBLE);
                    fallo.setProgress(0);
                    fallo.playAnimation();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        public void run() {

                            fallo.setVisibility(View.GONE);
                            fallo.setVisibility(View.GONE);
                            listPosition++;
                            paintView(listPosition);
                        }
                    }, 2000);


                }


                break;

        }


        Log.i("VALORQUIZZ", Integer.toString(quizzPoints));
    }

    public class CountriesAsyncTask extends AsyncTask<List<Country>, Void, List<Country>> {

        @Override
        protected List<Country> doInBackground(List<Country>... lists) {

            Call<List<Country>> call = service.getAllCountries();
            Response<List<Country>> response = null;

            try {
                response = call.execute();

                if (response.isSuccessful()) {
                    return response.body();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<Country> countries) {

            QuizGenerator quizGenerator = new QuizGenerator(countries);
            quiz = quizGenerator.generateQuizz();
            paintView(0);

        }
    }

    private void paintView(int listPosition) {

        if (listPosition == 5) {

            String currentUserEmail = firebaseUser.getEmail();


            usersRef.whereEqualTo("email", currentUserEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                loggedUserId = task.getResult().getDocuments().get(0).getId();
                                DocumentReference userLoggedReference = usersRef.document(loggedUserId);
                                userLoggedReference.update("totalPoints", FieldValue.increment(quizzPoints));
                                userLoggedReference.update("attemps", FieldValue.increment(1));
                                Log.d("DOCUMENT ID", loggedUserId);
                            } else {
                                Log.d("DOCUMENT ID", loggedUserId, task.getException());
                            }
                        }
                    });


            AlertDialog.Builder builderFinish = new AlertDialog.Builder(this);
            builderFinish.setPositiveButton("¡Vale!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();


                }
            });
            builderFinish.setCancelable(false);
            builderFinish.setMessage("¡Ha ganado " + Integer.toString(quizzPoints) + " puntos!");
            builderFinish.show();


        } else {

            progressBar.setProgress(listPosition + 1);

            if (listPosition == 3) {
                questionTitle.setVisibility(View.INVISIBLE);
                imageViewFlag.setVisibility(View.VISIBLE);


               Glide
                        .with(this)
                        .load(quiz.getQuestionList().get(listPosition).getTitle())
                        .into(imageViewFlag);

            } else {
                questionTitle.setVisibility(View.VISIBLE);
                imageViewFlag.setVisibility(View.INVISIBLE);
                questionTitle.setText(quiz.getQuestionList().get(listPosition).getTitle());
            }
            List<Button> buttonList = new ArrayList<>();
            buttonList.add(optionOne);
            buttonList.add(optionTwo);
            buttonList.add(optionThree);
            int randomButton;
            int range;

            for (int i = 0; i < 3; i++) {
                Button button = null;

                switch (i) {
                    case 0:
                        range = (buttonList.size() - 1) + 1;
                        randomButton = (int) (Math.random() * range) + 0;

                        button = buttonList.get(randomButton);
                        button.setText(quiz.getQuestionList().get(listPosition).getTrueResponse().getTitle());
                        button.setTag(quiz.getQuestionList().get(listPosition).getTrueResponse().getBooleanValue());
                        buttonList.remove(button);
                        break;
                    case 1:
                        range = (buttonList.size() - 1) + 1;
                        randomButton = (int) (Math.random() * range) + 0;
                        button = buttonList.get(randomButton);
                        button.setText(quiz.getQuestionList().get(listPosition).getFailResponse().getTitle());
                        button.setTag(quiz.getQuestionList().get(listPosition).getFailResponse().getBooleanValue());
                        buttonList.remove(button);
                        break;
                    case 2:

                        button = buttonList.get(0);
                        button.setText(quiz.getQuestionList().get(listPosition).getFailResponse2().getTitle());
                        button.setTag(quiz.getQuestionList().get(listPosition).getFailResponse2().getBooleanValue());
                        buttonList.remove(button);
                        break;
                }
            }

        }
        Log.i("LISTPOSITION", Integer.toString(listPosition));

    }


}