package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByType(RoleType type);
}
