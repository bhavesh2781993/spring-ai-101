package in.digiborn.s06ragpgvector;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReferenceDocsController {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/spring-boot-reference.st")
    private Resource sbDocsRefPromptTemplate;

    @Autowired
    public ReferenceDocsController(ChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/spring-docs")
    public String askQuestion(@RequestParam(name = "question") String question) {
        PromptTemplate promptTemplate = new PromptTemplate(sbDocsRefPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", question);
        promptParameters.put("documents", String.join("\n", findSimilarDocuments(question)));

        return chatModel.call(promptTemplate.create(promptParameters))
            .getResult()
            .getOutput()
            .getText();
    }

    private List<String> findSimilarDocuments(String message) {
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.builder().query(message).topK(3).build());
        return similarDocuments.stream().map(Document::getText).toList();
    }

}
