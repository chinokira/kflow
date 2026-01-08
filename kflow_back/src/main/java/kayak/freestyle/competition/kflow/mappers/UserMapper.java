package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.models.User;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {

    @Override
    public UserDto modelToDto(User m) {
        UserDto userDto = new UserDto();
        userDto.setId(m.getId());
        userDto.setName(m.getName());
        userDto.setEmail(m.getEmail());
        userDto.setPassword(m.getPassword());
        userDto.setRole(m.getRole());
        return userDto;
    }

    @Override
    public User dtoToModel(UserDto d) {
        User user = new User();
        if (d.getId() != null && d.getId() != 0) {
            user.setId(d.getId());
        }
        user.setName(d.getName());
        user.setEmail(d.getEmail());
        user.setPassword(d.getPassword());
        user.setRole(d.getRole());
        return user;
    }

}
