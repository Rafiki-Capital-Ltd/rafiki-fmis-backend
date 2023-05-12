package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface FarmAssetRepository extends JpaRepository<FarmAsset, UUID> {
    Page<FarmAsset> findByFarm(Farm farm, Pageable pageable);

    List<FarmAsset> findByFarm(Farm farm);
}
