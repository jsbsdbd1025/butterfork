package com.jiang.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by knowing on 2017/11/8.
 */

@Retention(CLASS)
@Target(FIELD)
public @interface BindView {
    /**
     * View ID to which the field will be bound.
     */
    @IdRes int value();
}


/**
 * 元注解
 * 1. Retention
 * <p>
 * <p> SOURCE  只在源码中可见 </p>
 * <p> CLASS   存在于编译后的class文件中 </p>
 * <p> RUNTIME 运行时可见 </p>
 * <p>
 * 2. Target
 * <p>
 * <p>相当于作用域：BindView中使用FIELD作用于变量，OnClick中使用METHOD作用于方法</p>
 */
