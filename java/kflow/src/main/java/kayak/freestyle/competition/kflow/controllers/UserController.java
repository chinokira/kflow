package kayak.freestyle.competition.kflow.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.exceptions.BadRequestException;
import kayak.freestyle.competition.kflow.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<UserDto, UserService> {

    public UserController(UserService userService) {
        super(userService);
    }
}
