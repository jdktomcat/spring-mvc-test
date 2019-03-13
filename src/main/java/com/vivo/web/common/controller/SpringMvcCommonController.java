package com.vivo.web.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类描述：测试控制类
 *
 * @author 汤旗
 * @date 2019-03-02
 */
@Controller
@RequestMapping(value = "/common")
public class SpringMvcCommonController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

}
