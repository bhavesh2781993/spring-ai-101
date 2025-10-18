package in.digiborn.ai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatModel chatModel;

    public ChatController(final ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/dad-jokes")
    public String getDadJokes(@RequestParam(name = "message", defaultValue = "tell me a dad joke") String message) {
        return chatModel.call(message);
    }

}
