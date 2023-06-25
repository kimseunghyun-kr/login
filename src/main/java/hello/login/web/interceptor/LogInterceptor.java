package hello.login.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.UUID;


//spring interceptor chain of action
//HTTP request -> WAS -> filter -> servlet -> SPRING INTERCEPTOR -> controller
//spring interceptor is provided by spring mvc, so it should be understandable
//that its start is from the dispatcher servlet.
//spring interceptor also allows usage of URL patterns to change control flow,
//but its mechanisms are very different from servlet URL patterns, and can be set
//with significant details

//sample control flow for login checks
//HTTP request -> WAS -> filter -> servlet -> SPRING INTERCEPTOR -> controller (normal)
//HTTP request -> WAS -> filter -> servlet -> SPRING INTERCEPTOR (rejected)

//spring interceptor is similar to servlet that it can form chains like servlet

//spring interceptor offers much more features than servlet filters other than the differences(and similarities)
//mentioned above


//to use a spring interceptor, one only needs to implement the HandlerInterceptor Interface
//where handlerInterceptor has the following signature

//public interface HandlerInterceptor {
//
// default boolean preHandle(HttpServletRequest request, HttpServletResponse
//response, Object handler) throws Exception {}

//default void postHandle(HttpServletRequest request, HttpServletResponse
//response,Object handler, @Nullable ModelAndView modelAndView) throws Exception {}
//
// default void afterCompletion(HttpServletRequest request, HttpServletResponse
//response, Object handler, @Nullable Exception ex) throws Exception {}
//
// }


//as can be seen from the above,
//spring Interceptor provides prehandle(), posthandle() and afterCompletion() methods
//which are far more segmented as compared to servlet's doFilter() method

//furthermore, servlet filter only provides request and response parameters within the inherited method
//but interceptor provides parameter about the handler, which controller(handler) is called upon
//,and it also provides a parameter about which modelAndView object is being returned

//to be more specific
//prehandler -> called before the controller (handler adapter) is called
//if preHandle returns true, it proceeds on to the next step within the control flow.
//if preHandle returns false, the usual control flow will not be called, including the remaining interceptors, and the handler adapters

//posthandle -> called after the controller (handler adapter) is called

//afterCompletion -> called after the view is rendered.

//If an exception has occured
//prehandle -> called before the controller is called
//posthandle -> if the controller throws an exception, postHandle will not be called
//afterCompletion -> afterCompletion is always called (similar to finally in the try except finally)
//should an afterCompletion occur after an exception, exception(ex) which is present in the parameter can be referenced to
//identify what kind of exception was thrown, and be logged

//interceptor can be summarised as a filter specifically tailored to the Spring MVC structure
//should any case arise where a filter is needed, a spring Interceptor usually is far more convenient
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();

//        takes the position of the set_id, Session_const for loginFilter
//        able to be represented as a regional variable for servlet but due to the
//        nature of prehandle, posthandle and aftercompletion occuring at different instances,
//        need a separate container to user the values designated in prehandle
//        in posthandle and aftercompletion

//        LogInterceptor is used like a singleton object, so keeping member variables can lead to potential unwanted
//        side effects, thus it is kept in request which would largely be unmutated.
//        this value is used in afterCompletion as request.getAttribute(LOG_ID)
        request.setAttribute(LOG_ID, uuid);


//        handlermethod contains all information of teh controller method that is to be called
//        handler information differs according to which handler mapping is being used.
//        with spring, usually, @Controller or @RequestMapping is used as the handler mapping,
//        for this instance, HandlerMethod is passed over as the handler information.

//        should a static resource be called, instead of @Controller,
//        ResourceHttpRequestHandler is passed over as the Handler information so
//        there needs to be a different code to handle the different classes(types) that aare being passed over

//        the reason for calling the completion log at afterCompletion and not postHandle
//        is due to postHandle not being called should an exception occurs. AfterCompletion guarantees
//        the log being called (behaves like finally)

        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle[{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}]", logId, requestURI);
        if(ex != null){
            log.error("afterCompletion error!! ", ex);
        }
    }

//    log provided by the LoginInterceptor

//    REQUEST [6234a913-f24f-461f-a9e1-85f153b3c8b2][/members/add]
//    [hello.login.web.member.MemberController#addForm(Member)]
//    postHandle [ModelAndView [view="members/addMemberForm";
//    model={member=Member(id=null, loginId=null, name=null, password=null),
//    org.springframework.validation.BindingResult.member=org.springframework.validation.BeanPropertyBindingResult: 0 errors}]]
//    RESPONSE [6234a913-f24f-461f-a9e1-85f153b3c8b2][/members/add]

//    URL PATH PATTERN supported by SPRING

//    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html
}
