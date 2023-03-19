package black.orange.rutube.service;

import black.orange.rutube.entity.Role;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RolesService {
    private final String DEFAULT_ROLE_NAME = "ROLE_USER";
    private final String ENTITY_ROLE_CLASS_NAME = "Роль";
    private final RoleRepository roleRepository;


    public List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }

    public List<Role> getDefaultRoles() {
        Role roleUser = roleRepository.findByName(DEFAULT_ROLE_NAME)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
        return List.of(roleUser);
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
