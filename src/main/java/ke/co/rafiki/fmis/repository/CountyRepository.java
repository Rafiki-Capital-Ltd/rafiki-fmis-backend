package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountyRepository extends JpaRepository<County, UUID> {
    Optional<County> findByName(String name);
}
