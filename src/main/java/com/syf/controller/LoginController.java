package com.syf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
public class LoginController {

    public static Map<String, Long> loginInfoMap=new HashMap<String, Long>();

    @Value("${exLoginName}")
    String exLoginName;
    @Value("${exUserPwd}")
    String exUserPwd;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("loginErr")
    public String loginErr() {
        return "loginErr";
    }

    @RequestMapping("loginByPwd")
    public String loginByPwd() {
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String ip = getIpAddress(request);
        log.info("客户端IP：" + ip);

        if (exLoginName.equals(loginName) && exUserPwd.equals(password)) {
            loginInfoMap.put(ip, System.currentTimeMillis());
            return "redirect:/index";
        }
        return "redirect:/loginErr";
    }

    /**
     * 从request中获取请求方IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
