package com.ballistic.velocity.gzip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * Note :- Filter for only specific api
 */
public class GZIPFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(GZIPFilter.class);

    private GZIPResponseWrapper gzipResponse;

    /** nope */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { logger.debug("########## Initiating CustomURLFilter filter ##########"); }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            logger.info("Header's :- " + getHeader(request));
            // gzip setting in gzip-resposne-wrapper
            if(request.getHeader(HEADERS.ACCEPT_ENCODING) != null && request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.ACZ) != -1) {
                this.gzipResponse = new GZIPResponseWrapper(response);
                if(request.getHeader(HEADERS.X_ADMAXIM_MODE) != null && request.getHeader(HEADERS.X_ADMAXIM_MODE).indexOf(HEADERS.X_TEST_MODE) != -1) {
                    // send back the X_ADMAXIM_MODE: dev-true
                    response.setHeader(HEADERS.X_ADMAXIM_MODE, HEADERS.X_TEST_MODE+"-test");
                }
                if(request.getHeader(HEADERS.X_OPENRTB_VERSION) != null && request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER) != -1) {
                    // send back the X_OPENRTB_VERSION: version accept
                    response.setHeader(HEADERS.X_OPENRTB_VERSION, HEADERS.X_OPEN_VER+" accept");
                }
                filterChain.doFilter(request, this.gzipResponse);
                this.gzipResponse.finish();
                return;
            }
            filterChain.doFilter(request, response);
        }
    }

    /** nope */
    @Override
    public void destroy() { logger.debug("########## Destroying CustomURLFilter filter ##########"); }

    /**
     * Note :- only use for print the log's
     * */
    private String getHeader(HttpServletRequest request) {
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
        if(request.getHeader(HEADERS.X_OPENRTB_VERSION) != null && request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER) != -1){
            builder.append("(").
                    append(HEADERS.X_OPENRTB_VERSION).
                    append(" -:- ").
                    append(request.getHeader(HEADERS.X_OPENRTB_VERSION).substring(request.getHeader(HEADERS.X_OPENRTB_VERSION).indexOf(HEADERS.X_OPEN_VER))).
                    append(")");
        }

        return builder.toString();
    }

}
