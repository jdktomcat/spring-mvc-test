package com.vivo.web.common.util;

import java.lang.reflect.Field;

/**
 * 类描述：属性工具类
 *
 * @author 汤旗
 * @date 2019-08-22 19:03
 */
public class FieldUtil {

    public static Field getObjectField(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field;
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T getObjectFieldValue(Field field, Object object, Class<T> clazz) {
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception ex) {
            return null;
        }
    }
}
