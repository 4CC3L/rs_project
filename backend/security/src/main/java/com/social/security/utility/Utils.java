package com.social.security.utility;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static byte[] convertToByteArray(Object obj) {
        try {

            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsBytes(obj);

        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
