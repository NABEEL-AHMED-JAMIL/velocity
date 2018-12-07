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

    private GZIPResponseStream gzipResponse;
    private ServletOutputStream servletOutputStream;
    private PrintWriter printWriter;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        response.addHeader("Content-Encoding", "gzip");
    }

    public void finish() throws IOException {

        if(this.printWriter != null) { this.printWriter.close(); }

        if(this.servletOutputStream != null) { this.servletOutputStream.close(); }

        if(this.gzipResponse != null) { this.gzipResponse.close(); }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(this.servletOutputStream == null) {
            this.gzipResponse = new GZIPResponseStream(getResponse().getOutputStream());
            this.servletOutputStream = gzipResponse;
        }
        return this.servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(this.printWriter == null) {
            this.gzipResponse = new GZIPResponseStream(getResponse().getOutputStream());
            this.printWriter = new PrintWriter(new OutputStreamWriter(this.gzipResponse, getResponse().getCharacterEncoding()));
        }
        return this.printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if(this.printWriter != null) { this.printWriter.flush(); }
        if(this.servletOutputStream != null) { this.servletOutputStream.flush(); }
        super.flushBuffer();
    }
}
