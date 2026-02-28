package in.digiborn.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(final ChatClient.Builder builder) {
        this.chatClient = builder
            .build();
    }

    @GetMapping("/dad-jokes")
    public String getDadJokes(@RequestParam(name = "message", defaultValue = "tell me a dad joke") String message) {
        return chatClient
            .prompt(message)
            .call()
            .content();
    }

}
