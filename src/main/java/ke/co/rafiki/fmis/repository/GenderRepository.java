package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenderRepository extends JpaRepository<Gender, UUID> {
    Optional<Gender> findByName(String name);
}
