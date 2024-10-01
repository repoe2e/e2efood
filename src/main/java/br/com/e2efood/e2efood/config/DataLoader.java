package br.com.e2efood.e2efood.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.e2efood.e2efood.model.Restaurante;
import br.com.e2efood.e2efood.repository.RestauranteRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DataLoader {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @PostConstruct
    public void loadData() {
        if (restauranteRepository.count() == 0) {
            List<Restaurante> restaurantes = List.of(
                new Restaurante("Restaurante A", "Italiana", 5.00, "30-40 min", "Rua A, 123", "São Paulo"),
                new Restaurante("Restaurante B", "Japonesa", 8.00, "40-50 min", "Rua B, 456", "São Paulo"),
                new Restaurante("Restaurante Z", "Brasileira", 3.50, "20-30 min", "Rua Z, 789", "Rio de Janeiro")
            );
            restauranteRepository.saveAll(restaurantes);
        }
    }
}
