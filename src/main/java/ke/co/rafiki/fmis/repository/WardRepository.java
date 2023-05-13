package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WardRepository extends JpaRepository<Ward, UUID> {
    Optional<Ward> findByName(String name);
}
