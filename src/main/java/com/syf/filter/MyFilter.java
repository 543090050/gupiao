//package com.syf.filter;
//
//import com.syf.controller.LoginController;
//import lombok.extern.java.Log;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(filterName = "myFilter", urlPatterns = "/*")
//@Log
//public class MyFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info("do init ");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String uri = request.getRequestURI().toString();
//
//        if (uri.contains("login") || uri.contains("css") ||
//                uri.contains("js") || uri.contains("ico")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
////        log.info("request uri: " + uri);
//        String curIP = LoginController.getIpAddress(request);
//        Long loginTime = LoginController.loginInfoMap.get(curIP);
//        Long interval = Long.valueOf(1000 * 60 * 60 *3);//3小时
//        if (null == loginTime || (System.currentTimeMillis() - loginTime > interval)) {
//            String path = request.getContextPath();
//            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
//            log.info("跳转登录页:" + basePath + "/login");
//            response.sendRedirect(basePath + "/login");
//            return;
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
