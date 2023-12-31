package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
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
public interface FarmSaleRepository extends JpaRepository<FarmSale, UUID> {
    Page<FarmSale> findByFarm(Farm farm, Pageable pageable);

    List<FarmSale> findByFarm(Farm farm);

    Page<FarmSale> findByOwner(User user, Pageable pageable);

    List<FarmSale> findByOwner(User user);

    @Query("SELECT fas FROM FarmSale AS fas WHERE fas.owner = :owner AND fas.farm = :farm")
    Page<FarmSale> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Query("SELECT fas FROM FarmSale AS fas WHERE fas.owner = :owner AND fas.farm = :farm")
    List<FarmSale> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Query("SELECT SUM(fas.amount) FROM FarmSale AS fas")
    Optional<BigDecimal> findTotal();

    @Query("SELECT SUM(fas.amount) FROM FarmSale AS fas WHERE fas.owner = :owner")
    Optional<BigDecimal> findTotal(@Param("owner") User owner);

    @Query("SELECT SUM(fas.amount) FROM FarmSale AS fas WHERE fas.owner = :owner AND fas.farm = :farm")
    Optional<BigDecimal> findTotal(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmSale fas SET fas.owner = null WHERE fas.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmSale fas SET fas.farm = null WHERE fas.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
