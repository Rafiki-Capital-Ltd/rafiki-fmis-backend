package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FarmConsumptionService {
    FarmConsumption save(FarmConsumption farmConsumption) throws Exception;
    Page<FarmConsumption> findAll(int page, int size,
                                  String sort, String sortDirection) throws Exception;
    Page<FarmConsumption> findAll(Farm farm, int page, int size,
                                  String sort, String sortDirection) throws Exception;
    FarmConsumption findOne(UUID id) throws Exception;
    FarmConsumption update(UUID id, FarmConsumption farmConsumption) throws  Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmConsumption> farmConsumptions);
    Page<FarmConsumption> findByFarm(Farm farm, int page, int size,
                                     String sort, String sortDirection) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    BigDecimal getTotal() throws Exception;
    BigDecimal getTotal(Farm farm) throws Exception;
}
