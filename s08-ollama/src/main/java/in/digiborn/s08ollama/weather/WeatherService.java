package in.digiborn.s08ollama.weather;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    private final WeatherConfigProperties weatherConfigProperties;
    private final RestClient restClient;

    private final Logger log = LoggerFactory.getLogger(WeatherService.class);

    public WeatherService(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
        this.restClient = RestClient.builder()
            .baseUrl(weatherConfigProperties.url())
            .build();
    }

    @Override
    public Response apply(Request request) {
        log.info("Weather Request: {}",request);
        Response response = restClient.get()
            .uri("/current.json?key={key}&q={q}", weatherConfigProperties.key(), request.city())
            .retrieve()
            .body(Response.class);
        log.info("Weather API Response: {}", response);
        return response;
    }

    // mapping the response of the Weather API to records. I only mapped the information I was interested in.
    public record Request(String city) {}
    public record Response(Location location,Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon){}
    public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {}
    public record Condition(String text){}

}
