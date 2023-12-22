package com.similar.products.domain.exceptions;

public class SimilarProductsException extends RuntimeException {
    private final int httpStatusCode;

    public SimilarProductsException(int httpStatusCode) {
        super();
        this.httpStatusCode = httpStatusCode;
    }

    public SimilarProductsException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }


    public SimilarProductsException(String message, Throwable cause, int httpStatusCode) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }
}
