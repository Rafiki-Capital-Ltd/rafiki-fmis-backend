package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WardRepository extends JpaRepository<Ward, UUID> {
    Optional<Ward> findByName(String name);
}
