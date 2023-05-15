package ke.co.rafiki.fmis.security;

import ke.co.rafiki.fmis.domain.BaseEntityAuditOwned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.UUID;

public class CustomPermissionEvaluator<E extends BaseEntityAuditOwned, R extends JpaRepository<E, UUID>> implements PermissionEvaluator {

    private final R repository;

    public CustomPermissionEvaluator(R repository) {
        this.repository = repository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (!(targetDomainObject instanceof BaseEntityAuditOwned))
            throw new IllegalArgumentException("Could not cast targetDomainObject to BaseEntityOwned");

        E subject = (E) targetDomainObject;

        boolean isAdmin = authentication.getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals(permission));

        return isAdmin || subject.getOwner().getEmail().equals(authentication.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        E subject = repository.findById((UUID) targetId).orElseThrow(IllegalArgumentException::new);

        boolean isAdmin = authentication.getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals(permission));

        return isAdmin || subject.getOwner().getEmail().equals(authentication.getName());
    }

    private E convertInstanceOfObject(Object o, Class<E> c) {
        return c.isInstance(o) ? c.cast(o) : null;
    }
}
