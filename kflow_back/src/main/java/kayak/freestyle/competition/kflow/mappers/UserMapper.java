package kayak.freestyle.competition.kflow.mappers;


import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.models.User;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {

    @Override
    public UserDto modelToDto(User m) {
        return UserDto.builder()
                .id(m.getId())
                .name(m.getName())
                .email(m.getEmail())
                .password(m.getPassword())
                .role(m.getRole())
                .build();
    }

    @Override
    public User dtoToModel(UserDto d) {
        return User.builder()
                .id(d.getId())
                .name(d.getName())
                .email(d.getEmail())
                .password(d.getPassword())
                .role(d.getRole())
                .build();
    }

}
