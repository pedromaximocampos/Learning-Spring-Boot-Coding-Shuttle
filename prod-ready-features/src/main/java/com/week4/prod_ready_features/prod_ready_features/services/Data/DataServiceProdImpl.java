package com.week4.prod_ready_features.prod_ready_features.services.Data;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceProdImpl implements DataService{
    @Override
    public String getData() {
        return "Production Data";
    }
}
