package com.xenialsoft.api.exception;

import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xenialsoft.api.common.data.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messages;

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {

        String codeOrMessage = ex.getMessage();
        String code = codeOrMessage;
        String message = messages.getMessage(ex.getMessage(), ex.getArgs(), request.getLocale());
        if (StringUtils.equals(codeOrMessage, message)) {
            code = "";
        }

//        // 오류 로그 쌓는 방법
//        log.error("########## 오류입니다. {}", message);
//        ex.printStackTrace();

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();

        // @formatter:off
        return handleExceptionInternal(ex,
                body,
                headers,
                HttpStatus.BAD_REQUEST,
                request);
        // @formatter:on
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException ex,
            WebRequest request) {

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Missing required request header: " + ex.getHeaderName())
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();

        // @formatter:off
        return handleExceptionInternal(ex,
                body,
                headers,
                HttpStatus.BAD_REQUEST,
                request);
        // @formatter:on
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();

        // @formatter:off
        return handleExceptionInternal(ex,
                body,
                headers,
                HttpStatus.FORBIDDEN,
                request);
        // @formatter:on
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException ex, WebRequest request) {

        String codeOrMessage = ex.getMessage();
        String code = codeOrMessage;
        String message = messages.getMessage(ex.getMessage(), ex.getArgs(), request.getLocale());
        if (StringUtils.equals(codeOrMessage, message)) {
            code = "";
        }

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();

        // @formatter:off
        return handleExceptionInternal(ex,
                body,
                headers,
                HttpStatus.NOT_FOUND,
                request);
        // @formatter:on
    }

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<?> handleServiceException(ServiceException ex, WebRequest request) {

        String codeOrMessage = ex.getMessage();
        String code = codeOrMessage;
        String message = messages.getMessage(ex.getMessage(), ex.getArgs(), request.getLocale());
        if (StringUtils.equals(codeOrMessage, message)) {
            code = "";
        }

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();

        // @formatter:off
        return handleExceptionInternal(ex,
                body,
                headers,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
        // @formatter:on
    }

    /**
     * {@code AuthenticationException}이 발생했을 때 호출되는 예외 처리 메서드입니다.
     * <p>
     * 이 메서드는 인증 과정에서 실패가 발생했을 때 호출되며, 클라이언트에게 적절한 HTTP 응답을 반환합니다. 응답에는 인증 실패 메시지 키와
     * 함께 HTTP 상태 코드 401 (Unauthorized)이 포함됩니다.
     * </p>
     *
     * @param ex      발생한 {@link AuthenticationException} 예외 객체
     * @param request 클라이언트 요청에 대한 정보 (optional)
     * @param locale  클라이언트 요청 언어
     * @return {@link ResponseEntity} 응답 본문에 인증 실패 메시지와 함께 HTTP 상태 코드 401을 반환
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request,
            Locale locale) {

        String code = "error.authentication.failed";
        String message = messages.getMessage(code, null, locale);

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
        // @formatter:on

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // @formatter:off
        // 필드별 에러 메시지를 추출하여 Map에 저장
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("\r\n"));
        // @formatter:on

        // @formatter:off
        ApiResponse body = ApiResponse.builder()
                .code(((HttpStatus) status).name())
                .message(message)
                .build();
        // @formatter:on

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {

        if (log.isDebugEnabled()) {
            ex.printStackTrace();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
