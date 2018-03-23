package com.handicraft.core.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;

public class HashUtil {
    public static String encrypt(long id, String algorithm) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[0];
        if (messageDigest != null) {
            bytes = messageDigest.digest((id + "/" + ZonedDateTime.now().toString()).getBytes());
        }

        StringBuffer hexString = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
