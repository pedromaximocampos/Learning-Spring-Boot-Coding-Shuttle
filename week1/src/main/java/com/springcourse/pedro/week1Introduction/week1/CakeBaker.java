package com.springcourse.pedro.week1Introduction.week1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

    private Frosting frostingFlavor;
    private Syrup  syrupFlavor;

    @Autowired
    public CakeBaker(Frosting frostingFlavor, Syrup syrupFlavor) {
        this.frostingFlavor = frostingFlavor;
        this.syrupFlavor = syrupFlavor;
    }

    public void bakeCake(){
        String frostingCake = frostingFlavor.getFrostingType();
        String syrupCake = syrupFlavor.getSyrupType();

        System.out.println(frostingCake +" and " + syrupCake);

    }

    public void setSyrupFlavor(Syrup syrup) {
        this.syrupFlavor = syrup;
    }

    public void setFrostingFlavor(Frosting frostingFlavor) {
        this.frostingFlavor = frostingFlavor;
    }

}
