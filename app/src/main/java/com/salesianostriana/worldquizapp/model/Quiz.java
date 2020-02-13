package com.salesianostriana.worldquizapp.model;

import java.util.List;

import lombok.Data;

@Data
public class Quiz {
    private List<Question> questionList;
}
