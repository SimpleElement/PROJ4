package org.example.Lab2_BLPS.service.authorization.service.util;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class Sha256PasswordUtil {

    public String hashCode(String password) throws NoSuchAlgorithmException {
        return Base64.getEncoder()
                .encodeToString(MessageDigest.getInstance("SHA-256")
                        .digest(password.getBytes(StandardCharsets.UTF_8))
                );
    }
}
