package com.skyrocket.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingService {
    public HashingService(){}

    public byte[] createPasswordHash(byte[] salt, String password) throws NoSuchAlgorithmException {
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        hasher.update(salt);

        return hasher.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
