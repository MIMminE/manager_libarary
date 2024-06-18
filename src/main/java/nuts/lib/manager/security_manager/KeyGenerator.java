package nuts.lib.manager.security_manager;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class KeyGenerator {

    public static RSAKey generate(int size) {
        try {
            return new RSAKeyGenerator(size).generate();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateFile(RSAKey rsaKey, String path) throws IOException, JOSEException {
        OutputStreamWriter publicStreamWriter = new OutputStreamWriter(new FileOutputStream(path + "/public_key.pem"));
        PemWriter publicPemWriter = new PemWriter(publicStreamWriter);
        PemObject publicKey = new PemObject("PUBLIC KEY", rsaKey.toPublicKey().getEncoded());
        publicPemWriter.writeObject(publicKey);
        publicPemWriter.close();


        OutputStreamWriter privateStreamWriter = new OutputStreamWriter(new FileOutputStream(path + "/private_key.pem"));
        PemWriter privatePemWriter = new PemWriter(privateStreamWriter);
        PemObject privateKey = new PemObject("PRIVATE KEY", rsaKey.toPrivateKey().getEncoded());
        privatePemWriter.writeObject(privateKey);
        privatePemWriter.close();
    }
}
