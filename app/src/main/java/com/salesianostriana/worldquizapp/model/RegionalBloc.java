
package com.salesianostriana.worldquizapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RegionalBloc {

    @SerializedName("acronym")
    @Expose
    private String acronym;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otherAcronyms")
    @Expose
    private List<Object> otherAcronyms = null;
    @SerializedName("otherNames")
    @Expose
    private List<String> otherNames = null;



}
