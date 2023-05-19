package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmActivityLogRepository extends JpaRepository<FarmActivityLog, UUID> {

    Page<FarmActivityLog> findByFarm(Farm farm, Pageable pageable);

    List<FarmActivityLog> findByFarm(Farm farm);

    Page<FarmActivityLog> findByOwner(User owner, Pageable pageable);

    List<FarmActivityLog> findByOwner(User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmActivityLog fal SET fal.owner = null WHERE fal.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmActivityLog fal SET fal.farm = null WHERE fal.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
