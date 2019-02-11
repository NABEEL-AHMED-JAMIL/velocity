package com.ballistic.velocity.gzip;

import com.ballistic.velocity.gzip.request.GZIPRequestWrapper;
import com.ballistic.velocity.gzip.response.GZIPResponseWrapper;
import com.ballistic.velocity.gzip.util.HEADERS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/* * * * * * * * * * * * * * * * * * *  *
 * Note :- Filter for only specific api *
 * * * * * * * * * * * * * * * * * * *  */
public class GZIPFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(GZIPFilter.class);

    /** nope */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { logger.debug("########## Initiating CustomURLFilter filter ##########"); }

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest) {

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            // this will help to check what pub sending in header
            logger.debug("#-------------------------------------------------#");
            this.disPlayAllHeader(request);
            logger.debug("#-------------------------------------------------#");

            try {

                GZIPRequestWrapper requestWrapper = null;
                GZIPResponseWrapper responseWrapper = null;

                if (isGZipEncoded(request, HEADERS.CONTENT_ENCODING)) {
                    logger.debug("GZIP Content-Encoding FOUND ......");
                    requestWrapper = new GZIPRequestWrapper(request);
                }

                if (isGZipEncoded(request, HEADERS.ACCEPT_ENCODING)) {
                    logger.debug("GZip Process Accept-encoding FOUND ......");
                    response.addHeader("Content-Encoding", "gzip");
                    responseWrapper = new GZIPResponseWrapper(response);
                }

                if (requestWrapper == null && responseWrapper == null) {
                    filterChain.doFilter(request, response);
                } else {
                    if (requestWrapper != null && responseWrapper != null) {
                        filterChain.doFilter(requestWrapper, responseWrapper);
                        responseWrapper.finishResponse();
                    } else if (requestWrapper != null && responseWrapper == null) {
                        filterChain.doFilter(requestWrapper, response);
                    } else if (requestWrapper == null && responseWrapper != null) {
                        filterChain.doFilter(request, responseWrapper);
                        responseWrapper.finishResponse();
                    }
                }

            }catch (Exception ex) {
                logger.error("Error :- " + ex.getLocalizedMessage());
                response.getWriter().print("<html><head><title>Oops An error happened!</title></head>");
                response.getWriter().print("<body>Something bad happened uh-oh!");
                response.getWriter().print("<br><h1>" + ex.getLocalizedMessage() + "</h1></body>");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("</html>");
            }
        }
    }

    private void disPlayAllHeader(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            logger.debug(headerName + " :- " + request.getHeader(headerName));
        }
    }

    private Boolean isGZipEncoded(HttpServletRequest request, String encoded) {
        String isEncoded = request.getHeader(encoded);
        return isEncoded != null && isEncoded.indexOf(HEADERS.GZIP) != -1;
    }

    // Deprecate
    private String getRequestHeader(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        if(request.getHeader(HEADERS.ACCEPT_ENCODING) != null && request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.GZIP) != -1) {
            builder.append("(").
                    append(HEADERS.ACCEPT_ENCODING).
                    append(" -:- ").
                    append(request.getHeader(HEADERS.ACCEPT_ENCODING).substring(request.getHeader(HEADERS.ACCEPT_ENCODING).indexOf(HEADERS.GZIP))).
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
    // Deprecate
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
        if(response.getHeader(HEADERS.CONTENT_ENCODING) != null && response.getHeader(HEADERS.CONTENT_ENCODING).indexOf(HEADERS.GZIP) != -1) {
            builder.append("(").
                    append(HEADERS.CONTENT_ENCODING).
                    append(" -:- ").
                    append(response.getHeader(HEADERS.CONTENT_ENCODING).substring(response.getHeader(HEADERS.CONTENT_ENCODING).indexOf(HEADERS.GZIP))).
                    append(")");
        }

        return builder.toString();
    }

    /** nope */
    @Override
    public void destroy() { logger.debug("########## Destroying CustomURLFilter filter ##########"); }
}