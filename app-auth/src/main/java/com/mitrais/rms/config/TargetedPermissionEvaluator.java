package com.mitrais.rms.config;

import org.springframework.security.access.PermissionEvaluator;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {
    String getTargetType();
}
