package com.resource.Oauthresourceserver.config;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConfigCorsFilter  implements Filter {
    public ConfigCorsFilter(){}
    @Override
    public void init(FilterConfig filterConfig){}
    @Override
    public void destroy(){}
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin","http://localhost:9227");
        res.setHeader("Access-Control-Allow-Methods","POST,GET");
        res.setHeader("Access-Control-Allow-Max-Age","3600");
        res.setHeader("Access-Control-Allow-Headers","authorization,content-type");

        if("OPTIONS".equalsIgnoreCase(req.getMethod())){
            res.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(request,response);
        }

    }

}

