package org.example.previsaotempo.exception;

/**
 * Exceção lançada quando um recurso não é encontrado pela API externa (ex: cidade inexistente).
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
