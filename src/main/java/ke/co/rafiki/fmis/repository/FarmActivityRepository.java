package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmActivityRepository extends JpaRepository<FarmActivity, UUID> {
    Page<FarmActivity> findByFarmActivityLog(FarmActivityLog farmActivityLog, Pageable pageable);

    List<FarmActivity> findByFarmActivityLog(FarmActivityLog farmActivityLog);
}
