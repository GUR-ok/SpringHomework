package homeworkSpringApp.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
            String requestURL = ((HttpServletRequest) servletRequest).getRequestURL().toString();
            log.info(requestURL);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException expEx) {
            String refreshToken = ((HttpServletRequest) servletRequest).getHeader("isRefreshToken");
            log.info("Expired token catched. RefreshToken header value: " + refreshToken);
            //Разрешить создание рефрештокена:
            String requestURL = ((HttpServletRequest) servletRequest).getRequestURL().toString();
            log.info("Checking for request url and validation refreshtoken: " + requestURL);
            if (requestURL.contains("refreshtoken") && jwtTokenProvider.validateRefreshToken(refreshToken)) {
                log.info("Checking that AccessToken and RefreshToken belong to the same User: " + expEx.getClaims().get("sub", String.class) + " " + jwtTokenProvider.getUserName(refreshToken));
                if (expEx.getClaims().get("sub", String.class).equals(jwtTokenProvider.getUserName(refreshToken))) {
                    //Проброс claims дальше в контроллер
                    log.info("Validation successfull");
                    servletRequest.setAttribute("claims", expEx.getClaims());
                    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
                    if (authentication != null) {
                        log.info("Add to contextholder");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
