package hello.login.web.filter;


import hello.login.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        try{
            log.info("login validation filter start {}", requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("login validation logic start {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("non login validated user request {}", requestURI);
                    //redirect to login
//                    additional + is present to store the current requestURI so that it may be used
//                    in the future to develop an auto redirect back to the current page user was viewing
//                    after logging in. {because it is annoying to redirect back manually from home again}
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return ;
                    //nothing more to do for users that are not logged in
                }
            }
//            is logged in if code reaches this point
            chain.doFilter(request, response);
        } catch(Exception e) {
            throw e; //able to log the exception, but the exception needs to reach TOMCAT WAS
        } finally {
            log.info("finished login validation filter {}", requestURI);
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
//        patternMatchUtils provided by spring to match a string to a String array of given patterns
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
