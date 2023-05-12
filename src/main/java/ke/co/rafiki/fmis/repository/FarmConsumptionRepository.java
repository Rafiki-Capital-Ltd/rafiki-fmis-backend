package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmConsumptionRepository extends JpaRepository<FarmConsumption, UUID> {
    Page<FarmConsumption> findByFarm(Farm farm, Pageable pageable);

    List<FarmConsumption> findByFarm(Farm farm);
}
