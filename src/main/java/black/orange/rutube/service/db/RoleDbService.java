package black.orange.rutube.service.db;

import black.orange.rutube.entity.Role;
import black.orange.rutube.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleDbService {
    private final static String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findAdminRole() {
        return findByName(ROLE_ADMIN_NAME);
    }
}
