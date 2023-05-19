package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
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
public interface FarmCropRepository extends JpaRepository<FarmCrop, UUID> {
    Page<FarmCrop> findByFarm(Farm farm, Pageable pageable);

    List<FarmCrop> findByFarm(Farm farm);

    Page<FarmCrop> findByOwner(User user, Pageable pageable);

    List<FarmCrop> findByOwner(User user);

    @Transactional
    @Query("SELECT fc FROM FarmCrop AS fc WHERE fc.owner = :owner AND fc.farm = :farm")
    Page<FarmCrop> findByOwnerAndFarm(
            @Param("owner") User owner,
            @Param("farm") Farm farm,
            Pageable pageable
    );

    @Transactional
    @Query("SELECT fc FROM FarmCrop AS fc WHERE fc.owner = :owner AND fc.farm = :farm")
    List<FarmCrop> findByOwnerAndFarm(@Param("owner") User owner, @Param("farm") Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmCrop fc SET fc.owner = null WHERE fc.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmCrop fc SET fc.farm = null WHERE fc.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
