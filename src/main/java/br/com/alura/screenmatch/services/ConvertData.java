package br.com.alura.screenmatch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper mapper = new ObjectMapper(); // Objeto do Jackson que faz a conversão

    @Override
    public <T> T getData(String json, Class<T> tclass) {
        try {
            return mapper.readValue(json, tclass); // converte o json para a classe escolhida
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
