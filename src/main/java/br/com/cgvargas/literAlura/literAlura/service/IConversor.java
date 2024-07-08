package br.com.cgvargas.literAlura.literAlura.service;

import br.com.cgvargas.literAlura.literAlura.model.DadosAutor;
import br.com.cgvargas.literAlura.literAlura.model.DadosLivro;

import java.util.List;

public interface IConversor {
    <T> T getData(String json, Class<T> classe);
    <T> List<T> getListaData(String json, Class<T> classe);
    DadosAutor getAutorData(DadosLivro dadosLivro);
}
