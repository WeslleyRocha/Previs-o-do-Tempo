package org.example.previsaotempo.exception;

/**
 * Exceção genérica para erros ocorridos durante a comunicação com uma API externa.
 */
public class ApiClientException extends Exception {
    public ApiClientException(String message) {
        super(message);
    }

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
