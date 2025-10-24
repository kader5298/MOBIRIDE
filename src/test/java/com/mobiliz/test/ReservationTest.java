package com.mobiliz.test;

import com.mobiliz.testBase.ReservationUtils;
import com.mobiliz.testData.LoginData;
import com.mobiliz.testData.ReservationData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {

    private static String token;
    private static int reservationId;

    @Test
    @Order(1)
    @DisplayName("Login - Token Al")
    void loginTest() throws Exception {
        String[] user = LoginData.USERS[0];
        String loginResponse = ReservationUtils.login(user[0], user[1]);
        token = ReservationUtils.extractToken(loginResponse);

        Assertions.assertNotNull(token, "Token alınamadı!");
        System.out.println("✅ Login token: " + token);
    }

    @Test
    @Order(2)
    @DisplayName("Rezervasyon Oluştur")
    void createReservationTest() throws Exception {
        String reservationDateTime = LocalDateTime.now().format(ReservationData.DATE_FORMATTER);
        String estimatedDeliveryDateTime = LocalDateTime.now()
            .plusHours(ReservationData.DELIVERY_HOURS)
            .format(ReservationData.DATE_FORMATTER);

        String reservationResponse = ReservationUtils.createReservation(token, reservationDateTime, estimatedDeliveryDateTime, ReservationData.DEFAULT_DESCRIPTION);
        JSONObject json = new JSONObject(reservationResponse);

        reservationId = json.getInt("id");
        Assertions.assertTrue(reservationId > 0, "Rezervasyon ID alınamadı!");
        System.out.println("✅ Rezervasyon oluşturuldu. ID: " + reservationId);
    }

    @Test
    @Order(3)
    @DisplayName("Rezervasyon Listeleme")
    void listReservationsTest() throws Exception {
        String listResponse = ReservationUtils.listReservations(token);
        JSONArray reservations = new JSONArray(listResponse);

        boolean found = false;
        for (int i = 0; i < reservations.length(); i++) {
            if (reservations.getJSONObject(i).getInt("id") == reservationId) {
                found = true;
                break;
            }
        }

        Assertions.assertTrue(found, "Oluşturulan rezervasyon listede bulunamadı!");
        System.out.println("✅ Rezervasyon listede bulundu. ID: " + reservationId);
    }

    @Test
    @Order(4)
    @DisplayName("Rezervasyon Silme")
    void deleteReservationTest() throws Exception {
        String deleteResponse = ReservationUtils.deleteReservation(token, reservationId);
        System.out.println("✅ Rezervasyon silindi. Yanıt: " + deleteResponse);
    }
}
