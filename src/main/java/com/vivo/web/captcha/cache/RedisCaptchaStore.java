package com.vivo.web.captcha.cache;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

/**
 * 类描述：验证码Redis缓存组件
 *
 * @author 汤旗
 * @date 2019-03-01
 */
public class RedisCaptchaStore implements CaptchaStore {

    private static final Logger CAPTCHA_LOG = LoggerFactory.getLogger(RedisCaptchaStore.class);

    private static final String CAPTCHA_SESSION_KEY = "com:commercial:captcha";

    private RedisTemplate<String, Serializable> redisTemplate;

    private HashOperations<String, String, Serializable> hashOperations;

    @Override
    public boolean hasCaptcha(String sid) {
        return this.hashOperations.hasKey(CAPTCHA_SESSION_KEY, sid);
    }

    @Override
    public void storeCaptcha(String sid, Captcha captcha) throws CaptchaServiceException {
        this.hashOperations.put(CAPTCHA_SESSION_KEY, sid, captcha);
    }

    @Override
    public void storeCaptcha(String sid, Captcha captcha, Locale locale) throws CaptchaServiceException {
        this.hashOperations.put(CAPTCHA_SESSION_KEY, sid, new CaptchaAndLocale(captcha, locale));
    }

    @Override
    public boolean removeCaptcha(String sid) {
        if (this.hasCaptcha(sid)) {
            this.hashOperations.delete(CAPTCHA_SESSION_KEY, sid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Captcha getCaptcha(String sid) throws CaptchaServiceException {
        Object val = this.hashOperations.get(CAPTCHA_SESSION_KEY, sid);
        if (val == null) {
            return null;
        }
        if (val instanceof Captcha) {
            return (Captcha) val;
        }
        if (val instanceof CaptchaAndLocale){
            return ((CaptchaAndLocale) val).getCaptcha();
        }
        return null;
    }

    @Override
    public Locale getLocale(String sid) throws CaptchaServiceException {
        Object captchaAndLocale = this.getCaptcha(sid);
        if (captchaAndLocale != null && captchaAndLocale instanceof CaptchaAndLocale) {
            return ((CaptchaAndLocale) captchaAndLocale).getLocale();
        }
        return null;
    }

    @Override
    public int getSize() {
        return Math.toIntExact(this.hashOperations.size(CAPTCHA_SESSION_KEY));
    }

    @Override
    public Collection getKeys() {
        return this.hashOperations.keys(CAPTCHA_SESSION_KEY);
    }

    @Override
    public void empty() {
        Collection<String> keys = this.getKeys();
        if (!keys.isEmpty()) {
            this.hashOperations.delete(CAPTCHA_SESSION_KEY, keys.toArray());
        }
    }

    @Override
    public void initAndStart() {
        this.empty();
    }

    @Override
    public void cleanAndShutdown() {
        this.empty();
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
}
