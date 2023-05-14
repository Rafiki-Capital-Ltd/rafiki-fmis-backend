package ke.co.rafiki.fmis.authorization;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.repository.FarmRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("unused")
@Component("farmPermEval")
public class FarmPermissionEvaluator implements PermissionEvaluator {

    private final FarmRepository farmRepository;

    public FarmPermissionEvaluator(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Farm farm = (Farm) targetDomainObject;
        String _permission = (String) permission;

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(_permission));

        return isAuthorized || farm.getOwner().getEmail().equals(authentication.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UUID farmId = (UUID) targetId;
        Farm farm = farmRepository.findById(farmId).orElseThrow();
        String _permission = (String) permission;

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(_permission));

        return isAuthorized || farm.getOwner().getEmail().equals(authentication.getName());
    }
}
