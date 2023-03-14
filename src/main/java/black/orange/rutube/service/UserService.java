package black.orange.rutube.service;

import black.orange.rutube.dto.AuthenticationRequestDto;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import black.orange.rutube.repository.RoleRepository;
import black.orange.rutube.repository.UserRepository;
import black.orange.rutube.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String auth(AuthenticationRequestDto authUser) throws AuthException {
        User userFromDataBase = findUserByEmail(authUser.getEmail());
        if (userFromDataBase == null) {
            throw new AuthException("User not found");
        }

        if (passwordEncoder.matches(authUser.getPassword(), userFromDataBase.getPassword())) {
            return jwtTokenProvider.createToken(authUser.getEmail(), userFromDataBase.getRoles());
        }

        throw new AuthException("Wrong email or password");
    }

    public String register(AuthenticationRequestDto authUser) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        User user = new User();

        user.setEmail(authUser.getEmail());
        user.setPassword(passwordEncoder.encode(authUser.getPassword()));
        user.setRoles(userRoles);

        user = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", user);

        return jwtTokenProvider.createToken(authUser.getEmail(), userRoles);
    }


    public User findUserByEmail(String email) {
        User result = userRepository.findByEmail(email);
        log.info("IN findByUsername - user: {} found by email: {}", result, email);
        return result;
    }
}
