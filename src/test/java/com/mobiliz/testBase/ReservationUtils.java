package com.mobiliz.testBase;

import com.mobiliz.testData.LoginData;
import com.mobiliz.testData.ReservationData;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReservationUtils {

    public static String login(String username, String password) throws Exception {
        String jsonBody = String.format(
            "{\"userName\":\"%s\",\"password\":\"%s\",\"rememberMe\":false}",
            username, password
        );

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(HttpRequest.newBuilder(URI.create(LoginData.BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("mobiliz-app-token", LoginData.APP_TOKEN)
                    .header("mobiliz-user-language", LoginData.LANGUAGE)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            );

        return response.body();
    }

    public static String extractToken(String responseBody) {
        JSONObject json = new JSONObject(responseBody);

        if (json.has("accessToken")) return json.getString("accessToken");
        if (json.has("data") && json.getJSONObject("data").has("token"))
            return json.getJSONObject("data").getString("token");
        if (json.has("result") && json.getJSONObject("result").has("token"))
            return json.getJSONObject("result").getString("token");

        throw new RuntimeException("Token bulunamadı! JSON cevabı: " + responseBody);
    }

    public static String createReservation(String token, String reservationDate, String estimatedDeliveryDate, String description) throws Exception {
        JSONObject jsonBody = new JSONObject()
            .put("reservationDate", reservationDate)
            .put("estimatedDeliveryDate", estimatedDeliveryDate)
            .put("description", description);

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(HttpRequest.newBuilder(URI.create(ReservationData.RESERVATION_URL))
                    .header("Content-Type", "application/json")
                    .header("mobiliz-token", token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            );

        return response.body();
    }

    public static String listReservations(String token) throws Exception {
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(HttpRequest.newBuilder(URI.create(ReservationData.RESERVATION_URL))
                    .header("Content-Type", "application/json")
                    .header("mobiliz-token", token)
                    .GET()
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            );

        return response.body();
    }

    public static String deleteReservation(String token, int reservationId) throws Exception {
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(HttpRequest.newBuilder(URI.create(ReservationData.DELETE_URL + reservationId))
                    .header("Content-Type", "application/json")
                    .header("mobiliz-token", token)
                    .DELETE()
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            );

        return response.body();
    }
}
