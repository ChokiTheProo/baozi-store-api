package br.com.uninter.baozistore.exception;

import org.springframework.http.HttpStatus;

/** Lancada quando uma regra da loja e violada (ex.: produto sem estoque). */
public class RegraNegocioException extends RuntimeException {

    private final HttpStatus status;

    public RegraNegocioException(HttpStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
