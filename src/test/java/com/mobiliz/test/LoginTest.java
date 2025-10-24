package com.mobiliz.test.test;

import com.mobiliz.testBase.ReservationUtils;
import com.mobiliz.testData.LoginData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    @DisplayName("Login - Token Al")
    void loginTest() throws Exception {
        for (String[] user : LoginData.USERS) {
            String loginResponse = ReservationUtils.login(user[0], user[1]);
            String token = ReservationUtils.extractToken(loginResponse);

            Assertions.assertNotNull(token, "Token alınamadı!");
            System.out.println("✅ Login token for " + user[0] + ": " + token);
        }
    }
}
