package cn.goat.oms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zhaoGang
 * @Date 2021/8/3 15:23
 * 防重复提交注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResubmitAnnotation {

    /**
     * 重复时间，第一次时间和第二次请求时间相隔acquireTimeout毫秒算重复
     *
     * @return
     */
    long acquireTimeout() default 1000;

    /**
     * 加锁超时时间，超过lockTimeout秒，就不再尝试获取锁
     *
     * @return
     */
    long lockTimeout() default 2000;
}
