package nuts.lib.manager.security_manager.crypto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Map;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.*;
import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.*;


public abstract class PasswordEncoderSupplier {

    public static DelegatingPasswordEncoder delegatingPasswordEncoder(PasswordEncoderSupport defaultEncoder) {
        Map<String, PasswordEncoder> encoderMap = Map.of(
                "bcrypt", bCryptPasswordEncoder(),
                "pbkdf2", pbkdf2PasswordEncoder(),
                "scrypt", sCryptPasswordEncoder(),
                "noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(defaultEncoder.idForEncode, encoderMap);
    }

    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static BCryptPasswordEncoder bCryptPasswordEncoder(int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    public static BCryptPasswordEncoder bCryptPasswordEncoder(BCryptVersion version) {
        return new BCryptPasswordEncoder(version);
    }

    public static BCryptPasswordEncoder bCryptPasswordEncoder(BCryptVersion version, int strength) {
        return new BCryptPasswordEncoder(version, strength);
    }

    public static Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
        return defaultsForSpringSecurity_v5_8();
    }

    public static Pbkdf2PasswordEncoder pbkdf2PasswordEncoder(String secret) {
        final int DEFAULT_SALT_LENGTH = 16;
        final int DEFAULT_ITERATIONS = 310000;
        final SecretKeyFactoryAlgorithm DEFAULT_ALGORITHM = SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

        return new Pbkdf2PasswordEncoder(secret, DEFAULT_SALT_LENGTH, DEFAULT_ITERATIONS, DEFAULT_ALGORITHM);
    }

    public static SCryptPasswordEncoder sCryptPasswordEncoder() {
        return SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
