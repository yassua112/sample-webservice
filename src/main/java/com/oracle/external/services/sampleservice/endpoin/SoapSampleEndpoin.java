package com.oracle.external.services.sampleservice.endpoin;



import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapHeader;

import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.oracle.external.services.sampleservice.header.Authenticationheader;
import com.oracle.external.services.sampleservice.header.ObjectFactory;
import com.oracle.external.services.sampleservice.request.v1.Sampleservicerq;
import com.oracle.external.services.sampleservice.response.v1.Sampleservicers;
import com.oracle.external.services.sampleservice.service.SoapSampleService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPHeaderElement;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import lombok.extern.slf4j.Slf4j;

import org.w3c.dom.Element;


@Endpoint
@Slf4j
public class SoapSampleEndpoin {
    
	private static final String NAMESPACE = "http://www.oracle.com/external/services/sampleservice/request/v1.0";
	
    @Autowired
	private SoapSampleService service;

	

	@PayloadRoot(namespace = NAMESPACE, localPart = "sampleservicerq")
	@ResponsePayload
	public Sampleservicers getLoanStatus(@RequestPayload Sampleservicerq request,@SoapHeader(value = "{http://www.oracle.com}authenticationheader") SoapHeaderElement  authToken) throws Exception{
		Authenticationheader headerRequest = new Authenticationheader();	
	try {
        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        DOMSource headerSource = (DOMSource) authToken.getSource();
        headerRequest = unmarshaller.unmarshal(headerSource.getNode(), Authenticationheader.class).getValue();
    } catch (JAXBException e) {
        e.printStackTrace();
    }
	return service.isTransactionValid(request,headerRequest);
	}

}
