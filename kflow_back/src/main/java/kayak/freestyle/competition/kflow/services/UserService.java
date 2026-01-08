package kayak.freestyle.competition.kflow.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.exceptions.BadRequestException;
import kayak.freestyle.competition.kflow.mappers.UserMapper;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.repositories.UserRepository;

@Service
public class UserService extends GenericService<User, UserDto, UserRepository, UserMapper> {

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto dto) {
        // Vérifier si l'email existe déjà
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return super.save(dto);
    }
}
