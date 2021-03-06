package com.kotall.rms.web.auth;

import java.security.MessageDigest;
import java.util.UUID;

import com.kotall.rms.core.RmsException;

/**
 * 生成token
 *
 * @author aracwong
 * @date 2017年9月3日 上午2:56:55
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RmsException("生成Token失败", e);
        }
    }
}
