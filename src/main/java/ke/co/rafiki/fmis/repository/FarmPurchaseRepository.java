package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmPurchase;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmPurchaseRepository extends JpaRepository<FarmPurchase, UUID> {

    Page<FarmPurchase> findByFarm(Farm farm, Pageable pageable);

    List<FarmPurchase> findByFarm(Farm farm);

    Page<FarmPurchase> findByOwner(User user, Pageable pageable);

    List<FarmPurchase> findByOwner(User user);

    @Query("SELECT fap FROM FarmPurchase AS fap WHERE fap.owner = :owner AND fap.farm = :farm")
    Page<FarmPurchase> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Query("SELECT fap FROM FarmPurchase AS fap WHERE fap.owner = :owner AND fap.farm = :farm")
    List<FarmPurchase> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Query("SELECT SUM(fap.amount) FROM FarmPurchase AS fap")
    Optional<BigDecimal> findTotal();

    @Query("SELECT SUM(fap.amount) FROM FarmPurchase AS fap WHERE fap.owner = :owner")
    Optional<BigDecimal> findTotal(@Param("owner") User owner);

    @Query("SELECT SUM(fap.amount) FROM FarmPurchase AS fap WHERE fap.owner = :owner AND fap.farm = :farm")
    Optional<BigDecimal> findTotal(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmPurchase fap SET fap.owner = null WHERE fap.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmPurchase fap SET fap.farm = null WHERE fap.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);

}
