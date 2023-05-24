package ke.co.rafiki.fmis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class MultiPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private final Map<String, PermissionEvaluator> permissionEvaluators;

    public MultiPermissionEvaluator(Map<String, PermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String targetType = getTargetType(targetDomainObject.getClass().getSimpleName());
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetType);
        if (permissionEvaluator == null)
            throw new IllegalArgumentException("No permission evaluator found for targetType=" + targetType + permissionEvaluators);
        return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        String _targetType = getTargetType(targetType);
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(_targetType);
        if (permissionEvaluator == null)
            throw new IllegalArgumentException("No permission evaluator found for targetType=" + _targetType + permissionEvaluators);
        return permissionEvaluator.hasPermission(authentication, targetId, targetType, permission);
    }

    private String getTargetType(String targetDomainObjectName) {
        return targetDomainObjectName.substring(0, 1).toLowerCase()
                + targetDomainObjectName.substring(1)
                + "PermissionEvaluator";
    }
}
