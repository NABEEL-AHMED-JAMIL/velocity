package com.ballistic.velocity.gzip;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class GZIPResponseWrapper extends HttpServletResponseWrapper {

    private static final Logger logger = LogManager.getLogger(GZIPResponseWrapper.class);

    protected HttpServletResponse origResponse;
    protected ServletOutputStream stream;
    protected PrintWriter writer;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    public ServletOutputStream createOutputStream() throws IOException {
        return (new GZIPResponseStream(origResponse));
    }

    public void finishResponse() {
        try {
            if (writer != null) { writer.close(); } else if (stream != null) { stream.close(); }
        } catch (IOException e) {
        }
    }

    @Override
    public void flushBuffer() throws IOException { stream.flush(); }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) { throw new IllegalStateException("getWriter() has already been called!"); }
        if (stream == null) { stream = createOutputStream(); }

        return (stream);
    }

    @Override
    public PrintWriter getWriter() throws IOException {

        if (writer != null) { return (writer); }
        if (stream != null) { throw new IllegalStateException("getOutputStream() has already been called!"); }

        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, HEADERS.UTF_8));
        return (writer);
    }

    @Override
    public void setContentLength(int length) { }

}