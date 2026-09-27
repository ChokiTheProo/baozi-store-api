package br.com.uninter.baozistore.exception;

/** Lancada quando um id nao existe no banco (vira HTTP 404). */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + " com id " + id + " nao encontrado");
    }
}
