package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Page<Farm> findByOwner(User farmer, Pageable pageable);

    Page<Farm> findByCounty(County county, Pageable pageable);

    Page<Farm> findByConstituency(Constituency constituency, Pageable pageable);

    Page<Farm> findBySubCounty(SubCounty subCounty, Pageable pageable);

    List<Farm> findByOwner(User owner);

    List<Farm> findByCounty(County county);

    List<Farm> findByConstituency(Constituency constituency);

    List<Farm> findBySubCounty(SubCounty subCounty);

    @Transactional
    @Modifying
    @Query("UPDATE Farm fm SET fm.owner = null WHERE fm.owner = :owner")
    void disassociateFromOwner(@Param("owner") User owner);
}
