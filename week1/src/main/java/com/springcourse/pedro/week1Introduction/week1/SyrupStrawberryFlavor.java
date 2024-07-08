package com.springcourse.pedro.week1Introduction.week1;

import org.springframework.stereotype.Component;

@Component
public class SyrupStrawberryFlavor implements Syrup{
    @Override
    public String getSyrupType() {
        return "Geting Syrup Strawberry Flavor";
    }
}
