package in.digiborn.s02openaiprompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final ChatClient chatClient;

    public MessagesController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/complex")
    public String getJoke() {
//        var systemMessage = new SystemMessage("Your primary function is to tell dad jokes. If someone asks you any other types of jokes, please tell them you only know dad jokes.");
//        var userMessage = new UserMessage("Tell me a universe joke");
//
//        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
//        return chatModel.call(prompt).getResult().getOutput().getText();

        return chatClient.prompt()
            .system("Your primary function is to tell dad jokes. If someone asks you any other types of jokes, please tell them you only know dad jokes.")
            .user("Tell me a universe joke")
            .call()
            .content();
    }

    @GetMapping("/simple")
    public String getJoke1() {
//        var userMessage = new UserMessage("Tell me a dad joke");
//
//        Prompt prompt = new Prompt(userMessage);
//        return chatModel.call(prompt).getResult().getOutput().getText();

        return chatClient.prompt()
            .user("Tell me a dad joke")
            .call()
            .content();
    }
}
