package black.orange.rutube.converter;

import black.orange.rutube.dto.AuthenticationRequestDto;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserConverter {
    private final BCryptPasswordEncoder passwordEncoder;

    public User toEntity(AuthenticationRequestDto authUser, List<Role> userRoles) {
        User user = new User();

        user.setEmail(authUser.getEmail());
        user.setPassword(passwordEncoder.encode(authUser.getPassword()));
        user.setRoles(userRoles);

        return user;
    }
}
