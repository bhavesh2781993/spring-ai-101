package in.digiborn.s07gpt4o;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageModal {

    private final ChatModel chatModel;

    public ImageModal(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/image-describe")
    public String describeImage() {
        ClassPathResource classPathResource = new ClassPathResource("/images/sincerely-media-2UlZpdNzn2w-unsplash.jpg");

        UserMessage userMessage = UserMessage.builder()
            .text("Can you please explain what you see in the following image?")
            .media(new Media(MimeTypeUtils.IMAGE_JPEG, classPathResource))
            .build();

        Prompt prompt = new Prompt(List.of(userMessage));
        return ChatClient.create(chatModel)
            .prompt(prompt)
            .call()
            .content();
    }

    @GetMapping("/code-describe")
    public String describeCode() {
        ClassPathResource classPathResource = new ClassPathResource("/images/java-open-ai.png");

        UserMessage userMessage = UserMessage.builder()
            .text("The following is a screenshot of some code. Can you do your best to provide a description of what this code does?")
            .media(new Media(MimeTypeUtils.IMAGE_PNG, classPathResource))
            .build();

        Prompt prompt = new Prompt(List.of(userMessage));
        return ChatClient.create(chatModel)
            .prompt(prompt)
            .call()
            .content();
    }

    @GetMapping("/image-to-code")
    public String imageToCode() {
        ClassPathResource classPathResource = new ClassPathResource("/images/java-open-ai.png");

        UserMessage userMessage = UserMessage.builder()
            .text("The following is a screenshot of some code. Can you translate this from the image into text?")
            .media(new Media(MimeTypeUtils.IMAGE_PNG, classPathResource))
            .build();

        Prompt prompt = new Prompt(List.of(userMessage));
        return ChatClient.create(chatModel)
            .prompt(prompt)
            .call()
            .content();
    }

}
