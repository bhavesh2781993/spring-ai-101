package in.digiborn.s08ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.digiborn.s08ollama.weather.WeatherConfigProperties;
import in.digiborn.s08ollama.weather.WeatherService;

@RestController
public class CityController {

    private final ChatClient chatClient;
    private final WeatherConfigProperties weatherConfigProperties;

    public CityController(ChatClient.Builder builder,
                          WeatherConfigProperties weatherConfigProperties) {
        this.chatClient = builder
            .defaultSystem("You are a useful AI assistant answering questions about city around the world!")
            .build();
        this.weatherConfigProperties = weatherConfigProperties;
    }

    @GetMapping("/cities")
    public String cityWeather(@RequestParam(value = "message") String message) {
        ToolCallback toolCallback = FunctionToolCallback
            .builder("currentWeather", new WeatherService(weatherConfigProperties))
            .description("Get the weather in location")
            .inputType(WeatherService.Request.class)
            .build();

        return chatClient.prompt()
            .user(message)
            .toolCallbacks(toolCallback)
            .call()
            .content();
    }

}
