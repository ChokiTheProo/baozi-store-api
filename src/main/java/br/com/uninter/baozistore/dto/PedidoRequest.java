package br.com.uninter.baozistore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Corpo JSON usado para criar/atualizar um pedido.
 * Exemplo: { "clienteId": 1, "produtoId": 1, "quantidade": 10 }
 */
public record PedidoRequest(
        @NotNull(message = "O clienteId e obrigatorio") Long clienteId,
        @NotNull(message = "O produtoId e obrigatorio") Long produtoId,
        @NotNull(message = "A quantidade e obrigatoria")
        @Min(value = 1, message = "A quantidade deve ser no minimo 1") Integer quantidade) {
}
