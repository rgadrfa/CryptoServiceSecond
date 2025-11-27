package model.file.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomNamer {
    public static String generateRandomText(int lengthInBytes) {
        byte[] randomBytes = new byte[lengthInBytes];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
