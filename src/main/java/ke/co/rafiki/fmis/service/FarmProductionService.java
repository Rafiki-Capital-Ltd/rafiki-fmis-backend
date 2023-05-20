package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmProduction;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FarmProductionService {
    FarmProduction save(FarmProduction farmProduction) throws Exception;
    Page<FarmProduction> findAll(int page, int size, String sort, String sortDirection) throws Exception;
    FarmProduction findOne(UUID id) throws Exception;
    FarmProduction update(UUID id, FarmProduction farmProduction) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmProduction> farmProductions);
    Page<FarmProduction> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    BigDecimal getTotal() throws Exception;
    BigDecimal getTotal(Farm farm) throws Exception;
}
