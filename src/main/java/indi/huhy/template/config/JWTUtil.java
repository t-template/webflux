package indi.huhy.template.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

    // TODO 密钥设置
    private static final String SECRET = "123456";
    private static final String ROLE_KEY = "role";
    private static final String IDENTIFICATION_KEY = "userID";

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String getRole(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(ROLE_KEY).asString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getIdentification(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(IDENTIFICATION_KEY).asString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String sign(String identification) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create().withClaim(IDENTIFICATION_KEY, identification).sign(algorithm);
    }
}