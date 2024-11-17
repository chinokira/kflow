package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.models.User;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {

    @Override
    public UserDto modelToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    @Override
    public User dtoToModel(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
