package nuts.lib.manager.security_manager.authentication.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtTokenService {

    public String test(){
        String compact = Jwts.builder()
                .subject("test")
                .signWith(Keys.hmacShaKeyFor("key".getBytes()), Jwts.SIG.HS256)
                .issuer("tester")
                .issuedAt(new Date())
                .expiration(new Date(3600))
                .compact();
        return compact;
    }
}
