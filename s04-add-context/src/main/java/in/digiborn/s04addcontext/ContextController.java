package in.digiborn.s04addcontext;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stuff-it/olympics")
public class ContextController {

    private final ChatModel chatModel;

    @Value("classpath:/prompts/olympics-2025.st")
    private Resource olympics2025PromptResource;

    @Value("classpath:/docs/olympics-2025.txt")
    private Resource olympics2025Context;

    public ContextController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/2025")
    public String get2025OlympicSports(
        @RequestParam(
            value = "question",
            defaultValue = "What sports are being included in 2025 summer olympics?") String question,
        @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {

        Map<String, Object> map = new HashMap<>();
        if (stuffit) {
            map.put("context", olympics2025Context);
        } else {
            map.put("context", "");
        }
        map.put("question", question);

        PromptTemplate promptTemplate = new PromptTemplate(olympics2025PromptResource);
        Prompt prompt = promptTemplate.create(map);
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }
}
