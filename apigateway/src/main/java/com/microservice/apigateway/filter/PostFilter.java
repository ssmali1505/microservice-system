package com.microservice.apigateway.filter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.log.LogMessage;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class PostFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(PostFilter.class);

	@Value("${recording.file:C:/Users/hp/Documents/Nitor/SpringBoot/apigateway/record.txt}")
	private String recordFile;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String url = request.getRequestURL().toString();
		if (!url.contains("swagger")) {
			logger.info(String.format("Post -> %s request to %s", request.getMethod(), url));
			log(ctx);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	private void log(RequestContext ctx) {
		String requestData = "N/A";
		HttpServletRequest request = ctx.getRequest();
		try {

			if (request.getContentLength() > 0) {
				requestData = CharStreams.toString(request.getReader());
			}

		} catch (Exception e) {

			logger.error("Error parsing request", e);

		}

		try {

			String line = String.format("Request, %s,%s,%s \r\n", request.getRequestURL(), request.getMethod(),
					requestData);

			BufferedWriter bw = Files.newBufferedWriter(Paths.get(recordFile), Charset.forName("UTF-8"),
					StandardOpenOption.APPEND);

			bw.write(line);

			bw.close();

		} catch (IOException e) {

			logger.error("Error writing request", e);

		}

		final InputStream responseDataStream = ctx.getResponseDataStream();
		if (responseDataStream != null) {
			try {

				final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));

				if (responseData != null) {
					try {

						String line = String.format("Response, %s \r\n", responseData);

						BufferedWriter bw = Files.newBufferedWriter(Paths.get(recordFile),

								Charset.forName("UTF-8"), StandardOpenOption.APPEND);

						bw.write(line);

						bw.close();

					} catch (IOException e) {

					}
				}
				ctx.setResponseBody(responseData);

			} catch (IOException e) {

			}
		}
	}
}
