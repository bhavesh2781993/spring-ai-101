package in.digiborn.s08ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    @GetMapping("/dad-joke")
    public String dadJoke(@RequestParam String jokeAbout) {
        return chatClient.prompt()
            .user(jokeAbout)
            .call()
            .content();
    }

}
