package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivity;
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
import java.util.UUID;

@Repository
public interface FarmActivityRepository extends JpaRepository<FarmActivity, UUID> {
    Page<FarmActivity> findByFarmActivityLog(FarmActivityLog farmActivityLog, Pageable pageable);

    List<FarmActivity> findByFarmActivityLog(FarmActivityLog farmActivityLog);

    Page<FarmActivity> findByOwner(User owner, Pageable pageable);

    List<FarmActivity> findByOwner(User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmActivity fac SET fac.owner = null WHERE fac.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmActivity fac SET fac.farmActivityLog = null WHERE fac.farmActivityLog = :farmActivityLog")
    void disassociateFromFarm(@Param("farmActivityLog") FarmActivityLog farmActivityLog);
}
