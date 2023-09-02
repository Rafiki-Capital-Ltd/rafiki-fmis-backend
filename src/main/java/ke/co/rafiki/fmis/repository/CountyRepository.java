package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountyRepository extends JpaRepository<County, Integer> {
    Optional<County> findByName(String name);
}
