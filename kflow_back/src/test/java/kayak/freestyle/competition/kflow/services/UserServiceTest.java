package kayak.freestyle.competition.kflow.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.mappers.UserMapper;
import kayak.freestyle.competition.kflow.models.Role;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userService = new UserService(repository, mapper, passwordEncoder);
        
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);
        user.setName("Test User");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setPassword("rawPassword");
        userDto.setRole(Role.USER);
        userDto.setName("Test User");
    }

    @SuppressWarnings("null")
    @Test
    void save_ShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(passwordEncoder).encode("rawPassword");
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void save_WithNullPassword_ShouldSaveAndReturnDto() {
        userDto.setPassword(null);
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void save_WithEmptyPassword_ShouldSaveAndReturnDto() {
        userDto.setPassword("");
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void save_WithAdminRole_ShouldSaveWithAdminRole() {
        userDto.setRole(Role.ADMIN);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(Role.ADMIN, result.getRole());
        verify(passwordEncoder).encode("rawPassword");
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void save_WithNullRole_ShouldSaveAndReturnDto() {
        userDto.setRole(null);
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void save_WithInvalidEmail_ShouldSaveAndReturnDto() {
        userDto.setEmail("invalid-email");
        when(mapper.dtoToModel(any(UserDto.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.modelToDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(mapper).dtoToModel(any(UserDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).modelToDto(any(User.class));
    }
} 