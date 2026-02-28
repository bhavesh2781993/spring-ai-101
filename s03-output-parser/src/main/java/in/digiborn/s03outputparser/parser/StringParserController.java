package in.digiborn.s03outputparser.parser;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/string-parser")
public class StringParserController {

    private final ChatModel chatModel;

    public StringParserController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/songs")
    public List<String> getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Taylor Swift") String artist) {

        var message = """
            Get list of top 10 songs by {artist}. If you don't know, just don't provide any result.
            {format}
            """;

        ListOutputConverter listOutputConverter = new ListOutputConverter();
        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(
            Map.of(
            "artist", artist,
            "format", listOutputConverter.getFormat()
            ));

        Generation generation = chatModel.call(prompt)
            .getResult();
        return listOutputConverter.convert(generation.getOutput().getText());
    }
}
