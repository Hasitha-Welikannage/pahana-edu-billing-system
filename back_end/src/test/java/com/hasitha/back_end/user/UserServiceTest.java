package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserDAO mockUserDao;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockUserDao = mock(UserDAO.class);
        userService = new UserService(mockUserDao);
    }

    @Test
    public void testFindAll_shouldReturnUserList() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Test");
        user.setLastName("User");

        when(mockUserDao.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.findAll();
        assertEquals(1, users.size());
        assertEquals("Test", users.get(0).getFirstName());
    }

    @Test
    public void testFindAll_shouldThrowNotFoundIfEmpty() {
        when(mockUserDao.findAll()).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> userService.findAll());
    }

    @Test
    public void testFindById_shouldReturnUser() {
        User user = new User();
        user.setId(5);
        user.setFirstName("Alice");

        when(mockUserDao.findById(5)).thenReturn(user);

        User result = userService.findById(5);
        assertEquals("Alice", result.getFirstName());
    }

    @Test
    public void testFindById_shouldThrowIfNotFound() {
        when(mockUserDao.findById(99)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> userService.findById(99));
    }

    @Test
    public void testCreate_shouldSucceed() {
        User user = new User();
        user.setFirstName("New");
        user.setLastName("User");
        user.setUserName("newuser");
        user.setPassword("pass123456");
        user.setRole("ADMIN");

        when(mockUserDao.findByUsername("newuser")).thenReturn(null);
        when(mockUserDao.create(user)).thenReturn(user);

        User created = userService.create(user);
        assertEquals("New", created.getFirstName());
    }

    @Test
    public void testCreate_shouldThrowIfUsernameExists() {
        User user = new User();
        user.setFirstName("New");
        user.setLastName("User");
        user.setUserName("existinguser");
        user.setPassword("pass123456");
        user.setRole("ADMIN");

        when(mockUserDao.findByUsername("existinguser")).thenReturn(new User());

        assertThrows(ValidationException.class, () -> userService.create(user));
    }

    @Test
    public void testUpdate_shouldUseExistingPasswordIfNotProvided() {
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUserName("testuser");

        User updatedUser = new User();
        updatedUser.setUserName("testuser");
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setRole("USER");

        when(mockUserDao.findById(1)).thenReturn(existingUser);
        when(mockUserDao.findPasswordById(1)).thenReturn("existingpass");
        when(mockUserDao.findByUsername("testuser")).thenReturn(existingUser);
        when(mockUserDao.update(eq(1), any(User.class))).thenReturn(updatedUser);

        User result = userService.update(1, updatedUser);
        assertEquals("Updated", result.getFirstName());
    }

    @Test
    public void testDelete_shouldCallDaoIfUserExists() {
        User user = new User();
        user.setId(7);

        when(mockUserDao.findById(7)).thenReturn(user);
        doNothing().when(mockUserDao).delete(7);

        assertDoesNotThrow(() -> userService.delete(7));
        verify(mockUserDao, times(1)).delete(7);
    }

    @Test
    public void testEnsureUserExists_shouldThrowIfNotExists() {
        when(mockUserDao.findById(42)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> userService.ensureUserExists(42));
    }
}
