package com.springcourse.pedro.week1Introduction.week1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SyrupChocolateFlavor implements Syrup{

    @Override
    public String getSyrupType() {
        return "geting Syrup Chocolate Flavor";
    }
}
