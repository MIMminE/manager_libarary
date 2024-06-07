package nuts.lib.manager.security_manager.crypto;

public enum PasswordEncoderSupport {
    bcrypt("bcrypt"),
    pbkdf2("pbkdf2"),
    scrypt("scrypt"),
    noop("noop");

    final String idForEncode;

    PasswordEncoderSupport(String idForEncode) {
        this.idForEncode = idForEncode;
    }
}
