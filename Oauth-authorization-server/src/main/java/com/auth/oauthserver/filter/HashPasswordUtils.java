package com.auth.oauthserver.filter;


import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashPasswordUtils {

    private final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public String hashSHA256(String pText, String pSalt) {
        String pTextSalt = pText + pSalt;
        String pHashedText = "";
        byte[] ptextSaltbyte = new byte[200];
        byte[] hashbyte = new byte[200];
        try {
            MessageDigest msgdigest = MessageDigest.getInstance("SHA-256");
            ptextSaltbyte = pTextSalt.getBytes("UTF-8");
            msgdigest.reset();
            msgdigest.update(ptextSaltbyte);
            hashbyte = msgdigest.digest();
            pHashedText = toHexString(hashbyte);
            //LOG.trace(ServerConstants.LOGGER_PREFIX_RESTFULL + "EncryptHash : " + pHashedText);

        } catch (NoSuchAlgorithmException n) {
            System.out.println("NoSuchAlgorithmException... in thirdparty"+ n);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException...in thirdparty"+ e);
        }
        System.out.println("password after encrypting "+pHashedText);
        return pHashedText;
    }

    public String toHexString(byte[] b) {
        System.out.println("converting to HexString in thirdparty");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int c = ((b[i]) >>> 4) & 0xf;
            sb.append(HEX[c]);
            c = (b[i] & 0xf);
            sb.append(HEX[c]);
        }
        return sb.toString();
    }
}

