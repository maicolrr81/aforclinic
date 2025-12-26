package com.xenialsoft.api.exception;

import com.xenialsoft.api.config.ApplicationCoreVersion;

import lombok.Getter;

@Getter
public class NotFoundException extends CustomRuntimeException {

    private static final long serialVersionUID = ApplicationCoreVersion.SERIAL_VERSION_UID;

    public NotFoundException() {
        super("데이터가 존재하지 않습니다.");
    }
    
    public NotFoundException(String message, Object... args) {
        super(message, args);
    }

}
