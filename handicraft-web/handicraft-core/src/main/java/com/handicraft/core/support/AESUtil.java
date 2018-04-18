package com.handicraft.core.support;

import com.handicraft.core.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.time.ZonedDateTime;
import java.util.Base64;

public class AESUtil {
    public static String encrypt(User user, String key) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        StringBuffer input = new StringBuffer();
        input.append(user.getUid())
                .append("/")
                .append(ZonedDateTime.now().plusMinutes(10).toString());

        byte[] keyBytes = new byte[16];
        System.arraycopy(key.getBytes("UTF8"), 0, keyBytes, 0, 16);
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(keyBytes);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameterSpec);
        return new String(Base64.getEncoder().encode(cipher.doFinal(input.toString().getBytes("UTF8"))));
    }

    public static String decrypt(String token, String key) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        byte[] keyBytes = new byte[16];
        System.arraycopy(key.getBytes("UTF8"), 0, keyBytes, 0, 16);
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(keyBytes);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, algorithmParameterSpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(token)), "UTF8");
    }

    public static String genererateRandomKey() {
        return RandomStringUtils.randomAscii(16);
    }
}
