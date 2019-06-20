package com.vivo.web.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：cookie管理测试控制器
 *
 * @author 汤旗
 * @date 2019-06-20 21:07
 */
@RequestMapping("/cookie")
@RestController
public class CookieManageController {


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletResponse response) {
        Cookie cookie = new Cookie("target-token", "12345678");
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return "add";
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public String del(HttpServletResponse response) {
        Cookie cookie = new Cookie("target-token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "del";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpServletRequest request) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("target-token".equalsIgnoreCase(cookie.getName())){
                cookieValue = cookie.getValue();
                break;
            }
        }
        return cookieValue;
    }


}
