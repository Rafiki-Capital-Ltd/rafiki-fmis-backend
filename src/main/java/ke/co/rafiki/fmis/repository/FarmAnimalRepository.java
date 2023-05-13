package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface FarmAnimalRepository extends JpaRepository<FarmAnimal, UUID> {
    Page<FarmAnimal> findByFarm(Farm farm, Pageable pageable);

    List<FarmAnimal> findByFarm(Farm farm);
}
