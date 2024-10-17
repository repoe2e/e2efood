package br.com.e2efood.e2efood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2efood.e2efood.model.Restaurante;
import br.com.e2efood.e2efood.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "API para gerenciamento de restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // Endpoint para buscar restaurantes por cidade (GET)
    @Operation(summary = "Buscar restaurantes por cidade", description = "Retorna uma lista de restaurantes que estão localizados em uma cidade específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurante.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum restaurante encontrado para a cidade solicitada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parâmetros de entrada inválidos. A cidade deve ser informada ou é inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/cidade")
    public ResponseEntity<?> buscarRestaurantesPorCidade(
            @Parameter(description = "Nome da cidade para buscar restaurantes", example = "São Paulo", required = true) @RequestParam(required = false) String cidade) {

        try {
            if (cidade == null || cidade.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("A cidade deve ser informada."));
            }

            if (!cidade.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("A cidade informada é inválida. Somente letras são permitidas."));
            }

            List<Restaurante> restaurantes = restauranteService.buscarPorCidade(cidade);
            if (restaurantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(restaurantes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Parâmetros de entrada inválidos: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor. Tente novamente mais tarde."));
        }
    }

    // Endpoint para cadastrar um novo restaurante (POST)
    @Operation(summary = "Cadastrar novo restaurante", description = "Adiciona um novo restaurante ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurante.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> cadastrarRestaurante(@RequestBody Restaurante restaurante) {
        try {
            if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("O nome do restaurante é obrigatório."));
            }

            Restaurante novoRestaurante = restauranteService.salvarRestaurante(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor. Tente novamente mais tarde."));
        }
    }

    // Classe para retornar erros no formato JSON
    public static class ErrorResponse {
        private String mensagem;

        public ErrorResponse(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
    
 // Endpoint para atualizar um restaurante existente (PUT)
    @Operation(summary = "Atualizar restaurante", description = "Atualiza as informações de um restaurante existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurante.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRestaurante(
            @Parameter(description = "ID do restaurante a ser atualizado", required = true) @PathVariable Long id,
            @RequestBody Restaurante restauranteAtualizado) {
        
        try {
            Restaurante restauranteExistente = restauranteService.buscarRestaurantePorId(id);

            if (restauranteExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Restaurante não encontrado."));
            }

            restauranteAtualizado.setId(id);  // Certifique-se de que o ID do restaurante não seja alterado
            Restaurante restauranteAtualizadoSalvo = restauranteService.atualizarRestaurante(restauranteAtualizado);
            return ResponseEntity.ok(restauranteAtualizadoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor. Tente novamente mais tarde."));
        }
    }
    
 // Endpoint para deletar um restaurante (DELETE)
    @Operation(summary = "Deletar restaurante", description = "Deleta um restaurante existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarRestaurante(
            @Parameter(description = "ID do restaurante a ser deletado", required = true) @PathVariable Long id) {
        try {
            Restaurante restauranteExistente = restauranteService.buscarRestaurantePorId(id);

            if (restauranteExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Restaurante não encontrado."));
            }

            restauranteService.deletarRestaurante(id);
            return ResponseEntity.ok().body(new ErrorResponse("Restaurante deletado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor. Tente novamente mais tarde."));
        }
    }


}
