package com.xenialsoft.api.exception;

import com.xenialsoft.api.config.ApplicationCoreVersion;

import lombok.Getter;

@Getter
public class ServiceException extends CustomRuntimeException {

    private static final long serialVersionUID = ApplicationCoreVersion.SERIAL_VERSION_UID;

    public ServiceException(String message, Object... args) {
        super(message, args);
    }

}
