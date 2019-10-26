package com.nazjara;

import com.nazjara.dto.BeerDto;
import com.nazjara.dto.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@ConfigurationProperties(value = "brewery", ignoreUnknownFields = false)
public class BreweryClient {

    private static final String BEER_PATH_V1 = "/api/v1/beer/";
    private static final String CUSTOMER_PATH_V1 = "/api/v1/customer/";

    private String host;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(host + BEER_PATH_V1 + id.toString(), BeerDto.class);
    }

    public String saveBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(host + BEER_PATH_V1, beerDto).toString();
    }

    public void updateBeer(UUID id, BeerDto beerDto) {
        restTemplate.put(host + BEER_PATH_V1 + id.toString(), beerDto);
    }

    public void deleteBeer(UUID id) {
        restTemplate.delete(host + BEER_PATH_V1 + id.toString());
    }

    public CustomerDto getCustomerById(UUID id) {
        return restTemplate.getForObject(host + CUSTOMER_PATH_V1 + id.toString(), CustomerDto.class);
    }

    public String saveCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(host + CUSTOMER_PATH_V1, customerDto).toString();
    }

    public void updateCustomer(UUID id, CustomerDto customerDto) {
        restTemplate.put(host + CUSTOMER_PATH_V1 + id.toString(), customerDto);
    }

    public void deleteClient(UUID id) {
        restTemplate.delete(host + CUSTOMER_PATH_V1 + id.toString());
    }

    public void setHost(String host) {
        this.host = host;
    }
}