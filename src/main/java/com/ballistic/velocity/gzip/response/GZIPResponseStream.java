package com.ballistic.velocity.gzip.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class GZIPResponseStream extends ServletOutputStream {

    private GZIPOutputStream gzipStream;

    public GZIPResponseStream(OutputStream outputStream) throws IOException {
        super();
        this.gzipStream = new GZIPOutputStream(outputStream);
    }

    @Override
    public void write(int b) throws IOException { this.gzipStream.write(b); }

    @Override
    public void write(byte[] b) throws IOException { this.gzipStream.write(b); }

    @Override
    public void write(byte[] b, int off, int len) throws IOException { this.gzipStream.write(b, off, len); }

    @Override
    public boolean isReady() { return false; }

    @Override
    public void setWriteListener(WriteListener writeListener) {}

    @Override
    public void close() throws IOException { this.gzipStream.close(); }

    @Override
    public void flush() throws IOException { this.gzipStream.flush(); }

}
