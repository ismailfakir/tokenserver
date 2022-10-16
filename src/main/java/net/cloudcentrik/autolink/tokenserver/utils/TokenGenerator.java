package net.cloudcentrik.autolink.tokenserver.utils;

import java.security.SecureRandom;

public class TokenGenerator {

    protected static SecureRandom random = new SecureRandom();

    public synchronized String generateTokenTemp( String username ) {
        long longToken = Math.abs( random.nextLong() );
        String random = Long.toString( longToken, 16 );
        return ( username + ":" + random );
    }

    public static synchronized String generateToken(int length, String key) {
        String easy = RandomString.digits + key;
        RandomString randomString = new RandomString(length, new SecureRandom(), easy);
        return randomString.nextString();
    }

    public static synchronized String generateToken() {
        RandomString randomString = new RandomString(64, new SecureRandom());
        return randomString.nextString();
    }
}