package com.weather.weather.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "weather")
public class WeatherController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("temp")
    public String getTemp() {
        WebClient webClient = webClientBuilder.build();
        var response1=   webClient.get()
                        .uri("http://api.weatherapi.com/v1/current.json?key=fe2f60d91ce7455484f135303242007&q=Milwaukee&aqi=no")
                        .retrieve().bodyToMono(String.class).block();
        var response2=   webClient.get()
                .uri("http://api.weatherapi.com/v1/current.json?key=fe2f60d91ce7455484f135303242007&q=Czerwonak&aqi=no")
                .retrieve().bodyToMono(String.class).block();
        var response3=   webClient.get()
                .uri("http://api.weatherapi.com/v1/current.json?key=fe2f60d91ce7455484f135303242007&q=Siberia&aqi=no")
                .retrieve().bodyToMono(String.class).block();

        return "Millwaukee " + extractTemperature(response1) +
                "<br>Czerwonak " + extractTemperature(response2) +
                "<br>Siberia " + extractTemperature(response3);
    }
    private String extractTemperature(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.path("current").path("temp_c").asText() + " °C";
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
