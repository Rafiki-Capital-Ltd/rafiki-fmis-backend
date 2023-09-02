package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmInput;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmInputService {
    Page<FarmInput> findAll(int page, int size,
                            String sort, String sortDirection) throws Exception;
    Page<FarmInput> findAll(Farm farm, int page, int size,
                            String sort, String sortDirection) throws Exception;
    FarmInput findOne(UUID id) throws Exception;
    FarmInput save(FarmInput farmInput) throws Exception;
    FarmInput update(UUID id, FarmInput farmInput) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmInput> farmInputs);
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    long getTotal() throws Exception;
    long getTotal(Farm farm) throws Exception;
}
