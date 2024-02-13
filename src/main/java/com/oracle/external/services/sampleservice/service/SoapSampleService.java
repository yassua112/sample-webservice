package com.oracle.external.services.sampleservice.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;


import com.oracle.external.services.sampleservice.header.Authenticationheader;
import com.oracle.external.services.sampleservice.mapper.GetDataFromRestApiResponse;
import com.oracle.external.services.sampleservice.mapper.PostDataToRestApiRequest;
import com.oracle.external.services.sampleservice.mapper.RequestRestApi;
import com.oracle.external.services.sampleservice.mapper.ResponseRestApi;
import com.oracle.external.services.sampleservice.request.v1.Sampleservicerq;
import com.oracle.external.services.sampleservice.response.v1.Sampleservicers;

@Service
public class SoapSampleService {

    private final static String URI_LOCAL_REST = "http://localhost:8080/external/services/rest/sample-service";

    public Sampleservicers isTransactionValid(Sampleservicerq resquest, Authenticationheader headerRequest) throws Exception{
        dataValidation(resquest,headerRequest);
        ResponseRestApi dataResponse = getDataFromRestApiService(resquest);
        Sampleservicers response = new Sampleservicers();
        response.setErrorCode(dataResponse.getSampleservicers().getError_code());
        response.setErrorMsg(dataResponse.getSampleservicers().getError_msg());
        response.setTrxId(dataResponse.getSampleservicers().getTrx_id());
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

    @SuppressWarnings("null")
    private ResponseRestApi getDataFromRestApiService(Sampleservicerq resquest){
        GetDataFromRestApiResponse responseData = new GetDataFromRestApiResponse();
        try{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> requestEntitiy = new HttpEntity<>(RequestRestApi.builder().sampleservicerq(PostDataToRestApiRequest.builder()
                                                                                                        .service_id(resquest.getServiceId())
                                                                                                        .type(resquest.getType())
                                                                                                        .order_type(resquest.getOrderType())
                                                                                                        .trx_id(resquest.getTrxId())
                                                                                                        .build())
                                                                                                        .build());

        ResponseEntity<ResponseRestApi> response = restTemplate.postForEntity(URI_LOCAL_REST, requestEntitiy, ResponseRestApi.class);
        if(response.getBody().getSampleservicers().getError_code().equals("200") ){
            throw new Exception(response.getBody().getSampleservicers().getError_msg());
        }
        responseData.setError_code(response.getBody().getSampleservicers().getError_code());
        responseData.setError_msg(response.getBody().getSampleservicers().getError_msg());
        responseData.setTrx_id(response.getBody().getSampleservicers().getTrx_id());
        }catch(Exception e){
            e.getLocalizedMessage();
        }
        
        return ResponseRestApi.builder().sampleservicers(responseData).build(); 
    
    }
}
