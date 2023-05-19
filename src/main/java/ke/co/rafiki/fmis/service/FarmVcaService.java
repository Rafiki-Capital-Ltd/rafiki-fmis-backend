package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmVca;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmVcaService {
    FarmVca save(FarmVca farmVca) throws Exception;
    Page<FarmVca> findAll(int page, int size, String sort, String sortDirection);
    FarmVca findOne(UUID id) throws Exception;
    FarmVca update(UUID id, FarmVca farmVca) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmVca> farmVcas);
    Page<FarmVca> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
}
