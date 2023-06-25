package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

//    setFilter(new LogFilter()) : set the filter to register
//setOrder(1) : set the priority of the filter within the filter chain. the lower the number the greater the priority order
//addUrlPatterns("/*") : designate the url pattern where the filter will be called. Can designate many url patterns at once
//    search servlet URL pattern to search about the URL pattern rule
//    @ServletComponentScan and @Webfilter(filterName = "logFilter", urlPatterns = "/*")
//    also allows for the registration of filters but is unable to control the priority order of the filter
//    so use FilterRegistrationBean in WebConfig @Config

//    to learn how to leave the same identifier for the same HTTP request,
//    search logback mdc
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
