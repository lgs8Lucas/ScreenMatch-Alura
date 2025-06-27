package br.com.alura.screenmatch.services;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class GeminiQuery {
    private static  String apiKey = System.getenv("GEMINI_KEY");
    private static String ENDPOINT = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + apiKey;

    public static String getData(String texto) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson(); // Crie uma instância do Gson
        // Corpo da requisição no formato JSON
        String json = """
        {
           "contents": [
             {
               "parts": [
                 {"text": "Traduza para portugês o texto: %s"}
               ]
             }
           ]
         }
    """.formatted(texto);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json
                );

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = "";
                if (response.body() != null) {
                    errorBody = response.body().string();
                }
                System.err.println("--- ERRO DA API GEMINI ---");
                System.err.println("Código HTTP: " + response.code());
                System.err.println("URL da Requisição: " + request.url());
                System.err.println("Corpo da Requisição Enviado: " + json);
                System.err.println("Mensagem do Servidor: " + errorBody);
                System.err.println("-------------------------");
                return "Erro: " + response.code() + " - Detalhes: " + errorBody;
            }

            // AQUI ESTÁ A MUDANÇA PRINCIPAL!
            // 1. Obtém o corpo da resposta como string (o JSON completo)
            String responseJson = response.body().string();

            // 2. Converte a string JSON em um objeto JsonObject usando Gson
            JsonObject jsonResponse = gson.fromJson(responseJson, JsonObject.class);

            // 3. Navega no JSON para encontrar o campo 'text'
            // A estrutura é: "candidates" -> primeiro elemento -> "content" -> "parts" -> primeiro elemento -> "text"
            String extractedText = jsonResponse.getAsJsonArray("candidates") // Pega o array "candidates"
                    .get(0).getAsJsonObject() // Pega o primeiro elemento do array (o primeiro candidato)
                    .getAsJsonObject("content") // Pega o objeto "content"
                    .getAsJsonArray("parts") // Pega o array "parts"
                    .get(0).getAsJsonObject() // Pega o primeiro elemento do array "parts"
                    .get("text").getAsString(); // Pega o valor da chave "text" como String

            return extractedText; // Retorna apenas o texto extraído
        }
    }
}
