package com.salesianostriana.worldquizapp.model.unsplash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ancestry {

    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("subcategory")
    @Expose
    private Subcategory subcategory;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}
