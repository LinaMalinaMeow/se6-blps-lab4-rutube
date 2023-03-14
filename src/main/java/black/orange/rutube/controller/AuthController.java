package black.orange.rutube.controller;

import black.orange.rutube.dto.AuthenticationRequestDto;
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

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        String token = userService.register(authenticationRequestDto);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) throws AuthException {
        String token = userService.auth(authenticationRequestDto);
        return ResponseEntity.ok().body(token);
    }
}
