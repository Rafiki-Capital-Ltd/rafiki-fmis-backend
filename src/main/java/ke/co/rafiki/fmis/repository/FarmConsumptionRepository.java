package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import ke.co.rafiki.fmis.domain.FarmCrop;
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
public interface FarmConsumptionRepository extends JpaRepository<FarmConsumption, UUID> {
    Page<FarmConsumption> findByFarm(Farm farm, Pageable pageable);

    List<FarmConsumption> findByFarm(Farm farm);

    Page<FarmCrop> findByOwner(User user, Pageable pageable);

    List<FarmCrop> findByOwner(User user);

    @Transactional
    @Query("SELECT fac FROM FarmConsumption AS fac WHERE fac.owner = :owner AND fac.farm = :farm")
    Page<FarmCrop> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Transactional
    @Query("SELECT fac FROM FarmConsumption AS fac WHERE fac.owner = :owner AND fac.farm = :farm")
    List<FarmCrop> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmConsumption fac SET fac.owner = null WHERE fac.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmConsumption fac SET fac.farm = null WHERE fac.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
