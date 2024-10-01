package br.com.e2efood.e2efood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "Buscar restaurantes por cidade", description = "Retorna uma lista de restaurantes que estão localizados em uma cidade específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurante.class, example = "[{\"id\": 1, \"nome\": \"Restaurante Exemplo\", \"categoria\": \"Italiana\", \"taxaEntrega\": 5.0, \"tempoEstimado\": \"30-40 minutos\", \"endereco\": \"Rua Exemplo, 123\", \"cidade\": \"São Paulo\"}]"))),
            @ApiResponse(responseCode = "204", description = "Nenhum restaurante encontrado para a cidade solicitada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parâmetros de entrada inválidos. A cidade deve ser informada ou é inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") })
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

    public static class ErrorResponse {
        @Schema(description = "Mensagem de erro detalhada", example = "Parâmetros de entrada inválidos: Cidade não pode estar vazia.")
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
}
