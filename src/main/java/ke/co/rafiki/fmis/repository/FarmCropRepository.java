package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import ke.co.rafiki.fmis.domain.FarmCrop;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FarmCropRepository extends JpaRepository<FarmCrop, UUID> {
    Page<FarmAnimal> findByFarm(Farm farm, Pageable pageable);

    List<FarmAnimal> findByFarm(Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmCrop fc SET fc.owner = null WHERE fc.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);
}
