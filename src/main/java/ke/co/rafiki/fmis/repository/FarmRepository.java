package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Page<Farm> findByOwner(User farmer, Pageable pageable);

    Page<Farm> findByCounty(County county, Pageable pageable);

    Page<Farm> findByWard(Ward ward, Pageable pageable);

    List<Farm> findByOwner(User farmer);

    List<Farm> findByCounty(County county);

    List<Farm> findByWard(Ward ward);
}
