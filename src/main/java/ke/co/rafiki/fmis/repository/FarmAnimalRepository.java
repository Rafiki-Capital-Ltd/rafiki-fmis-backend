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

import java.util.List;
import java.util.UUID;

public interface FarmAnimalRepository extends JpaRepository<FarmAnimal, UUID> {
    Page<FarmAnimal> findByFarm(Farm farm, Pageable pageable);

    List<FarmAnimal> findByFarm(Farm farm);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAnimal fa SET fa.owner = null WHERE fa.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);

    @Transactional
    @Modifying
    @Query("UPDATE FarmAnimal fa SET fa.farm = null WHERE fa.farm = :farm")
    void disassociateFromFarm(@Param("farm") Farm farm);
}
