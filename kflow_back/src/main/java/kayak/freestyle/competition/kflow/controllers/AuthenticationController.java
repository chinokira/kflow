package kayak.freestyle.competition.kflow.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    public enum GrantType {
        refreshToken,
        password
    }

    @Setter
    @Getter
    public static class JwtRequest {

        private String username;
        private String password;
        private String refreshToken;
        private GrantType grantType;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class JwtResponse {

        private String accessToken;
        private String refreshToken;
    }

    @PostMapping("authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest request) {
        if (request.getGrantType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no grant type");
        }
        switch (request.getGrantType()) {
            case password:
                return authenticate(request.getUsername(), request.getPassword());
            case refreshToken:
                return refresh(request.getRefreshToken());
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid grant type");
        }
    }

    private JwtResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("email or password is invalid"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("email or password is invalid");
        }
        return new JwtResponse(
                generateAccessToken(user),
                generateRefreshToken(user));
    }

    private JwtResponse refresh(String refreshToken) {
        var jwt = jwtDecoder.decode(refreshToken);
        User user = userRepository.findById(Long.parseLong(jwt.getSubject())).get();
        return new JwtResponse(
                generateAccessToken(user),
                generateRefreshToken(user));
    }

    private String generateAccessToken(User user) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .issuer("annuaire-backend")
                .expiresAt(Instant.now().plusSeconds(1 * 60))
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getName())
                .claim("role", user.getRole().name())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    private String generateRefreshToken(User user) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .issuer("annuaire-backend")
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .subject(String.valueOf(user.getId()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}
