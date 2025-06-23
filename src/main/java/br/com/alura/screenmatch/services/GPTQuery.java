package br.com.alura.screenmatch.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class GPTQuery {
    public static String getTranslation(String text) {
        OpenAiService service = new OpenAiService(Keys.GPT_KEY); // Chave

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct") // Modelo do chat
                .prompt("traduza para o português o texto: " + text) // Prompt
                .maxTokens(1000) // Tokens maximos
                .temperature(0.7)
                .build();

        var res = service.createCompletion(request);
        return res.getChoices().get(0).getText(); // pega a primeira resposta do chat
    }
}
