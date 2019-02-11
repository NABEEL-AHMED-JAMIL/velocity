package com.ballistic.velocity.gzip.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZIPRequestStream extends ServletInputStream {

    private GZIPInputStream gzipStream;

    public GZIPRequestStream(InputStream inputStream) throws IOException {
        this.gzipStream = new GZIPInputStream(inputStream);
    }

    // first 1
    @Override
    public int read() throws IOException { return this.gzipStream.read(); }

    // optional
    @Override
    public int read(byte[] b) throws IOException { return this.gzipStream.read(b); }

    // optional
    @Override
    public int read(byte[] b, int off, int len) throws IOException { return this.gzipStream.read(b, off, len); }

    @Override
    public boolean isFinished() { return false; }

    @Override
    public boolean isReady() { return false; }

    @Override
    public void setReadListener(ReadListener readListener) { }

    @Override
    public void close() throws IOException { this.gzipStream.close(); }

}