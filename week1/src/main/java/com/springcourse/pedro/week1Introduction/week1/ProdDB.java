package com.springcourse.pedro.week1Introduction.week1;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "chosenDB.env", havingValue = "production")
public class ProdDB implements DB{

    public String getData(){
        return "prod data";
    }
}
