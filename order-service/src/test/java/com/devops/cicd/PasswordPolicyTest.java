package com.devops.cicd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordPolicyTest {

    @Test
    void should_have_at_least_a_maj(){
        // Assert
        String password = "sun";

        // Act
        Boolean expected = PasswordPolicy.isStrong(password);

        // Verify
        assertFalse(expected);
    }

    @Test
    void should_have_at_least_height_caracteres(){
        // Assert
        String password = "moon";

        // Act
        Boolean expected = PasswordPolicy.isStrong(password);

        // Verify
        assertFalse(expected);
    }

    @Test
    void should_have_at_least_a_special_caractere(){
        // Assert
        String password = "ju$iter";

        // Act
        Boolean expected = PasswordPolicy.isStrong(password);

        // Verify
        assertFalse(expected);
    }

    @Test
    void should_have_at_least_a_number(){
        // Assert
        String password = "nep7tune";

        // Act
        Boolean expected = PasswordPolicy.isStrong(password);

        // Verify
        assertFalse(expected);
    }

    @Test
    void should_be_right(){
        // Assert
        String password = "S@t25rne9";

        // Act
        Boolean expected = PasswordPolicy.isStrong(password);

        // Verify
        assertTrue(expected);
    }
}
