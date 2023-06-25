package hello.login.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session == null) {
            return "no session active";
        }

        session.getAttributeNames().asIterator()
                .forEachRemaining( x -> log.info("session name = {}, value = {}", x, session.getAttribute(x)));

        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new
                Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());
        return "printing session";
    }

//    HttpSession maintains connection for a given time from the last user interaction with the server
//    to set session timeout diff from default(30mins) ->
//    server.servlet.session.timeout=60 (60 secs)
//    in application.properties

//    session.getLastAccessedTime() : last accessed time
//    if timeout time passes from the LAT, then WAS will expire the session internally

}
