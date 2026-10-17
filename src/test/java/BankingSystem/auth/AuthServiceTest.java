package BankingSystem.auth;

import BankingSystem.exception.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AuthServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final PasswordEncoder  passwordEncoder = mock(PasswordEncoder.class);

    private final JwtService jwtService = mock(JwtService.class);

    private final AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);

    @Test
    void authenticateShouldReturnTokenForValidCredentials(){
        User user = new User();
        user.setUsername("rahul");
        user.setPassword("encodedPassword");
        user.setRole("CUSTOMER");

        when(userRepository.findByUsername("rahul")).thenReturn(java.util.Optional.of(user));

        when(passwordEncoder.matches("password123","encodedPassword")).thenReturn(true);

        when(jwtService.generateToken("rahul", "CUSTOMER")).thenReturn("fake-jwt-token");


        // act
        String result = authService.authenticate("rahul", "password123");

        //assert
        assertEquals("fake-jwt-token",  result);


        verify(jwtService).generateToken("rahul", "CUSTOMER");
    }

    @Test
    void authenticateShouldReturnNUllForWrongPassword(){
        User user = new User();
        user.setUsername("rahul");
        user.setPassword("encodedPassword");
        user.setRole("CUSTOMER");

        when(userRepository.findByUsername("rahul")).thenReturn(java.util.Optional.of(user));

        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        //act
        String result = authService.authenticate("rahul", "worngPassword");

        assertNull(result);

        verify(jwtService, never()).generateToken(anyString(), anyString());
    }

    @Test
    void authenticateShouldReturnNullWhenUserDoesNotExist(){
       when(userRepository.findByUsername("unknown")).thenReturn(java.util.Optional.empty());
       // authService.authenticate("unknown", "password123");
        assertNull(authService.authenticate("unknown", "password123"));

        verify(passwordEncoder, never()).matches(anyString(), anyString());

        verify(jwtService, never()).generateToken(anyString(), anyString());
    }

    @Test
    void registerShouldCreateUserWithEncodedPassword(){
        User user =  new User();
        user.setUsername("newuser");
        user.setPassword("EncodedPassword");
        user.setRole("CUSTOMER");
        when(userRepository.findByUsername("newuser"))
                .thenReturn(java.util.Optional.empty());
    }
}
