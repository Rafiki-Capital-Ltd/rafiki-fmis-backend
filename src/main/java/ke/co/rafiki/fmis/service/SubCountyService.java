package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.SubCounty;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubCountyService {
    List<SubCounty> findAll();
    Page<SubCounty> findAll(int page, int size, String sort, String sortDirection);
    List<SubCounty> findAll(County county);
    List<SubCounty> findAll(Constituency constituency);
    SubCounty findOne(Integer id) throws Exception;
    SubCounty save(SubCounty subCounty) throws Exception;
    SubCounty update(Integer id, SubCounty subCounty) throws Exception;
    void delete(Integer id) throws Exception;
    void deleteMany(List<SubCounty> subCounties);
    void deleteAll();
}
