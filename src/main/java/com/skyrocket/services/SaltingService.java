package com.skyrocket.services;

import com.skyrocket.model.UserAccount;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SaltingService {

    private static final SecureRandom salter = new SecureRandom();
    private static final byte[] salt = new byte[16];

    public SaltingService(){}

    public static byte[] createSalt() {
        salter.nextBytes(salt);
        return salt;
    }
}
