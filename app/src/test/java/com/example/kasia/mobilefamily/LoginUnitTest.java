package com.example.kasia.mobilefamily;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    @Test
    void validateLoginCredentials() {

        LoginActivity myObjectUnderTest = new LoginActivity();

        // ...when the string is returned from the object under test...
        Boolean result = myObjectUnderTest.validate("user","user");

        // ...then the result should be the expected one.
        assertTrue(result);
    }

}