package black.orange.rutube.controller;

import black.orange.rutube.dto.TokenDto;
import black.orange.rutube.dto.UserDto;
import black.orange.rutube.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.register(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> auth(@Valid @RequestBody UserDto userDto) throws AuthException {
        return ResponseEntity.ok().body(userService.auth(userDto));
    }
}
