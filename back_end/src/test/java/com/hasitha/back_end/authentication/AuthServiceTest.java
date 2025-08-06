package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private AuthDAO mockAuthDao;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        mockAuthDao = mock(AuthDAO.class);
        authService = new AuthService(mockAuthDao);
    }

    // --- Tests for successful login ---
    @Test
    public void testLogin_shouldSucceedWithCorrectCredentials() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        User expectedUser = new User(1, "test", "user", username, "password", "cashier");

        when(mockAuthDao.authenticate(username, password)).thenReturn(expectedUser);

        // Act
        User result = authService.login(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(mockAuthDao, times(1)).authenticate(username, password);
    }

    @Test
    public void testLogin_shouldTrimWhitespaceFromCredentials() {
        // Arrange
        String usernameWithWhitespace = " testuser ";
        String passwordWithWhitespace = " password123 ";
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername(usernameWithWhitespace);
        request.setPassword(passwordWithWhitespace);

        User expectedUser = new User(1, "test", "user", "testuser", "password123", "cashier");

        when(mockAuthDao.authenticate("testuser", "password123")).thenReturn(expectedUser);

        // Act
        User result = authService.login(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser.getUserName(), result.getUserName());
        verify(mockAuthDao, times(1)).authenticate("testuser", "password123");
    }

    // --- Tests for invalid login attempts ---
    @Test
    public void testLogin_shouldThrowValidationException_whenLoginRequestIsNull() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(null);
        });

        assertEquals("Login request cannot be null.", exception.getMessage());
        verify(mockAuthDao, never()).authenticate(anyString(), anyString());
    }

    @Test
    public void testLogin_shouldThrowValidationException_whenUsernameIsBlank() {
        // Arrange
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername(" ");
        request.setPassword("password123");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(request);
        });

        assertEquals("Username is required.", exception.getMessage());
        verify(mockAuthDao, never()).authenticate(anyString(), anyString());
    }

    @Test
    public void testLogin_shouldThrowValidationException_whenUsernameIsNull() {
        // Arrange
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername(null);
        request.setPassword("password123");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(request);
        });

        assertEquals("Username is required.", exception.getMessage());
        verify(mockAuthDao, never()).authenticate(anyString(), anyString());
    }

    @Test
    public void testLogin_shouldThrowValidationException_whenPasswordIsBlank() {
        // Arrange
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword(" ");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(request);
        });

        assertEquals("Password is required.", exception.getMessage());
        verify(mockAuthDao, never()).authenticate(anyString(), anyString());
    }

    @Test
    public void testLogin_shouldThrowValidationException_whenPasswordIsNull() {
        // Arrange
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword(null);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(request);
        });

        assertEquals("Password is required.", exception.getMessage());
        verify(mockAuthDao, never()).authenticate(anyString(), anyString());
    }

    @Test
    public void testLogin_shouldThrowValidationException_whenCredentialsAreIncorrect() {
        // Arrange
        String username = "wronguser";
        String password = "wrongpassword";
        // Using setters to create the request object
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        when(mockAuthDao.authenticate(username, password)).thenReturn(null);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            authService.login(request);
        });

        assertEquals("Incorrect username or password. Please try again.", exception.getMessage());
        verify(mockAuthDao, times(1)).authenticate(username, password);
    }
}
