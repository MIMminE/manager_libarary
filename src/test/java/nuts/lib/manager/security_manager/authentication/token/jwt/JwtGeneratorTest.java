package nuts.lib.manager.security_manager.authentication.token.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.zaxxer.hikari.HikariDataSource;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.HMACSupport;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.RSASupport;
import nuts.lib.manager.security_manager.authentication.token.jwt.repository.SimpleRepository;
import nuts.lib.manager.security_manager.authentication.token.jwt.service.SignJwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class JwtGeneratorTest {


    @Test
    void jwtGeneratorTest() throws JOSEException, ParseException {

        SignJwtTokenService jwtTokenService = SignJwtTokenService.useHMACSigner(HMACSupport.HS256, "eyJpc3MiOiJ5b3VyLWlzc3VlciIsInN1YiI6IjEyMzQ1Njc4OTAiLCJleHAiOj");

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("1234567890")
                .issuer("your-issuer")
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1시간 유효
                .claim("roles", "admin")
                .build();

        JWT token = jwtTokenService.createToken(claimsSet);

        boolean verify = jwtTokenService.verifyToken(token.serialize());

        assertThat(verify).isTrue();
    }

    @Test
    void reaTest() throws JOSEException, ParseException {

        RSAKey rsaKey = new RSAKeyGenerator(2048).generate();

        SignJwtTokenService jwtTokenService = SignJwtTokenService.useRSASigner(RSASupport.RS256, rsaKey);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("1234567890")
                .issuer("your-issuer")
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1시간 유효
                .claim("roles", "admin, user")
                .build();

        JWT token = jwtTokenService.createToken(claimsSet);

        boolean verify = jwtTokenService.verifyToken(token.serialize());

        assertThat(verify).isTrue();

        List<? extends GrantedAuthority> authority = jwtTokenService.getAuthority(token);
        for (GrantedAuthority grantedAuthority : authority) {
            System.out.println(grantedAuthority);
        }
    }

//    @Test
//    void tokenRepoTest() throws JOSEException, ParseException {
//
//        HikariDataSource dataSource = DataSourceManager.createJdbcMySqlDataSource("localhost", 9000, "test_db", "tester", "tester");
//        TransactionTemplate transactionTemplate = new TransactionTemplate(new HibernateTransactionManager());
//        RSAKey rsaKey = new RSAKeyGenerator(2048).generate();
//
//        SignJwtTokenService jwtTokenService = SignJwtTokenService.useRSASigner(RSASupport.RS256, rsaKey);
//        jwtTokenService.plugIn(new SimpleRepository(new JdbcTemplate(dataSource), transactionTemplate, "authority"));
//
//        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                .subject("1234567890")
//                .issuer("your-issuer")
//                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1시간 유효
//                .claim("roles", "admin, user")
//                .build();
//
//        JWT token = jwtTokenService.createToken(claimsSet);
//
//        List<? extends GrantedAuthority> authority = jwtTokenService.getAuthority(token);
//        for (GrantedAuthority grantedAuthority : authority) {
//            System.out.println(grantedAuthority);
//        }
//
//    }
}