package com.salesianostriana.worldquizapp.repository;

import com.salesianostriana.worldquizapp.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryService {

    @GET("rest/v2/all")
    Call<List<Country>> getAllCountries();

    @GET("rest/v2/name/{name}")
    Call<Country> getCountryByName(@Path("name") String name);

    @GET("rest/v2/name/{name}?fullText=true")
    Call<Country> getCountryByFullName(@Path("name") String name);

    @GET("rest/v2/alpha/{code}")
    Call<Country> getCountryByCodeIso(@Path("code") String code);

    @GET("rest/v2/alpha?codes={code};{code1};{code2}")
    Call<List<Country>> getListCountriesByCodeIso(@Path("code") String code, @Path("code1") String code1, @Path("code2") String code2);

    @GET("rest/v2/currency/{currency}")
    Call<Country> getCountryByCurrency(@Path("currency") String currency);

    @GET("rest/v2/lang/{et}")
    Call<List<Country>> getListCountriesByLanguage(@Path("et") String et);


    @GET("rest/v2/capital/{capital}")
    Call<Country> getCountryByCapitalCity(@Path("capital") String capital);

    @GET("rest/v2/callingcode/{callingcode}")
    Call<Country> getCountryByCallingcode(@Path("callingcode") int callingcode);

    @GET("rest/v2/region/{region}")
    Call<List<Country>> getCountriesByRegion(@Path("region") String region);

    @GET("rest/v2/regionalbloc/{regionalbloc}")
    Call<List<Country>> getCountriesByRegionalBloc(@Path("regionalbloc") String regionalbloc);







}
