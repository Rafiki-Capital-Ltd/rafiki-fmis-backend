package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.County;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CountyService {
    List<County> findAll();
    Page<County> findAll(int page, int size, String sort, String sortDirection);
    County findOne(Integer id) throws Exception;
    County save(County county) throws Exception;
    County update(Integer id, County county) throws Exception;
    void delete(Integer id) throws Exception;
    void deleteMany(List<County> counties);
    void deleteAll();
}
