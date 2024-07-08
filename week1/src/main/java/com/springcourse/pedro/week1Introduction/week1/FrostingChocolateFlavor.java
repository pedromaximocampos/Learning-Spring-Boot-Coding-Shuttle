package com.springcourse.pedro.week1Introduction.week1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FrostingChocolateFlavor implements Frosting{
    @Override
    public String getFrostingType() {
        return "geting Frosting Chocolate Flavor";
    }
}
