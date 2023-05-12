package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmConsumptionService {
    FarmConsumption create(FarmConsumption farmConsumption);
    Page<FarmConsumption> findAll(int page, int size, String sort, String sortDirection);
    FarmConsumption update(UUID id, FarmConsumption farmConsumption);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmConsumption> farmActivities);
    Page<FarmConsumption> findByFarm(Farm farm, int page, int size, String sort, String sortDirection);
}
