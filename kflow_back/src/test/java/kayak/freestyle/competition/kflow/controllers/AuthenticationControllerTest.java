import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.server.ResponseStatusException;

import kayak.freestyle.competition.kflow.controllers.AuthenticationController;
import kayak.freestyle.competition.kflow.models.Role;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private AuthenticationController authenticationController;

    private AuthenticationController.JwtRequest validRequest;
    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setEmail("test@example.com");
        validUser.setPassword("encodedPassword");
        validUser.setRole(Role.USER);
        validUser.setName("Test User");

        validRequest = new AuthenticationController.JwtRequest();
        validRequest.setUsername("test@example.com");
        validRequest.setPassword("password");
        validRequest.setGrantType(AuthenticationController.GrantType.password);
    }

    @Test
    void authenticate_WithValidCredentials_ShouldReturnTokens() {
        when(userRepository.findByEmail(validRequest.getUsername())).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches(validRequest.getPassword(), validUser.getPassword())).thenReturn(true);
        org.springframework.security.oauth2.jwt.Jwt mockJwt = mock(org.springframework.security.oauth2.jwt.Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("access-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);

        AuthenticationController.JwtResponse response = authenticationController.authenticate(validRequest);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    void authenticate_WithInvalidCredentials_ShouldThrowException() {
        when(userRepository.findByEmail(validRequest.getUsername())).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches(validRequest.getPassword(), validUser.getPassword())).thenReturn(false);

        assertThrows(org.springframework.security.authentication.BadCredentialsException.class,
            () -> authenticationController.authenticate(validRequest));
    }

    @Test
    void authenticate_WithInvalidGrantType_ShouldThrowException() {
        validRequest.setGrantType(null);

        assertThrows(ResponseStatusException.class,
            () -> authenticationController.authenticate(validRequest));
    }

    @Test
    void authenticate_WithRefreshToken_ShouldReturnNewTokens() {
        validRequest.setGrantType(AuthenticationController.GrantType.refreshToken);
        validRequest.setRefreshToken("validRefreshToken");

        org.springframework.security.oauth2.jwt.Jwt mockJwt = mock(org.springframework.security.oauth2.jwt.Jwt.class);
        when(mockJwt.getSubject()).thenReturn("1");
        when(mockJwt.getTokenValue()).thenReturn("refresh-token");
        when(jwtDecoder.decode("validRefreshToken")).thenReturn(mockJwt);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        AuthenticationController.JwtResponse response = authenticationController.authenticate(validRequest);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }
} 