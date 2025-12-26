package com.xenialsoft.api.config.aspect;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.xenialsoft.api.core.auth.model.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Pointcut("@annotation(com.xenialsoft.api.config.aspect.Auditable)")
    public void auditable() {

    }

    @Before("auditable()")
    public void setAuditFields(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                continue;
            }
            if (arg instanceof Collection<?> collection) {
                collection.forEach(this::setAuditableFields);
            } else if (arg.getClass().isArray()) {
                Arrays.stream((Object[]) arg).forEach(this::setAuditableFields);
            } else {
                setAuditableFields(arg);
            }
        }
    }

    private void setAuditableFields(Object entity) {
        setAuditField(entity, CreatedBy.class);
        setAuditField(entity, LastModifiedBy.class);
    }

    private void setAuditField(Object entity, Class<? extends Annotation> annotation) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (field.isAnnotationPresent(annotation)) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, entity, getCurrentAuditor());
            }
        });
    }

    private Object getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "system"; // 기본값
        }
        // @formatter:off
        return Optional.ofNullable(authentication.getPrincipal())
                .filter(CustomUserDetails.class::isInstance)
                .map(CustomUserDetails.class::cast)
                .map(CustomUserDetails::getId)
                .orElseThrow(() -> new IllegalStateException("Cannot resolve auditor"));
        // @formatter:off
    }

}
