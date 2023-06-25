package hello.login.web.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

//Filter interface is provided by java servlet.
// any classes that implements the Filter interface will be auto detected
//by servlet.
//servlet calls Filter before it calls itself
//with the following chain of command
//http req -> WebAppSys(WAS) -> FILTER -> servlet -> controller
//filter has the following signature
//public interface Filter {

// public default void init(FilterConfig filterConfig) throws ServletException
//{}
// public void doFilter(ServletRequest request, ServletResponse response,
// FilterChain chain) throws IOException, ServletException;
// public default void destroy() {}
//}

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }
//    class relation
//    ServletRequest > HttpServletRequest
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
//            this is the most important call as it allows the next chain to be called should there be remaining filters to be called
//            or it calls the servlet should there be none
//            given that the signature of this method is void, without this line, unable to proceed to either
//            the next filter or the servlet call
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
