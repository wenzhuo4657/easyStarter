package org.example.filter;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class logfilter extends OncePerRequestFilter {

//获取具有唯一性的traaceid，用于在日志中标识http请求

    private  final static  String MDC_TRACE_ID="traceId";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceIDStr=getMdcTreaceID();
        MDC.put(MDC_TRACE_ID,traceIDStr);
        filterChain.doFilter(request,response);
        MDC.clear();
    }

    private String getMdcTreaceID() {
        long currentTime=System.nanoTime();
        return  String.join("_",MDC_TRACE_ID,String.valueOf(currentTime));
    }


}
