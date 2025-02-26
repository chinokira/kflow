package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<UserDto, UserService> {

    public UserController(UserService userService) {
        super(userService);
    }
}
