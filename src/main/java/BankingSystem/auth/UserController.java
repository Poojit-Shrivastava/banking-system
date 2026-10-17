package BankingSystem.auth;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){

        this.userService = userService;

    }

    @PostMapping
    public UserResponse createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return new UserResponse(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getRole()
        );
    }
}
