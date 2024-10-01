package br.com.e2efood.e2efood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e2efood.e2efood.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	List<Restaurante> findByCidade(String cidade);
}