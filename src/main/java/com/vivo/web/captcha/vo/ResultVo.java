package com.vivo.web.captcha.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类描述：操作描述类
 *
 * @author 汤旗
 * @date 2019-03-02
 */
public class ResultVo {

    private boolean result;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date now;

    public ResultVo(boolean result, String message, Date now) {
        this.result = result;
        this.message = message;
        this.now = now;
    }


    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
