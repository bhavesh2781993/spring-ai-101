package in.digiborn.s03outputparser;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MapParser {

    private final ChatModel chatModel;

    public MapParser(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/authors/{author}")
    public Map<String, Object> getAuthorSocialMediaHandles(@PathVariable("author") String author) {
        String message = """
            Generate a list of links for the author {author}. Include author name as key and any social media links as object
            {format}
            """;

        MapOutputConverter converter = new MapOutputConverter();

        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("author", author, "format", converter.getFormat()));
        Generation generation = chatModel.call(prompt).getResult();
        return converter.convert(generation.getOutput().getText());
    }

}
