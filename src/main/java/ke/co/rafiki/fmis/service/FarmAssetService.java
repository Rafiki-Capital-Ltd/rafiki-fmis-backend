package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmAssetService {
    FarmAsset create(FarmAsset farmAsset) throws Exception;
    Page<FarmAsset> findAll(int page, int size, String sort, String sortDirection);
    FarmAsset findOne(UUID id) throws Exception;
    FarmAsset update(UUID id, FarmAsset farmAsset) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmAsset> farmActivities);
    Page<FarmAsset> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
