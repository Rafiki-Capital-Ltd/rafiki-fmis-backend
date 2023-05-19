package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmCrop;
import ke.co.rafiki.fmis.domain.FarmProduction;
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
public interface FarmProductionRepository extends JpaRepository<FarmProduction, UUID> {
    Page<FarmProduction> findByFarm(Farm farm, Pageable pageable);

    List<FarmProduction> findByFarm(Farm farm);

    Page<FarmCrop> findByOwner(User user, Pageable pageable);

    List<FarmCrop> findByOwner(User user);

    @Transactional
    @Query("SELECT fap FROM FarmProduction AS fap WHERE fap.owner = :owner AND fap.farm = :farm")
    Page<FarmCrop> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Transactional
    @Query("SELECT fap FROM FarmProduction AS fap WHERE fap.owner = :owner AND fap.farm = :farm")
    List<FarmCrop> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmProduction fap SET fap.owner = null WHERE fap.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmProduction fap SET fap.farm = null WHERE fap.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
