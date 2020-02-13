package com.salesianostriana.worldquizapp.repository;

import com.salesianostriana.worldquizapp.model.Country;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CountryRepository {
    private static CountryRepository countryRepositoryInstance = null;
    private List<Country> listCountry;

    private CountryRepository() {

        this.listCountry = new ArrayList<Country>();
    }

    public static CountryRepository getInstance() {
        if (countryRepositoryInstance == null)
            countryRepositoryInstance = new CountryRepository();

        return countryRepositoryInstance;
    }
}

