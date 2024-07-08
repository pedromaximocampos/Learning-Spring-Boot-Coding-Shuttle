package com.springcourse.pedro.week1Introduction.week1;

import org.springframework.stereotype.Component;

@Component
public class FrostingStrawberryFlavor implements Frosting{
    @Override
    public String getFrostingType() {
        return "getting frosting strawberry flavor";
    }
}
