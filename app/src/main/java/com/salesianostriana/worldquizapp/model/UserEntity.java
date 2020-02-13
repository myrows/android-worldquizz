package com.salesianostriana.worldquizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class
UserEntity {

    private String name, surname, nickname, email,photoUrl;
    private int totalPoints, attemps;


    public double getAverageScore(){
        if(totalPoints == 0 || attemps == 0) {
            double nuevoUsuario=0;
            return nuevoUsuario;
        }
        else{

            return (totalPoints/attemps)*20;
        }
    }




}
