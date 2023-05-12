package stronghold.controller;

import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class SignUpMenuControllerTest {


    @org.junit.jupiter.api.Test
    void generateRandomString() throws Exception {
        String randomString = SignUpMenuController.generateRandomString();
        System.out.println(randomString);
        if(!randomString.matches("^.*[a-z]+.*$")){
            throw new Exception("no lowercase letters!", null);
        }
        if(!randomString.matches("^.*[A-Z]+.*$")){
            throw new Exception("no uppercase letters!", null);
        }
        if(!randomString.matches("^.*[0-9]+.*$")){
            throw new Exception("no numbers!", null);
        }
        if(randomString.matches("^[A-Za-z0-9]+$")){
            throw new Exception("no symbols!", null);
        }
    }

    @org.junit.jupiter.api.Test
    void currentUserGetterAndSetter(){
        SignUpMenuController.setCurrentUser(
                new User("JUnit", "12457093"
                , "Unit test", "junit@oracle.org",
                        1,
                        "test", "TESt")
        );
        assertEquals(SignUpMenuController.getCurrentUser().
                getUsername(), "JUnit");
    }

    @org.junit.jupiter.api.Test
    void authenticate() {
        assertEquals(SignUpMenuController.authenticate(
                "admin","peter"), false);
        assertEquals(SignUpMenuController.authenticate("mamad","Mamm@d12"),true);
    }

    @org.junit.jupiter.api.Test
    void testAll() throws Exception {
        generateRandomString();
        authenticate();
        currentUserGetterAndSetter();
    }
}