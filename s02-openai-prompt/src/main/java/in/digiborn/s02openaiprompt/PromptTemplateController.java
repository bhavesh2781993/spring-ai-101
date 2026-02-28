package in.digiborn.s02openaiprompt;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/prompt-templates")
public class PromptTemplateController {

    private final ChatModel chatModel;

    @Value("classpath:/prompts/youtube.st")
    private Resource youtubePromptTemplate;

    public PromptTemplateController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/message")
    public String getPopularYoutubersByGenre(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        var message = """
            list of 10 popular youtube content creators in the {genre} with their youtube channel names and subscriber counts.
            If you don't know the answer, just say I don't know.
            """;

        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));

        return chatModel.call(prompt)
            .getResult()
            .getOutput()
            .getText();
    }

    @GetMapping("/template")
    public String getPopularYoutubersByGenreUsingTemplate(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        PromptTemplate promptTemplate = new PromptTemplate(youtubePromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));

        return chatModel.call(prompt)
            .getResult()
            .getOutput()
            .getText();
    }
}
