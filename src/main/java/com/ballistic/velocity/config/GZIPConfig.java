package com.ballistic.velocity.config;

import com.ballistic.velocity.gzip.GZIPFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* * * * * * * * * * * * * * * * * *
 * Note :- BeanConfig Section Done *
 * * * * * * * * * * * * * * * * * */
@Configuration
public class GZIPConfig {

    private static final Logger logger = LogManager.getLogger(GZIPConfig.class);

    @Bean
    public FilterRegistrationBean <GZIPFilter> filterRegistrationBean() {
        FilterRegistrationBean <GZIPFilter> registrationBean = new FilterRegistrationBean();
        GZIPFilter gzipFilter = new GZIPFilter();
        registrationBean.setFilter(gzipFilter);
        registrationBean.addUrlPatterns("/rtb-bid/request");
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }
}