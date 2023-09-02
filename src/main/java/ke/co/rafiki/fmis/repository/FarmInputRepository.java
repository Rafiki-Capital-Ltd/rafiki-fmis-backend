package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmInput;
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
public interface FarmInputRepository extends JpaRepository<FarmInput, UUID> {
    Page<FarmInput> findByFarm(Farm farm, Pageable pageable);

    List<FarmInput> findByFarm(Farm farm);

    Page<FarmInput> findByOwner(User user, Pageable pageable);

    List<FarmInput> findByOwner(User user);

    @Query("SELECT fa FROM FarmInput AS fa WHERE fa.owner = :owner AND fa.farm = :farm")
    Page<FarmInput> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Query("SELECT fai FROM FarmInput AS fai WHERE fai.owner = :owner AND fai.farm = :farm")
    List<FarmInput> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Query("SELECT SUM(fai.quantity) FROM FarmInput AS fai")
    long findTotal();

    @Query("SELECT SUM(fai.quantity) FROM FarmInput AS fai WHERE fai.owner = :owner")
    long findTotal(@Param("owner") User owner);

    @Query("SELECT SUM(fai.quantity) FROM FarmInput AS fai WHERE fai.owner = :owner AND fai.farm = :farm")
    long findTotal(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmInput fai SET fai.owner = null WHERE fai.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmInput fai SET fai.farm = null WHERE fai.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
