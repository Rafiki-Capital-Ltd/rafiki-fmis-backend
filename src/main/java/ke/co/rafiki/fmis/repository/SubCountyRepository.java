package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.SubCounty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCountyRepository extends JpaRepository<SubCounty, Integer> {
    Optional<SubCounty> findByName(String name);
    Page<SubCounty> findByCounty(County county, Pageable pageable);
    Page<SubCounty> findByConstituency(Constituency constituency, Pageable pageable);
    List<SubCounty> findByCounty(County county);
    List<SubCounty> findByConstituency(Constituency constituency);
}
