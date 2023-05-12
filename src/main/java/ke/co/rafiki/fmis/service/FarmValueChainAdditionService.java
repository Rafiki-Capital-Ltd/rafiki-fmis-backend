package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmValueChainAddition;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmValueChainAdditionService {
    FarmValueChainAddition create(FarmValueChainAddition farmValueChainAddition);
    Page<FarmValueChainAddition> findAll(int page, int size, String sort, String sortDirection);
    FarmValueChainAddition update(UUID id, FarmValueChainAddition farmValueChainAddition);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmValueChainAddition> farmActivities);
    Page<FarmValueChainAddition> findByFarm(Farm farm, int page, int size, String sort, String sortDirection);
}
