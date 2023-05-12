package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmActivityLogRepository extends JpaRepository<FarmActivityLog, UUID> {
    Optional<FarmActivityLog> findByFarm(Farm farm);
}
