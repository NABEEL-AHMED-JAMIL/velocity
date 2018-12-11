package com.ballistic.velocity.config;

import com.ballistic.velocity.gzip.GZIPFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* * * * * * * * * * * * * * * * * *
 * Note :- BeanConfig Section Done *
 * * * * * * * * * * * * * * * * * */
@Configuration
//@ComponentScan({"com.ballistic.velocity.bean.view", "com.ballistic.velocity.bean.email"})
public class BeanConfig {

    private static final Logger logger = LogManager.getLogger(BeanConfig.class);

    @Bean
    public FilterRegistrationBean <GZIPFilter> filterRegistrationBean() {
        FilterRegistrationBean <GZIPFilter> registrationBean = new FilterRegistrationBean();
        GZIPFilter gzipFilter = new GZIPFilter();
        registrationBean.setFilter(gzipFilter);
        registrationBean.addUrlPatterns("/rtb-bid");
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }
}