package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConstituencyService {
    List<Constituency> findAll();
    Page<Constituency> findAll(int page, int size, String sort, String sortDirection);
    List<Constituency> findAll(County county);
    Constituency findOne(Integer id) throws Exception;
    Constituency save(Constituency constituency) throws Exception;
    Constituency update(Integer id, Constituency constituency) throws Exception;
    void delete(Integer id) throws Exception;
    void deleteMany(List<Constituency> constituencies);
    void deleteAll();
}
