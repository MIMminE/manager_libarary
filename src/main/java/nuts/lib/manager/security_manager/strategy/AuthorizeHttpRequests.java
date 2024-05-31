package nuts.lib.manager.security_manager.strategy;


public abstract class AuthorizeHttpRequests {

    static public HttpSecurityStrategy permitAll = http -> http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    // 요청 종류에 따라서 (url , 메서드, 각 리퀘스트 매처별
}
