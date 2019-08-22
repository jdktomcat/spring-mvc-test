package com.vivo.web.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 类描述：
 *
 * @author 汤旗
 * @date 2019-08-22 16:08
 */
@Service
public class InitAfterSpringContextService implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context 没有parent
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext instanceof XmlWebApplicationContext) {
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) applicationContext;
            System.out.println(xmlWebApplicationContext.getApplicationName());
        }
    }
}
