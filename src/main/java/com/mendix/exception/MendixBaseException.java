package com.mendix.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MendixBaseException extends RuntimeException {

    protected String code;

    public MendixBaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public MendixBaseException(String message) {
        super(message);
    }
}
