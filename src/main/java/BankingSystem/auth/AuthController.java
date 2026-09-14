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
}
