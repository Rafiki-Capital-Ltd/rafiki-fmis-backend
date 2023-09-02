package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmExpense;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FarmExpenseService {
    Page<FarmExpense> findAll(int page, int size,
                              String sort, String sortDirection) throws Exception;
    Page<FarmExpense> findAll(Farm farm, int page, int size,
                              String sort, String sortDirection) throws Exception;
    FarmExpense findOne(UUID id) throws Exception;
    FarmExpense save(FarmExpense farmExpense) throws Exception;
    FarmExpense update(UUID id, FarmExpense farmExpense) throws Exception;
    void delete(UUID id);
    void deleteMany(List<FarmExpense> farmExpenses);
    void deleteAll();
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    BigDecimal getTotal() throws Exception;
    BigDecimal getTotal(Farm farm) throws Exception;
}
