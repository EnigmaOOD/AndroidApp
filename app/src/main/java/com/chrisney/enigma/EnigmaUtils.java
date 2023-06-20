package com.chrisney.enigma;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EnigmaUtils {
   private final static int[] data = {77, 56, 117, 82, 122, 42, 67, 104, 88, 99, 69, 81, 112, 57, 36, 111, 48, 117, 79, 118, 56, 106, 110, 102, 48, 69, 116, 33, 70, 101, 52, 36};
   public static String enigmatization(byte[] enc) {
        try {
            byte[] keyValue  = keyToBytes(data);
            byte[] result = decrypt(keyValue, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static byte[] decrypt(byte[] keyValue, byte[] encrypted)
            throws Exception {
        SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParams);
        return cipher.doFinal(encrypted);
    }
    private static byte[] keyToBytes(int[] key) {
        int size = 16 * (key.length / 16);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append((char) key[i]);
        }
        return builder.toString().getBytes();
    }
}
