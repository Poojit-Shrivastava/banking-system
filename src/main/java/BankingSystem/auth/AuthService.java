package BankingSystem.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authenticate(String username, String password){
        User user = userRepository
                .findByUsername(username)
                .orElse(null);
        if(user == null){
            return null;
        }
        boolean matches = passwordEncoder.matches(
                password, user.getPassword()
        );
        if(!matches){
            return null;
        }
        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}
