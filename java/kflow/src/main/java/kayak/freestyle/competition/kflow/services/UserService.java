package kayak.freestyle.competition.kflow.services;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.exceptions.BadRequestException;
import kayak.freestyle.competition.kflow.mappers.UserMapper;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.repositories.UserRepository;

@Service
public class UserService extends GenericService<User, UserDto, UserRepository, UserMapper> {

    /*private final PasswordEncoder passwordEncoder;*/
    public UserService(UserRepository repository, UserMapper mapper/* , PasswordEncoder passwordEncoder*/) {
        super(repository, mapper);
        /*this.passwordEncoder = passwordEncoder;*/
    }

    @Override
    public UserDto save(UserDto dto) {
        /*dto.setPassword(passwordEncoder.encode(dto.getPassword()));*/
        return super.save(dto);
    }
}
