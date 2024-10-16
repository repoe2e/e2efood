package br.com.e2efood.e2efood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Modelo que representa um restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do restaurante", example = "1")
    private Long id;

    @Schema(description = "Nome do restaurante", example = "Restaurante Exemplo", required = true)
    private String nome;

    @Schema(description = "Categoria do restaurante", example = "Italiana", required = true)
    private String categoria;

    @Schema(description = "Taxa de entrega do restaurante", example = "5.0", required = true)
    private Double taxaEntrega;

    @Schema(description = "Tempo estimado de entrega", example = "30-40 minutos", required = true)
    private String tempoEstimado;

    @Schema(description = "Endereço do restaurante", example = "Rua Exemplo, 123", required = true)
    private String endereco;

    @Schema(description = "Cidade onde o restaurante está localizado", example = "São Paulo", required = true)
    private String cidade;

    // Construtor com parâmetros
    public Restaurante(String nome, String categoria, Double taxaEntrega, String tempoEstimado, String endereco, String cidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.taxaEntrega = taxaEntrega;
        this.tempoEstimado = tempoEstimado;
        this.endereco = endereco;
        this.cidade = cidade;
    }

    // Construtor padrão (necessário para JPA)
    public Restaurante() {}

    // Getters and Setters
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(Double taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public String getTempoEstimado() { return tempoEstimado; }
    public void setTempoEstimado(String tempoEstimado) { this.tempoEstimado = tempoEstimado; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
