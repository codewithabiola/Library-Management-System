package com.example.UserLibrary.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {

    private static final String SECRET_KEY = "a_secretKey_here";
    private static final String IV = "theres_a_iv_here";

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),"AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key,iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedValue) {
        try{
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),"AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key,iv);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
