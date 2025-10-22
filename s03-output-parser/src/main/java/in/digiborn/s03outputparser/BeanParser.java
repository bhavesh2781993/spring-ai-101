package in.digiborn.s03outputparser;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BeanParser {

    private final ChatModel chatModel;

    public BeanParser(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/authors/{author}/books")
    public Author getAuthorBooks(@PathVariable("author") String author) {
        String message = """
            Generate a list of books written by author {author}. Include only books that you are confirm that is written by the author.
            {format}
            """;

        BeanOutputConverter<Author> converter = new BeanOutputConverter<>(Author.class);

        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("author", author, "format", converter.getFormat()));
        Generation generation = chatModel.call(prompt).getResult();
        return converter.convert(generation.getOutput().getText());
    }

}
