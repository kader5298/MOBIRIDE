package com.mobiliz.testData;

import java.time.format.DateTimeFormatter;

public class ReservationData {
    public static final String RESERVATION_URL = "https://mobiride.testnet.mobiliz.com.tr/api/reservation";
    public static final String DELETE_URL = RESERVATION_URL + "/";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final int DELIVERY_HOURS = 2;
    public static final String DEFAULT_DESCRIPTION = "Test Rezervasyon";
}
