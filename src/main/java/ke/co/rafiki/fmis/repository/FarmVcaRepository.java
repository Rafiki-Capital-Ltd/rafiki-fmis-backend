package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmVca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmVcaRepository extends JpaRepository<FarmVca, UUID> {
    Page<FarmVca> findByFarm(Farm farm, Pageable pageable);

    List<FarmVca> findByFarm(Farm farm);
}
