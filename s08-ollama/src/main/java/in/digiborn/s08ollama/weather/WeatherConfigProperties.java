package in.digiborn.s08ollama.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather.api")
public record WeatherConfigProperties(String key, String url) {
}
