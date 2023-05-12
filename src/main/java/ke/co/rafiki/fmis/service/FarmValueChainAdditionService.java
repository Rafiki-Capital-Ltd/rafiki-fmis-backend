package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmValueChainAddition;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmValueChainAdditionService {
    FarmValueChainAddition create(FarmValueChainAddition farmValueChainAddition) throws Exception;
    Page<FarmValueChainAddition> findAll(int page, int size, String sort, String sortDirection);
    FarmValueChainAddition findOne(UUID id) throws Exception;
    FarmValueChainAddition update(UUID id, FarmValueChainAddition farmValueChainAddition) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmValueChainAddition> farmValueChainAdditions);
    Page<FarmValueChainAddition> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
