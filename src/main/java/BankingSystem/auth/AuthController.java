package BankingSystem.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        String token = authService.authenticate(request.getUsername(), request.getPassword());

        if(token == null){
            throw new RuntimeException("Invalid username or Password");
        }
        return token;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody User user){
        User createdUser = authService.register(user.getUsername(), user.getPassword());
        return new UserResponse(createdUser.getId(), createdUser.getUsername(),  createdUser.getRole());
    }
}
