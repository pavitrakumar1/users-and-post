package com.example.webservice.userandpost.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientHelper {


	Logger logger = LoggerFactory.getLogger(RestClientHelper.class);
	/*
	 * @Autowired Properties srvEndPoints;
	 */
	
	//public static final String GET_USER_ENDPOINT = "SVC_GET_USERS";
	//public static final String GET_POSTS_ENDPOINT = "SVC_GET_POSTS";
	
	//TODO: Fix the externalization of end-point properties file
	public static final String GET_USER_ENDPOINT = "http://jsonplaceholder.typicode.com/users";
	public static final String GET_POSTS_ENDPOINT = "http://jsonplaceholder.typicode.com/posts";
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Autowired
	RestTemplate restTemplate;
	
	//Generic function to pass user-def req and get user-def response. 
	public <S, T> ResponseEntity<S> exchange(HttpMethod methodType, String endPoint, T request, Class<S> responseType) throws RestClientException {
		logger.info("Before making rest API call for end point {}", endPoint);
		ResponseEntity<S> response = null;
		//HttpHeaders headers = new HttpHeaders();
		//HttpEntity<T> entity = new HttpEntity<T>(headers);
		//String serviceEndPointURI = srvEndPoints.getProperty(endPoint);
		//response = restTemplate.exchange(endPoint, methodType, entity, responseType);
		response = restTemplate.getForEntity(endPoint, responseType); // Currently we are not using any authentication, and user req. Hence using a strip-down version
		return response;
	}
}
