package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FarmSaleService {
    FarmSale save(FarmSale farmSale) throws Exception;
    Page<FarmSale> findAll(int page, int size, String sort, String sortDirection) throws Exception;
    FarmSale findOne(UUID id) throws Exception;
    FarmSale update(UUID id, FarmSale farmSale) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmSale> farmSales);
    Page<FarmSale> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    BigDecimal getTotal() throws Exception;
    BigDecimal getTotal(Farm farm) throws Exception;
}
