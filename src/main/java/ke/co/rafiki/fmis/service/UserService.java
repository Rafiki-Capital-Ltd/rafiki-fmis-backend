package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    Page<User> findAll(int page, int size, String sort, String sortDirection);
    User findOne(UUID id) throws Exception;
    User findOne(String email) throws Exception;
    User save(User user) throws Exception;
    User update(UUID id, User user) throws Exception;
    void delete(UUID id);

}
