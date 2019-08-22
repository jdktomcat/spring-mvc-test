package com.vivo.web.config;

import com.vivo.web.interceptor.TestInterceptor;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author 汤旗
 * @date 2019-08-21 18:03
 */
@Configuration
public class WebConfigTest extends WebMvcConfigurationSupport {

    @Autowired
    private TestInterceptor testInterceptor;

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor).addPathPatterns("/**");
    }

    /**
     * Return a {@link RequestMappingHandlerMapping} ordered at 0 for mapping
     * requests to annotated controllers.
     */
    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        ApplicationContext applicationContext = getApplicationContext();
        Map<String, RequestMappingHandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RequestMappingHandlerMapping.class, true, false);
        if (!matchingBeans.isEmpty()) {
            List<RequestMappingHandlerMapping> handlerMappings = new ArrayList<>(matchingBeans.values());
            // We keep HandlerMappings in sorted order.
            AnnotationAwareOrderComparator.sort(handlerMappings);
            for (RequestMappingHandlerMapping handlerMapping : handlerMappings) {
                try {
                    Map<String, Field> fieldMap = new HashMap<>();
                    Class clazz = RequestMappingHandlerMapping.class;
                    do {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            fieldMap.put(field.getName(), field);
                        }
                        clazz = clazz.getSuperclass();
                    } while (clazz != Object.class);
                    Field targetField = fieldMap.get("adaptedInterceptors");
                    Object object = targetField.get(handlerMapping);
                    if (object instanceof List) {
                        List<HandlerInterceptor> adaptedInterceptors = (List<HandlerInterceptor>) object;
                        adaptedInterceptors.add(testInterceptor);
                        targetField.setAccessible(true);
                        targetField.set(handlerMapping, adaptedInterceptors);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
        return super.requestMappingHandlerMapping();
    }
}
