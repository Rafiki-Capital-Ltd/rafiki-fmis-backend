package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmVca;
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
public interface FarmVcaRepository extends JpaRepository<FarmVca, UUID> {
    Page<FarmVca> findByFarm(Farm farm, Pageable pageable);

    List<FarmVca> findByFarm(Farm farm);

    Page<FarmVca> findByOwner(User user, Pageable pageable);

    List<FarmVca> findByOwner(User user);

    @Transactional
    @Query("SELECT fav FROM FarmVca AS fav WHERE fav.owner = :owner AND fav.farm = :farm")
    Page<FarmVca> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Transactional
    @Query("SELECT fav FROM FarmVca AS fav WHERE fav.owner = :owner AND fav.farm = :farm")
    List<FarmVca> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmVca fav SET fav.owner = null WHERE fav.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmVca fav SET fav.farm = null WHERE fav.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
