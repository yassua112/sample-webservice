package com.oracle.external.services.sampleservice.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.oracle.external.services.sampleservice.header.Authenticationheader;
import com.oracle.external.services.sampleservice.mapper.ResponseRestApi;
import com.oracle.external.services.sampleservice.request.v1.Sampleservicerq;
import com.oracle.external.services.sampleservice.response.v1.Sampleservicers;

@Service
public class SoapSampleService {

    public Sampleservicers isTransactionValid(Sampleservicerq resquest, Authenticationheader headerRequest) throws Exception{
        dataValidation(resquest,headerRequest);

        Sampleservicers response = new Sampleservicers();
        response.setErrorCode("23123");
        return response;
    }

    private void dataValidation(Sampleservicerq resquest, Authenticationheader headerRequest) throws Exception{
        if(ObjectUtils.isEmpty(headerRequest.getUsername())){
            throw new Exception("UserName Tidak Boleh Kosong");
        }
        if(ObjectUtils.isEmpty(headerRequest.getPassword())){
            throw new Exception("Password Tidak Boleh Kosong");
        }

        if(ObjectUtils.isEmpty(resquest.getServiceId())){
            throw new Exception("Service Id Tidak Boleh Kosong");
        }
        
        if(ObjectUtils.isEmpty(resquest.getOrderType())){
            throw new Exception("Order Id Tidak Boleh Kosong");
        }
        if(headerRequest.getPassword().equals("admin") && headerRequest.getUsername().equals("user")){
            throw new Exception("Authentikasi Anda Tidak Benar");
        }

    }

    private ResponseRestApi getDataFromRestApiService(Sampleservicerq resquest){


        return null;
    }
}
