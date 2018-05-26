package com.sumscope.security.spring_security.Utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xuejian.sun
 * @date 2018/5/26
 */
@Slf4j
public final class BeanUtil {

    private BeanUtil(){}

    public static <T> T cloneBean(T t){
        T t1 = null;
        try {
            t1 = (T) BeanUtils.cloneBean(t);
        } catch (IllegalAccessException e) {
            log.error("",e);
        } catch (InstantiationException e) {
            log.error("",e);
        } catch (InvocationTargetException e) {
            log.error("",e);
        } catch (NoSuchMethodException e) {
            log.error("",e);
        }
        return t1;
    }
}
