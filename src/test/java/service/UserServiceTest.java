package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import entity.Admin;
import entity.Client;
import entity.User;
import model.UserModel;
import repository.impl.UserRepositoryImpl;
import service.impl.UserServiceImpl;

public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGetAll_withFilterAdmin() {
        User admin1 = new Admin("Admin", "User1", "admin1@mail.com", "password", 1);
        User admin2 = new Admin("Admin", "User2", "admin2@mail.com", "password", 2);
        when(userRepository.getAll()).thenReturn(Arrays.asList(admin1, admin2));

        List<User> result = userService.getAll("admin");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> user.getType().equalsIgnoreCase("Admin")));
    }

    @Test
    public void testGetAll_withFilterClient() {
        User client1 = new Client(1L, "Client", "User1", "client1@mail.com", "password", "address 1", "paypal");
        User client2 = new Client(2L, "Client", "User2", "client2@mail.com", "password", "address 2", "paypal");
        when(userRepository.getAll()).thenReturn(Arrays.asList(client1, client2));

        List<User> result = userService.getAll("client");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> user.getType().equalsIgnoreCase("Client")));
    }

    @Test
    public void testFindByEmail_UserExists() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        when(userRepository.findByEmail("test@mail.com")).thenReturn(user);

        User result = userService.findByEmail("test@mail.com");

        assertNotNull(result);
        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        when(userRepository.findByEmail("notfound@mail.com")).thenReturn(null);

        User result = userService.findByEmail("notfound@mail.com");

        assertNull(result);
    }

    @Test
    public void testUserExist_UserExists() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        when(userRepository.findByEmail("test@mail.com")).thenReturn(user);

        UserModel result = userService.userExist("test@mail.com");

        assertTrue(result.successful());
        assertEquals(user, result.getUSer());
    }

    @Test
    public void testUserExist_UserDoesNotExist() {
        when(userRepository.findByEmail("nonexistent@mail.com")).thenReturn(null);

        UserModel result = userService.userExist("nonexistent@mail.com");

        assertFalse(result.successful());
        assertNull(result.getUSer());
    }

    @Test
    public void testUserExistById_UserExists() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        when(userRepository.findById(1L)).thenReturn(user);

        boolean result = userService.userExist(1L);

        assertTrue(result);
    }

    @Test
    public void testUserExistById_UserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(null);

        boolean result = userService.userExist(99L);

        assertFalse(result);
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        UserModel expectedModel = new UserModel();
        expectedModel.setSuccess(true);
        expectedModel.setMessage("User created successfully.");
        when(userRepository.save(user)).thenReturn(expectedModel);

        UserModel result = userService.create(user);

        assertTrue(result.successful());
        assertEquals("User created successfully.", result.message());
    }

    @Test
    public void testDeleteUser_Success() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        when(userRepository.findById(1L)).thenReturn(user);
        UserModel expectedModel = new UserModel();
        expectedModel.setSuccess(true);
        expectedModel.setMessage("User deleted.");
        when(userRepository.delete(user)).thenReturn(expectedModel);

        UserModel result = userService.delete(1L);

        assertTrue(result.successful());
        assertEquals("User deleted.", result.message());
    }

    @Test
    public void testIsFirst_NoUsers() {
        when(userRepository.getAll()).thenReturn(Arrays.asList());

        boolean result = userService.isFirst();

        assertTrue(result);
    }

    @Test
    public void testIsFirst_UsersExist() {
        User user = new User(1L, "Test", "User", "test@mail.com", "password");
        when(userRepository.getAll()).thenReturn(Arrays.asList(user));

        boolean result = userService.isFirst();

        assertFalse(result);
    }
}
