package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    Page<User> findAll(int page, int size, String sort, String sortDirection);
    User findOne(UUID id) throws NotFoundException;
    User findOne(String email) throws NotFoundException;
    User save(User user) throws BadRequestException;
    User update(UUID id, User user) throws NotFoundException;
    void delete(UUID id);

}
