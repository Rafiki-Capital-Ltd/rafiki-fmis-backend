package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmActivityService {
    FarmActivity save(FarmActivity farmActivity) throws Exception;
    Page<FarmActivity> findAll(int page, int size,
                               String sort, String sortDirection) throws Exception;
    FarmActivity findOne(UUID id) throws Exception;
    FarmActivity update(UUID id, FarmActivity farmActivity) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmActivity> farmActivities);
    Page<FarmActivity> findByFarmActivityLog(
            FarmActivityLog farmActivityLog, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
