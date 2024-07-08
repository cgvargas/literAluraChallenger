package br.com.cgvargas.literAlura.literAlura.service;

import br.com.cgvargas.literAlura.literAlura.model.DadosAutor;
import br.com.cgvargas.literAlura.literAlura.model.DadosLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Conversor implements IConversor {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> classe) {
        T resultado = null;
        try {
            JsonNode node = mapper.readTree(json);
            if (classe == DadosLivro.class) {
                var s = node.get("results").get(0);
                resultado = mapper.treeToValue(s, classe);
            } else if (classe == DadosAutor.class) {
                var s = node.get("results").get(0).get("authors").get(0);
                resultado = mapper.treeToValue(s, classe);
            } else {
                resultado = mapper.readValue(json, classe);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public <T> List<T> getListaData(String json, Class<T> classe) {
        List<T> resultado = new ArrayList<>();
        try {
            JsonNode node = mapper.readTree(json);
            JsonNode results = node.get("results");

            for (JsonNode jsonNode : results) {
                T item = mapper.treeToValue(jsonNode, classe);
                resultado.add(item);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public DadosAutor getAutorData(DadosLivro dadosLivro) {
        return dadosLivro.autores().get(0);
    }
}
