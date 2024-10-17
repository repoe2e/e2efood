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

	// Método para salvar um novo restaurante
	public Restaurante salvarRestaurante(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

	// Método para buscar restaurante por ID
	public Restaurante buscarRestaurantePorId(Long id) {
		return restauranteRepository.findById(id).orElse(null);
	}

	// Método para atualizar um restaurante existente
	public Restaurante atualizarRestaurante(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}
	
	// Método para deletar um restaurante existente
	public void deletarRestaurante(Long id) {
	    restauranteRepository.deleteById(id);
	}


}
