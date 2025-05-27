package com.example.adminsystem.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        logger.info("Request URL::{} | HTTP Method::{} | Start Time::{}",
                    request.getRequestURL(), request.getMethod(), startTime);
        return true; // Continue processing
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // This method is called after the handler method has been invoked but before the view is rendered.
        // Not typically used for logging response body, as response might not be committed yet.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        logger.info("Request URL::{} | HTTP Method::{} | Status::{} | Time Taken::{}ms",
                    request.getRequestURL(), request.getMethod(), response.getStatus(), executeTime);

        if (ex != null) {
            logger.error("Request URL::{} | Exception::{}", request.getRequestURL(), ex.getMessage(), ex);
        }
    }
}
