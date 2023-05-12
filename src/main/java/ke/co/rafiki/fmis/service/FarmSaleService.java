package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmSaleService {
    FarmSale create(FarmSale farmSale);
    Page<FarmSale> findAll(int page, int size, String sort, String sortDirection);
    FarmSale update(UUID id, FarmSale farmSale);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmSale> farmActivities);
    Page<FarmSale> findByFarm(Farm farm, int page, int size, String sort, String sortDirection);
}
