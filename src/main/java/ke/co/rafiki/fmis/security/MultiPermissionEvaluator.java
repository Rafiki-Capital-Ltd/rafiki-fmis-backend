package ke.co.rafiki.fmis.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class MultiPermissionEvaluator implements PermissionEvaluator {

    private final Map<String, PermissionEvaluator> permissionEvaluators;

    public MultiPermissionEvaluator(Map<String, PermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String targetType = targetDomainObject.getClass().getSimpleName();
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetType);
        if (permissionEvaluator == null)
            throw new IllegalArgumentException("No permission evaluator found for targetType=" + targetType);
        return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetType);
        if (permissionEvaluator == null)
            throw new IllegalArgumentException("No permission evaluator found for targetType=" + targetType);
        return permissionEvaluator.hasPermission(authentication, targetId, targetType, permission);
    }
}
