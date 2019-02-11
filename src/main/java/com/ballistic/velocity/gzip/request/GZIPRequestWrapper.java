package com.ballistic.velocity.gzip.request;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GZIPRequestWrapper extends HttpServletRequestWrapper {

	private ServletInputStream is;
	private BufferedReader reader;

	public GZIPRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.is = new GZIPRequestStream(getRequest().getInputStream());
		this.reader = new BufferedReader(new InputStreamReader(this.is));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException { return this.is; }

	@Override
	public BufferedReader getReader() throws IOException { return this.reader; }

}