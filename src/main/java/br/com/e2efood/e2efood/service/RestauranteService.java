package br.com.e2efood.e2efood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.e2efood.e2efood.model.Restaurante;
import br.com.e2efood.e2efood.repository.RestauranteRepository;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> buscarPorCidade(String cidade) {
        return restauranteRepository.findByCidade(cidade);
    }
}
