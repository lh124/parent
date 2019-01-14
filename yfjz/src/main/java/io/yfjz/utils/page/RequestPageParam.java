package io.yfjz.utils.page;


import java.lang.annotation.*;

/**
 * 设置使用分页时，设置默认值
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 11:43 2018/07/26
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPageParam {
    int page() default 1;

    int limit() default 10;

    int pageNo() default 1;

    int pageSize() default 10;

    int offset() default 0;
    String search() default "";
}
