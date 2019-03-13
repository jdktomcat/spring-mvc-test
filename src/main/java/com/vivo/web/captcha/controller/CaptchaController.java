package com.vivo.web.captcha.controller;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.vivo.web.captcha.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 类描述：验证码控制器
 *
 * @author 汤旗
 * @date 2019-03-01
 */
@Controller
@RequestMapping("captcha")
public class CaptchaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);

    @Resource
    private GenericManageableCaptchaService captchaService;

    /**
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // the output stream to render the com.vivo.web.captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        // get the session id that will identify the generated com.vivo.web.captcha.
        // the same id must be used to validate the response, the session id is a good candidate!
        String captchaId = UUID.randomUUID().toString();
        // call the ImageCaptchaService getChallenge method
        BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
        response.addCookie(new Cookie("cookie-uuid", captchaId));
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(challenge, "jpeg", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 验证验证码
     */
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo validCaptcha(String captcha, String uuid) {
        if (StringUtils.isEmpty(captcha)) {
            return new ResultVo(false, "验证码不能为空", new Date());
        }
        if (StringUtils.isEmpty(uuid)) {
            return new ResultVo(false, "唯一标识码不能为空", new Date());
        }
        if (!captchaService.validateResponseForID(uuid, captcha)) {
            return new ResultVo(false, "验证码错误", new Date());
        }
        return new ResultVo(true, "验证成功", new Date());
    }
}
