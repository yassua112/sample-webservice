package com.oracle.external.services.sampleservice.mapper;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRestApi implements Serializable{
    private GetDataFromRestApiResponse sampleservicers;

}
