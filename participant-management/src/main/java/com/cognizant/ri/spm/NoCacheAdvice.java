package com.cognizant.ri.spm;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Sets the 'Cache-Control' header to 'no-cache' for all controllers project
 * controllers.
 * 
 * Resources service from static/ will continue to be cached.
 * 
 * @author 107406
 *
 * @param <T>
 */
@Slf4j
@ControllerAdvice("com.cognizant")
public class NoCacheAdvice<T> implements ResponseBodyAdvice<T> {

	@Override
	public T beforeBodyWrite(T arg0, MethodParameter arg1, MediaType arg2,
			Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest request, ServerHttpResponse response) {
		log.debug("Setting no-cache for {}", request.getURI());
		response.getHeaders().add(HttpHeaders.CACHE_CONTROL, "no-cache");
		return arg0;
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}

}
