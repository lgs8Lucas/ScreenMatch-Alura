package br.com.alura.screenmatch.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> tclass);
}
