package com.golovkobalak.smarthome.model;

import com.golovkobalak.smarthome.data.Measure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
@PropertySource("facade.properties")
public class FacadeRestService {

    private RestTemplate restTemplate;
    @Value(value = "${facade.url}")
    private String url;

    public FacadeRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Measure postMeasure(Measure measure) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept((Collections.singletonList(MediaType.APPLICATION_JSON)));
        HttpEntity<Measure> httpEntity = new HttpEntity<>(measure, headers);
        return restTemplate.postForObject(url, httpEntity, Measure.class);
    }

}
