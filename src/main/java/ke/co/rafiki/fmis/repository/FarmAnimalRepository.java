package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
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
public interface FarmAnimalRepository extends JpaRepository<FarmAnimal, UUID> {
    Page<FarmAnimal> findByFarm(Farm farm, Pageable pageable);

    List<FarmAnimal> findByFarm(Farm farm);

    Page<FarmAnimal> findByOwner(User user, Pageable pageable);

    List<FarmAnimal> findByOwner(User user);

    @Query("SELECT fa FROM FarmAnimal AS fa WHERE fa.owner = :owner AND fa.farm = :farm")
    Page<FarmAnimal> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Query("SELECT fa FROM FarmAnimal AS fa WHERE fa.owner = :owner AND fa.farm = :farm")
    List<FarmAnimal> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Query("SELECT SUM(fa.quantity) FROM FarmAnimal AS fa")
    long findTotal();

    @Query("SELECT SUM(fa.quantity) FROM FarmAnimal AS fa WHERE fa.owner = :owner")
    long findTotal(@Param("owner") User owner);

    @Query("SELECT SUM(fa.quantity) FROM FarmAnimal AS fa WHERE fa.owner = :owner AND fa.farm = :farm")
    long findTotal(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAnimal fa SET fa.owner = null WHERE fa.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAnimal fa SET fa.farm = null WHERE fa.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
