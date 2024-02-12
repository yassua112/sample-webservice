package com.oracle.external.services.sampleservice.service;

import org.springframework.stereotype.Service;

import com.oracle.external.services.sampleservice.header.Authenticationheader;
import com.oracle.external.services.sampleservice.request.v1.Sampleservicerq;
import com.oracle.external.services.sampleservice.response.v1.Sampleservicers;

@Service
public class SoapSampleService {

    public Sampleservicers isTransactionValid(Sampleservicerq resquest, Authenticationheader headerRequest){

        Sampleservicers response = new Sampleservicers();
        response.setErrorCode("23123");
        return response;
    }

    
}
