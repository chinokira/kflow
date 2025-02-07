package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.models.User;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {
    
    @Override
    public UserDto modelToDto(User m) {
        UserDto userDto = UserDto.builder()
                .id(m.getId())
                .bibNb(m.getBibNb())
                .name(m.getName())
                .email(m.getEmail())
                .password(m.getPassword())
                .categories(m.getCategories())
                .runs(m.getRuns())
                .build();
        return userDto;
    }

    @Override
    public User dtoToModel(UserDto d) {
        User user = User.builder()
                .id(d.getId())
                .bibNb(d.getBibNb())
                .name(d.getName())
                .email(d.getEmail())
                .password(d.getPassword())
                .categories(d.getCategories())
                .runs(d.getRuns())
                .build();
        return user;
    }

}
