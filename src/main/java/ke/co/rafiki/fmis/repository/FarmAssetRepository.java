package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
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
public interface FarmAssetRepository extends JpaRepository<FarmAsset, UUID> {
    Page<FarmAsset> findByFarm(Farm farm, Pageable pageable);

    List<FarmAsset> findByFarm(Farm farm);

    Page<FarmAsset> findByOwner(User user, Pageable pageable);

    List<FarmAsset> findByOwner(User user);

    @Transactional
    @Query("SELECT fas FROM FarmAsset AS fas WHERE fas.owner = :owner AND fas.farm = :farm")
    Page<FarmAsset> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Transactional
    @Query("SELECT fas FROM FarmAsset AS fas WHERE fas.owner = :owner AND fas.farm = :farm")
    List<FarmAsset> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAsset fas SET fas.owner = null WHERE fas.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAsset fas SET fas.owner = null WHERE fas.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
