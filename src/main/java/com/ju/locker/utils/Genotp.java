package com.ju.locker.utils;

import java.util.Random;

public class Genotp {
    private static final int OTP_LENGTH = 4;

    public static String generateOTP() {
        // Define characters that can be used in the OTP
        String allowedChars = "0123456789";

        // Use a StringBuilder to construct the OTP
        StringBuilder otp = new StringBuilder();

        // Create a random generator
        Random random = new Random();

        // Generate OTP of the specified length
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(allowedChars.length());
            char digit = allowedChars.charAt(index);
            otp.append(digit);
        }

        return otp.toString();
    }

   
}
