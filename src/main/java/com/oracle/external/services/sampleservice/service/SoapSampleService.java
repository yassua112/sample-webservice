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
        ResponseRestApi dataResponse = getDataFromRestApiService(resquest,headerRequest);
        Sampleservicers response = new Sampleservicers();
        response.setErrorCode(dataResponse.getSampleservicers().getError_code());
        response.setErrorMsg(dataResponse.getSampleservicers().getError_msg());
        response.setTrxId(dataResponse.getSampleservicers().getTrx_id());
        return response;
    }

    @SuppressWarnings("null")
    private ResponseRestApi getDataFromRestApiService(Sampleservicerq resquest,Authenticationheader headerRequest){
        GetDataFromRestApiResponse responseData = new GetDataFromRestApiResponse();
        try{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("password", headerRequest.getPassword());
        headers.add("username", headerRequest.getUsername());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> requestEntitiy = new HttpEntity<>(RequestRestApi.builder().sampleservicerq(PostDataToRestApiRequest.builder()
                                                                                                        .service_id(resquest.getServiceId())
                                                                                                        .type(resquest.getType())
                                                                                                        .order_type(resquest.getOrderType())
                                                                                                        .trx_id(resquest.getTrxId())
                                                                                                        .build())
                                                                                                        .build(),headers);

        ResponseEntity<ResponseRestApi> response = restTemplate.postForEntity(URI_LOCAL_REST, requestEntitiy, ResponseRestApi.class);
        responseData.setError_code(response.getBody().getSampleservicers().getError_code());
        responseData.setError_msg(response.getBody().getSampleservicers().getError_msg());
        responseData.setTrx_id(response.getBody().getSampleservicers().getTrx_id());
        }catch(Exception e){
              
        responseData.setError_code("500");
        responseData.setError_msg("Ada Error Saat Mengakses Rest Api Service");
        return ResponseRestApi.builder().sampleservicers(responseData).build(); 
            
        }
        
        return ResponseRestApi.builder().sampleservicers(responseData).build(); 
    
    }
}
