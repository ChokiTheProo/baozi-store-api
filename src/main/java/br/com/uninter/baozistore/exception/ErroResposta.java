package br.com.uninter.baozistore.exception;

import java.time.LocalDateTime;
import java.util.List;

/** Formato JSON padrao das respostas de erro da API. */
public record ErroResposta(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<String> detalhes) {
}
