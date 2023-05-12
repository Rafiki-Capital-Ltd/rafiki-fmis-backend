package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmProduction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmProductionService {
    FarmProduction create(FarmProduction farmProduction);
    Page<FarmProduction> findAll(int page, int size, String sort, String sortDirection);
    FarmProduction update(UUID id, FarmProduction farmProduction);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmProduction> farmActivities);
    Page<FarmProduction> findByFarm(Farm farm, int page, int size, String sort, String sortDirection);
}
