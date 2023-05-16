package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Page<Role> findAll(int page, int size, String sort, String sortDirection);
    Role findOne(UUID id) throws Exception;
    Role findOne(String type) throws Exception;
    Role save(Role role) throws Exception;
    List<Role> saveAll(List<Role> roles) throws Exception;
    Role update(UUID id, Role role) throws Exception;
    void delete(UUID id);
}
