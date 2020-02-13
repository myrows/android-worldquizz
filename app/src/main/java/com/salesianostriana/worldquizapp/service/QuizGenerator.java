package com.salesianostriana.worldquizapp.service;

import com.salesianostriana.worldquizapp.model.Country;
import com.salesianostriana.worldquizapp.model.Question;
import com.salesianostriana.worldquizapp.model.Quiz;
import com.salesianostriana.worldquizapp.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuizGenerator {

    //CountryRepository countryRepository = CountryRepository.getInstance();
    //List<Country> countryList = countryRepository.getListCountry();
    private List<Country> fullCountryList;

    public QuizGenerator(List<Country> fullCountryList) {
        this.fullCountryList = fullCountryList;
    }

    //Numero de preguntas
    static int QUESTION_NUMBER = 5;
    //Numero de paises en la lista
    static int COUNTRY_LIST_NUMBER = 9;

    //Genera un quizz de 5 preguntas
    public Quiz generateQuizz() {
        Quiz quiz = new Quiz();
        List<Question> questionList = new ArrayList<>();
        List<Country> selectedCountryList = generateSelectedCountryList();

        for (int i = 0; i < QUESTION_NUMBER; i++) {

            //Genera preguntas con paises aleatorios
            //Question question = generateQuestionWithRandmonCountries(i);

            //Genera preguntas con una lista dada
            Question question = generateQuestion(i, selectedCountryList, fullCountryList);
            questionList.add(question);
        }

        quiz.setQuestionList(questionList);


        return quiz;
    }

    //Genera una pregunta aleatoria entre 10 paises aleatorios
   /* private Question generateQuestionWithRandmonCountries(int numType) {
        //Pregunta aleatoria
        //int randomNum = ThreadLocalRandom.current().nextInt(0, 6);

        Question question = new Question(generateSelectedCountryList(), numType);


        return question;
    }*/

    //Genera una pregunta aleatorio entre 10 paises ofrecidos
    private Question generateQuestion(int numType, List<Country> selectedCountryList,List<Country> fullCountryList) {
        //Pregunta aleatoria
        //int randomNum = ThreadLocalRandom.current().nextInt(0, 6);

        Question question = new Question(selectedCountryList, numType, fullCountryList);


        return question;
    }


    //Genera 10 paises aleatorios
    private List<Country> generateSelectedCountryList() {
        List<Country> selectCountries = new ArrayList<>();
        for (int i = 0; i < COUNTRY_LIST_NUMBER; i++) {
            int range = (fullCountryList.size() - 1) + 1;
            int randomNum = (int)(Math.random() * range) + 0;
                    //ThreadLocalRandom.current().nextInt(0, fullCountryList.size());
            Country country;
            country = fullCountryList.get(randomNum);
            selectCountries.add(country);
        }

        return selectCountries;
    }


}
