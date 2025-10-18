package in.digiborn.s02openaiprompt;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompts")
public class PromptController {

    private final ChatModel chatModel;

    public PromptController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/dad-jokes")
    public String jokes() {
        return chatModel.call(new Prompt("Tell me a dad joke")).getResult().getOutput().getText();
    }
}
