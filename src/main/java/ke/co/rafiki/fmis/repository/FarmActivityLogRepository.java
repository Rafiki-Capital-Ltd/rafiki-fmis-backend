package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmActivityLogRepository extends JpaRepository<FarmActivityLog, UUID> {
    Optional<FarmActivityLog> findByFarm(Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmActivityLog fal SET fal.owner = null WHERE fal.owner = :owner")
    void dissasociateFromOwner(@Param("owner") User owner);
}
