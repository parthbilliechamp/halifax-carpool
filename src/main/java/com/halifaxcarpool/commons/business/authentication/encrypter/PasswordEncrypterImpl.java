package com.halifaxcarpool.commons.business.authentication.encrypter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypterImpl implements IPasswordEncrypter {

    private static final String algorithm = "MD5";
    @Override
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(text.getBytes());
        byte[] bytes = messageDigest.digest();
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            String byteToString =
                    Integer.toString((aByte & 0xff) + 0x100, 16).substring(1);
            builder.append(byteToString);
        }
        return builder.toString();
    }

}
