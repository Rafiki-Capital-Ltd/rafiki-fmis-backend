package ke.co.rafiki.fmis.misc;

import ke.co.rafiki.fmis.domain.enums.RoleType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.Normalizer;

public final class HelperMethods {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static PageRequest getPageRequest(int page, int size, String sort, String sortDirection) {
        return PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
    }

    public static boolean isAuthorized(RoleType role) {
        Authentication authentication = getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(role.toString()));
    }
}
