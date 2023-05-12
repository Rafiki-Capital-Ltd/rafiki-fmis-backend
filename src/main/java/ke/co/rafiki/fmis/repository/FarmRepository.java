package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Page<Farm> findByOwner(User farmer, Pageable pageable);

    List<Farm> findByOwner(User farmer);
}
