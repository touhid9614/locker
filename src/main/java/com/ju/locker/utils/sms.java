package com.ju.locker.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class sms {
	
	private static final String API_URL = "https://portal.adnsms.com/api/v1/secure/send-sms";
    private static final String API_KEY = "KEY-mplvzo0jvtyjczpuwrc578p7ydvgf2tv";
    private static final String API_SECRET = "DRA3TB0ANJWkQ39q";
    private static final String REQUEST_TYPE = "SINGLE_SMS";
    private static final String MESSAGE_TYPE = "TEXT";

    public static void sendSms(String mobileNumber, String message) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String postData = String.format("api_key=%s&api_secret=%s&request_type=%s&message_type=%s&mobile=%s&message_body=%s",
                API_KEY, API_SECRET, REQUEST_TYPE, MESSAGE_TYPE, mobileNumber, message);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(postData);
            wr.flush();
        

        int responseCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Body: " + response.toString());
        }
        catch(Exception e) {
        	System.out.println("Invalid number" );
        }
    }


}
