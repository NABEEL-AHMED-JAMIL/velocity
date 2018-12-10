package com.ballistic.velocity.gzip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* * * * * * * * * * * * * * * * * * *  *
 * Note :- Filter for only specific api *
 * * * * * * * * * * * * * * * * * * *  */
public class GZIPFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(GZIPFilter.class);

    private GZIPResponseWrapper gzipResponse;

    /** nope */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { logger.debug("########## Initiating CustomURLFilter filter ##########"); }

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            System.out.println("Request " + request.getHeader(HttpHeaders.ACCEPT_ENCODING));
            logger.debug("Header's Request :- " + getRequestHeader(request));
            // gzip setting in gzip-resposne-wrapper
            if(request.getHeader(HEADERS.ACCEPT_ENCODING) != null && request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.ACZ) != -1) {
                response.setHeader(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN, HEADERS.ACCESS_CONTROL_ALLOW_ALL);
                response.setHeader(HEADERS.ACCESS_CONTROL_ALLOW_METHODS, HEADERS.ACCESS_CONTROL_ALLOW_METHODS_ALL);
                response.setHeader(HEADERS.ACCESS_CONTROL_MAX_AGE, HEADERS.MAX_AGE);
                response.setHeader(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS, HEADERS.ACCESS_CONTROL_ALLOW_HEADERS_);
                if(request.getHeader(HEADERS.X_ADMAXIM_MODE) != null && request.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE) != -1) {
                    // send back the X_ADMAXIM_MODE: dev-true
                    response.setHeader(HEADERS.X_ADMAXIM_MODE, HEADERS.X_TEST_MODE+"-test");
                }
                if(request.getHeader(HEADERS.X_OPENRTB_VERSION) != null && request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER) != -1) {
                    // send back the X_OPENRTB_VERSION: version accept
                    response.setHeader(HEADERS.X_OPENRTB_VERSION, HEADERS.X_OPEN_VER+" accept");
                }
                this.gzipResponse = new GZIPResponseWrapper(response);
                filterChain.doFilter(servletRequest, this.gzipResponse);
                this.gzipResponse.finishResponse();
                logger.debug("Header's Response :- " + getResponseHeader(response));
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /** nope */
    @Override
    public void destroy() { logger.debug("########## Destroying CustomURLFilter filter ##########"); }

    /**
     * Note :- only use for print the log's
     * */
    private String getRequestHeader(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();

        if(request.getHeader(HEADERS.ACCEPT_ENCODING) != null && request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.ACZ) != -1) {
            builder.append("(").
                    append(HEADERS.ACCEPT_ENCODING).
                    append(" -:- ").
                    append(request.getHeader(HEADERS.ACCEPT_ENCODING).substring(request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.ACZ))).
                    append(")");
        }
        if(request.getHeader(HEADERS.X_ADMAXIM_MODE) != null && request.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE) != -1) {
            builder.append("(").
                    append(HEADERS.X_ADMAXIM_MODE).
                    append(" -:- ").
                    append(request.getHeader(HEADERS.X_ADMAXIM_MODE).substring(request.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE))).
                    append(")");
        }
        if(request.getHeader(HEADERS.X_OPENRTB_VERSION) != null && request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER) != -1) {
            builder.append("(").
                    append(HEADERS.X_OPENRTB_VERSION).
                    append(" -:- ").
                    append(request.getHeader(HEADERS.X_OPENRTB_VERSION).substring(request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER))).
                    append(")");
        }

        return builder.toString();
    }

    /**
     * Note :- only use for print the log's
     * */
    private String getResponseHeader(HttpServletResponse response) {

        StringBuilder builder = new StringBuilder();

        if(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN) != null && response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_ALL) != -1) {
            builder.append("(").
                    append(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN).substring(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_ORIGIN).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_ALL))).
                    append(")");
        }
        if(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_METHODS) != null && response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_METHODS).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_METHODS_ALL) != -1) {
            builder.append("(").
                    append(HEADERS.ACCESS_CONTROL_ALLOW_METHODS).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_METHODS).substring(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_METHODS).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_METHODS_ALL))).
                    append(")");
        }
        if(response.getHeader(HEADERS.ACCESS_CONTROL_MAX_AGE) != null && response.getHeader(HEADERS.ACCESS_CONTROL_MAX_AGE).indexOf(HEADERS.MAX_AGE) != -1) {
            builder.append("(").
                    append(HEADERS.ACCESS_CONTROL_MAX_AGE).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.ACCESS_CONTROL_MAX_AGE).substring(response.getHeader(HEADERS.ACCESS_CONTROL_MAX_AGE).indexOf(HEADERS.MAX_AGE))).
                    append(")");
        }
        if(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS) != null && response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS_) != -1) {
            builder.append("(").
                    append(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS).substring(response.getHeader(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS).indexOf(HEADERS.ACCESS_CONTROL_ALLOW_HEADERS_))).
                    append(")");
        }
        if(response.getHeader(HEADERS.X_ADMAXIM_MODE) != null && response.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE+"-test") != -1) {
            builder.append("(").
                    append(HEADERS.X_ADMAXIM_MODE).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.X_ADMAXIM_MODE).substring(response.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE+"-test"))).
                    append(")");
        }
        if(response.getHeader(HEADERS.X_OPENRTB_VERSION) != null && response.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER+" accept") != -1) {
            builder.append("(").
                    append(HEADERS.X_OPENRTB_VERSION).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.X_OPENRTB_VERSION).substring(response.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER+" accept"))).
                    append(")");
        }
        if(response.getHeader(HEADERS.CONTENT_ENCODING) != null && response.getHeader(HEADERS.CONTENT_ENCODING).indexOf(HEADERS.ACZ) != -1) {
            builder.append("(").
                    append(HEADERS.CONTENT_ENCODING).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.CONTENT_ENCODING).substring(response.getHeader(HEADERS.CONTENT_ENCODING).indexOf(HEADERS.ACZ))).
                    append(")");
        }

        return builder.toString();
    }
}