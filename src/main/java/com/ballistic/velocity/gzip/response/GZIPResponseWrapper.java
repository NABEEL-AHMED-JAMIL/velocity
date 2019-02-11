package com.ballistic.velocity.gzip.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream stream;
    private PrintWriter writer;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    private ServletOutputStream createOutputStream() throws IOException {
        return new GZIPResponseStream(getResponse().getOutputStream());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null && this.stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }
        if (this.writer == null) {
            this.stream = createOutputStream();
            this.writer = new PrintWriter(new OutputStreamWriter(this.stream, getResponse().getCharacterEncoding()));
        }
        return this.writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if(this.writer != null) { throw new IllegalStateException("getWriter() has already been called!"); }
        if(this.stream == null) { this.stream = createOutputStream(); }

        return this.stream;
    }

    @Override
    public void flushBuffer() throws IOException {

        if (this.writer != null) { this.writer.flush(); }

        IOException streamIOExc = null;
        try {
            if (this.stream != null) { this.stream.flush(); }
        } catch (IOException e) {
            streamIOExc = e;
        }

        IOException writerIOExc = null;
        try {
            super.flushBuffer();
        } catch (IOException e) {
            writerIOExc = e;
        }

        if (streamIOExc != null) throw streamIOExc;
        if (writerIOExc != null) throw writerIOExc;
    }

    public void finishResponse() {
        try {
            if (this.writer != null) { this.writer.close(); }
            if (this.stream != null) { this.stream.close(); }
        } catch (IOException ex) {}
    }
}