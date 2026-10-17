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

    public User register(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already  exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("CUSTOMER");

        return userRepository.save(user);

    }
}
