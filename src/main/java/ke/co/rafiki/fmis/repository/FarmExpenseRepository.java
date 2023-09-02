package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmExpense;
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
public interface FarmExpenseRepository extends JpaRepository<FarmExpense, UUID> {

    Page<FarmExpense> findByFarm(Farm farm, Pageable pageable);

    List<FarmExpense> findByFarm(Farm farm);

    Page<FarmExpense> findByOwner(User user, Pageable pageable);

    List<FarmExpense> findByOwner(User user);

    @Query("SELECT fae FROM FarmExpense AS fae WHERE fae.owner = :owner AND fae.farm = :farm")
    Page<FarmExpense> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Query("SELECT fae FROM FarmExpense AS fae WHERE fae.owner = :owner AND fae.farm = :farm")
    List<FarmExpense> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Query("SELECT SUM(fae.amount) FROM FarmExpense AS fae")
    Optional<BigDecimal> findTotal();

    @Query("SELECT SUM(fae.amount) FROM FarmExpense AS fae WHERE fae.owner = :owner")
    Optional<BigDecimal> findTotal(@Param("owner") User owner);

    @Query("SELECT SUM(fae.amount) FROM FarmExpense AS fae WHERE fae.owner = :owner AND fae.farm = :farm")
    Optional<BigDecimal> findTotal(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmExpense fae SET fae.owner = null WHERE fae.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmExpense fae SET fae.farm = null WHERE fae.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
    
}
