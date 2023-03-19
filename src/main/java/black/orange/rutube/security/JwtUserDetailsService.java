package black.orange.rutube.security;

import black.orange.rutube.entity.User;
import black.orange.rutube.security.jwt.JwtUserFactory;
import black.orange.rutube.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findUserByEmail(email);

        return JwtUserFactory.create(user);
    }
}
