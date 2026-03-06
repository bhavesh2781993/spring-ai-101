package in.digiborn.s08ollama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import in.digiborn.s08ollama.weather.WeatherConfigProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class S08OllamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(S08OllamaApplication.class, args);
    }

}
