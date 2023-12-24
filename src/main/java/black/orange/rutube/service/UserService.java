package black.orange.rutube.service;

import black.orange.rutube.converter.UserConverter;
import black.orange.rutube.dto.TokenDto;
import black.orange.rutube.dto.UserDto;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import black.orange.rutube.exception.EntityAlreadyExistsException;
import black.orange.rutube.exception.EntityNotFoundException;
import black.orange.rutube.exception.auth.WrongAuthException;
import black.orange.rutube.security.jwt.JwtTokenProvider;
import black.orange.rutube.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final static String ENTITY_CLASS_NAME = "Пользователь";
    private final UserDbService userDbService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserConverter userConverter;
    private final RolesService rolesService;

    public TokenDto auth(UserDto authDto) {
        User userFromDataBase = userDbService.findUserByEmail(authDto.getEmail()).orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));

        if (passwordEncoder.matches(authDto.getPassword(), userFromDataBase.getEncodedPassword())) {
            return TokenDto.builder().token(jwtTokenProvider.createToken(authDto.getEmail(), userFromDataBase.getRoles())).build();
        }

        throw new WrongAuthException();
    }

    public TokenDto register(UserDto registerDto) {
        if (userDbService.existsByEmail(registerDto.getEmail().trim())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        List<Role> userRoles = rolesService.getDefaultRoles();

        User user = userConverter.toEntity(registerDto, userRoles);

        userDbService.create(user);

        return TokenDto.builder().token(jwtTokenProvider.createToken(registerDto.getEmail(), userRoles)).build();
    }


    public Long getUserIdFromContext() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDbService.findUserByEmail(userName).orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME)).getId();
    }

    public String getUserEmail(Long userId) {
        return userDbService.findUserById(userId).orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME)).getEmail();
    }
}
