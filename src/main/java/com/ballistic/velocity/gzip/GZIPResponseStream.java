package com.ballistic.velocity.gzip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

public class GZIPResponseStream extends ServletOutputStream {

    private static final Logger logger = LogManager.getLogger(GZIPResponseStream.class);

    protected ByteArrayOutputStream baos;
    protected GZIPOutputStream gzipstream;
    protected boolean closed;
    protected HttpServletResponse response;
    protected ServletOutputStream output;

    public GZIPResponseStream(HttpServletResponse response) throws IOException {
        super();
        closed = false;
        this.response = response;
        this.output = response.getOutputStream();
        baos = new ByteArrayOutputStream();
        gzipstream = new GZIPOutputStream(baos);
    }

    @Override
    public void close() throws IOException {

        if (closed) { throw new IOException("This output stream has already been closed"); }
        gzipstream.finish();
        byte[] bytes = baos.toByteArray();
        response.addHeader(HEADERS.CONTENT_LENGTH, Integer.toString(bytes.length));
        response.addHeader(HEADERS.CONTENT_ENCODING, HEADERS.ACZ);
        output.write(bytes);
        output.flush();
        output.close();
        closed = true;
    }

    @Override
    public void flush() throws IOException {
        if (closed) { throw new IOException("Cannot flush a closed output stream"); }
        gzipstream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        if (closed) { throw new IOException("Cannot write to a closed output stream"); }
        gzipstream.write((byte) b);
    }

    @Override
    public void write(byte b[]) throws IOException { write(b, 0, b.length); }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        if (closed) { throw new IOException("Cannot write to a closed output stream"); }
        gzipstream.write(b, off, len);
    }

    public boolean closed() { return (this.closed); }

    public void reset() { }

    @Override
    public boolean isReady() { return false; }

    @Override
    public void setWriteListener(WriteListener writeListener) { }

}
