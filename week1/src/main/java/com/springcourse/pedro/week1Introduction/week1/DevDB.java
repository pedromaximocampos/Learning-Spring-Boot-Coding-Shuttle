package com.springcourse.pedro.week1Introduction.week1;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "chosenDB.env", havingValue = "development")
public class DevDB implements DB{

    public String getData(){
        return "Dev Data";
    }
}
