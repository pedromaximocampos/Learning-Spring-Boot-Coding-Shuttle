package com.springcourse.pedro.week1Introduction.week1;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

// Creating a bean, so we don't need to use the 'new' method and problably waste memory!
@Component
public class Apple {

    void eat(){
        System.out.println("eating apple");
    }

    @PostConstruct
    public void callThisBeforeAppleBeUsed(){
        System.out.println("creating apple");
    }

    @PreDestroy
    public void callThisBeforeAppleBeDestroyed(){
        System.out.println("destroying apple");
    }
}
