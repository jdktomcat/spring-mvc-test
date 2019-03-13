package com.vivo.web.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

import java.util.Locale;

/**
 * 类描述：验证码服务类
 *
 * @author 汤旗
 * @date 2019-03-01
 */
public class ManageableCaptchaService extends GenericManageableCaptchaService {

    public ManageableCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    /**
     * 重写validateResponseForID方法,默认每次经过该操作都会删除对应的Captcha
     *
     * @param ID       SessionID
     * @param response 提交的验证码参数值
     */
    @Override
    public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {
        if (!this.store.hasCaptcha(ID)) {
            return false;
        }
        Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
        //如果验证成功，移除Captcha
        if (valid) {
            this.store.removeCaptcha(ID);
        }
        return valid;
    }

    /**
     * 每次调用都重新生成
     * @param ID
     * @param locale
     * @return
     * @throws CaptchaServiceException
     */
    @Override
    public Object getChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        Captcha captcha;
        if(!this.store.hasCaptcha(ID)) {
            this.store.removeCaptcha(ID);
        }
        captcha = this.generateAndStoreCaptcha(locale, ID);
        if(captcha == null) {
            captcha = this.generateAndStoreCaptcha(locale, ID);
        } else if(captcha.hasGetChalengeBeenCalled()) {
            captcha = this.generateAndStoreCaptcha(locale, ID);
        }
        Object challenge = this.getChallengeClone(captcha);
        captcha.disposeChallenge();
        return challenge;
    }

}
