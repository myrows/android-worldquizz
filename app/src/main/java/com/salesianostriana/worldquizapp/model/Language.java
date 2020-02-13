
package com.salesianostriana.worldquizapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Language {

    @SerializedName("iso639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso639_2")
    @Expose
    private String iso6392;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nativeName")
    @Expose
    private String nativeName;


}
