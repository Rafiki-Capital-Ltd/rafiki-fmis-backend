package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmSaleRepository extends JpaRepository<FarmSale, UUID> {
    Page<FarmSale> findByFarm(Farm farm, Pageable pageable);

    List<FarmSale> findByFarm(Farm farm);
}
