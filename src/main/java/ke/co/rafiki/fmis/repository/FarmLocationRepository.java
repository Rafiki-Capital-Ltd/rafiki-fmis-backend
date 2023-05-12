package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.FarmLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FarmLocationRepository extends JpaRepository<FarmLocation, UUID> {
}
