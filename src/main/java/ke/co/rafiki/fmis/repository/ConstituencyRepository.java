package ke.co.rafiki.fmis.repository;


import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstituencyRepository extends JpaRepository<Constituency, Integer> {
    Optional<Constituency> findByName(String name);
    Page<Constituency> findByCounty(County county, Pageable pageable);
    List<Constituency> findByCounty(County county);
}
