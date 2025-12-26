package com.xenialsoft.api.exception;

import com.xenialsoft.api.config.ApplicationCoreVersion;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private static final long serialVersionUID = ApplicationCoreVersion.SERIAL_VERSION_UID;

    private Object[] args;

    public CustomRuntimeException(String message, Object... args) {
        super(message);
        this.args = args;
    }

}
