package com.week4.prod_ready_features.prod_ready_features.services.Data;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DataServiceDevImpl implements DataService{
    @Override
    public String getData() {
        return "Development Data";
    }
}
