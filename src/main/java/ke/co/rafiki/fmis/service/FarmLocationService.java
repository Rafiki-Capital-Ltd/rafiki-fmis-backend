package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmLocation;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmLocationService {
    FarmLocation create(FarmLocation farmLocation) throws Exception;
    Page<FarmLocation> findAll(int page, int size, String sort, String sortDirection);
    FarmLocation findOne(UUID id) throws  Exception;
    FarmLocation update(UUID id, FarmLocation farmLocation) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmLocation> farmActivities);
    Page<FarmLocation> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
