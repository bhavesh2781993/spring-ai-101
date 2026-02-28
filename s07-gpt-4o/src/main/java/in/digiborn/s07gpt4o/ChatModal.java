package in.digiborn.s07gpt4o;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatModal {

    private final ChatModel chatModel;

    public ChatModal(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/dad-jokes")
    public String getDadJokes(@RequestParam(name = "topic", defaultValue = "dog") String topic) {
        PromptTemplate promptTemplate = new PromptTemplate("Tell me dad joke about {topic}");
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        return ChatClient.create(chatModel)
            .prompt(prompt)
            .call()
            .content();
    }

}
