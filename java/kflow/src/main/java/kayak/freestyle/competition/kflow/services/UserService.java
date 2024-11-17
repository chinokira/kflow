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
;
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

    public void patch(long id, Map<String, Object> values) {
        final User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        values.forEach((k, v) -> {
            try {
                final var attributeType = User.class.getDeclaredField(k).getType();
                final var setter = User.class.getMethod("set" + k.substring(0, 1).toUpperCase() + k.substring(1), attributeType);
                setter.invoke(user, v);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                throw new BadRequestException("invalid attribute " + k, e);
            }
        });
    }
}
