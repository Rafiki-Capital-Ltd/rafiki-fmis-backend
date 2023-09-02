package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmPurchase;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FarmPurchaseService {
    Page<FarmPurchase> findAll(int page, int size,
                              String sort, String sortDirection) throws Exception;
    Page<FarmPurchase> findAll(Farm farm, int page, int size,
                              String sort, String sortDirection) throws Exception;
    FarmPurchase findOne(UUID id) throws Exception;
    FarmPurchase save(FarmPurchase farmPurchase) throws Exception;
    FarmPurchase update(UUID id, FarmPurchase farmPurchase) throws Exception;
    void delete(UUID id);
    void deleteMany(List<FarmPurchase> farmPurchases);
    void deleteAll();
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    BigDecimal getTotal() throws Exception;
    BigDecimal getTotal(Farm farm) throws Exception;
}
