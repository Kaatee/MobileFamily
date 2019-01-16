package com.example.kasia.mobilefamily;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {


        @Test
        public void validateLoginCredentials() {

            LoginActivity myObjectUnderTest = new LoginActivity();
            Boolean result;

            result = myObjectUnderTest.validate("user","user");
            assertTrue(result);

            result = myObjectUnderTest.validate("user123","user");
            assertFalse(result);

            result = myObjectUnderTest.validate("","");
            assertFalse(result);

            result = myObjectUnderTest.validate("user","");
            assertFalse(result);

            result = myObjectUnderTest.validate("","user");
            assertFalse(result);
        }

    }