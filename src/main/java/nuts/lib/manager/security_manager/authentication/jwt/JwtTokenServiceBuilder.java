package nuts.lib.manager.security_manager.authentication.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.security_manager.authentication.TokenRepository;

public class JwtTokenServiceBuilder {

    private JwtServiceSymmetricKeyConfigurer symmetricKeyConfigurer;
    private JwtServiceAsymmetricKeyConfigurer asymmetricKeyConfigurer;

    public JwtTokenServiceBuilder useSymmetricKeyAlgorithm(Configurer<JwtServiceSymmetricKeyConfigurer> configurer) {
        this.symmetricKeyConfigurer = new JwtServiceSymmetricKeyConfigurer();
        configurer.config(this.symmetricKeyConfigurer);
        return this;
    }

    public JwtTokenServiceBuilder useAsymmetricKeyAlgorithm(Configurer<JwtServiceAsymmetricKeyConfigurer> configurer) {
        this.asymmetricKeyConfigurer = new JwtServiceAsymmetricKeyConfigurer();
        configurer.config(this.asymmetricKeyConfigurer);
        return this;
    }


    public JwtTokenService build(TokenRepository tokenRepository) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor("hellokeyhellokeyhellokeyhellokeyhellokey".getBytes()), Jwts.SIG.HS256);

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor("hellokeyhellokeyhellokeyhellokeyhellokey".getBytes()))
                .build();

        return new JwtTokenService(jwtBuilder, jwtParser, tokenRepository);
    }

}
