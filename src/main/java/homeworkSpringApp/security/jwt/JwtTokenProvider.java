package homeworkSpringApp.security.jwt;

import homeworkSpringApp.model.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.accesstoken.expired}")
    private Long accessLife;

    @Value("${jwt.refreshtoken.expired}")
    private Long freshLife;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String userName, List<Role> userRoles){
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", getRoleNames(userRoles));
        // Помещение в токен флага isRefreshtoken = false с целью
        // запрета использования токена для обновления токена
        claims.put("isRefreshtoken",false);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessLife);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String createRefreshToken(String userName, List<Role> userRoles){
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", getRoleNames(userRoles));
        // Помещение в токен флага isRefreshtoken = true с целью
        // запрета использования токена для выполнения запросов,
        // требующих авторизации
        claims.put("isRefreshtoken",true);
        Date now = new Date();
        Date validity = new Date(now.getTime() + freshLife);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws ExpiredJwtException{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            //Проверка токена на истекший срок действия или наличие флага isRefreshtoken в claims
            if (claims.getBody().getExpiration().before(new Date()) || claims.getBody().get("isRefreshtoken",Boolean.class)){
                log.info("Accesstoken validation fault");
                return false;
            }
            return true;
    }

    public boolean validateRefreshToken(String token) throws ExpiredJwtException{
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        //Проверка токена на истекший срок действия или отсутствие флага isRefreshtoken в claims
        if (claims.getBody().getExpiration().before(new Date()) || !claims.getBody().get("isRefreshtoken",Boolean.class)){
            log.info("Refreshtoken validation fault");
            return false;
        }
        return true;
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> {
            result.add(role.getName());
        });
        return result;
    }
}
