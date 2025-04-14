package com.poly.controller;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
        controller.registerUser("test", "1234");
    }

    @Test
    void whenRegisterNewUser_thenSuccess() {
        assertTrue(controller.registerUser("new", "pass"));
    }
    
    @Test
    void whenRegisterExistingUser_thenFail() {
        assertFalse(controller.registerUser("test", "1234")); 
    }

    @Test 
    void whenLoginWithValidCredentials_thenSuccess() {
        assertTrue(controller.loginUser("test", "1234"));
    }

    @Test
    void whenLoginWithInvalidPassword_thenFail() {
        assertFalse(controller.loginUser("test", "wrong"));
    }

    @Test
    void whenRegisterWithNullCredentials_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser(null, "pass"));
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser("user", null));
    }

    @Test
    void whenLoginWithNullCredentials_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.loginUser(null, "pass"));
        assertThrows(IllegalArgumentException.class, () -> controller.loginUser("test", null));
    }

    @Test
    void whenUpdatePasswordWithValidFormat_thenSuccess() {
        assertTrue(controller.updatePassword("test", "1234", "newPass123A"));
        assertTrue(controller.loginUser("test", "newPass123A"));
    }

    @Test
    void whenUpdatePasswordWithInvalidFormat_thenThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> controller.updatePassword("test", "1234", "weak"));
    }

    @Test
    void whenDeleteUserWithValidCredentials_thenSuccess() {
        assertTrue(controller.deleteUser("test", "1234"));
        assertFalse(controller.loginUser("test", "1234"));
    }

    @Test
    void whenDeleteUserWithInvalidCredentials_thenFail() {
        assertFalse(controller.deleteUser("test", "wrong"));
    }

    @Test
    void whenGetAllUsers_thenReturnCorrectList() {
        controller.registerUser("user2", "pass2");
        var users = controller.getAllUsers();
        assertTrue(users.contains("test"));
        assertTrue(users.contains("user2"));
        assertEquals(2, users.size());
    }

    @Test
    void whenRegisterWithShortUsername_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser("ab", "pass123"));
    }

    @Test
    void whenRegisterWithLongUsername_thenThrowException() {
        String longUsername = "a".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser(longUsername, "pass123"));
    }

    @Test
    void whenRegisterWithWeakPassword_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser("user1", "123"));
    }

    @Test
    void whenLoginWithNonexistentUser_thenFail() {
        assertFalse(controller.loginUser("nonexistent", "password123"));
    }

    @Test
    void whenDeleteNonexistentUser_thenFail() {
        assertFalse(controller.deleteUser("nonexistent", "password123"));
    }

    @Test
    void whenUpdatePasswordForNonexistentUser_thenFail() {
        assertFalse(controller.updatePassword("nonexistent", "oldPass", "newPass123"));
    }

    @Test
    void whenRegisterWithValidEmail_thenSuccess() {
        assertTrue(controller.registerUser("user@example.com", "Pass123!"));
    }

    @Test
    void whenRegisterWithInvalidEmail_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser("invalid.email", "Pass123!"));
    }

    @Test
    void whenRegisterWithSpecialCharacters_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerUser("user#name", "Pass123!"));
    }

    @Test
    void whenUpdatePasswordWithSameAsOld_thenFail() {
        assertFalse(controller.updatePassword("test", "1234", "1234"));
    }

    @Test
    void whenLoginAfterMultipleFailedAttempts_thenTemporarilyLocked() {
        for(int i = 0; i < 3; i++) {
            controller.loginUser("test", "wrongpass");
        }
        assertFalse(controller.loginUser("test", "1234"));
    }

    @Test
    void whenGetUserDetailsAfterUpdate_thenShowUpdatedInfo() {
        controller.updateUserDetails("test", "Test User", "test@example.com");
        var details = controller.getUserDetails("test");
        assertEquals("Test User", details.get("name"));
        assertEquals("test@example.com", details.get("email"));
    }
}
// This test class covers the UserController class, testing various scenarios including registration, login, password updates, and user deletion.
// It also includes edge cases such as invalid credentials, special characters, and email formats.