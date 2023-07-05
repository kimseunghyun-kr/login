package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    setFilter(new LogFilter()) : set the filter to register
//    setOrder(1) : set the priority of the filter within the filter chain. the lower the number the greater the priority order
//    addUrlPatterns("/*") : designate the url pattern where the filter will be called. Can designate many url patterns at once -> "*/" means all
//    search servlet URL pattern to search about the URL pattern rule
//    @ServletComponentScan and @Webfilter(filterName = "logFilter", urlPatterns = "/*")
//    also allows for the registration of filters but is unable to control the priority order of the filter
//    so use FilterRegistrationBean in WebConfig @Config

//    to learn how to leave the same identifier for the same HTTP request,
//    search logback mdc
//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

//    this substitutes the LogFilter(), thus the @bean for LogFilter() is commented out
//    the interceptor can be registered by addInterceptors method provided by WebMvcConfigurer
//    registry.addInterceptor(new LogInterceptor()) : registers the LogInterceptor
//    order(1) : designates the priority order of the interceptor
//    addPathPatterns("/**") : designates the URL pattern to apply the interceptor
//    excludePathPatterns("/css/**", "/*.ico", "/error") : designates the blacklisted URL patterns in which the interceptor will not be called upon

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

}
