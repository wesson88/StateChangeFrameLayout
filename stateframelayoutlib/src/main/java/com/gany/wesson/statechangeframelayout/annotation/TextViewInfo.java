package com.gany.wesson.statechangeframelayout.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout.annotation
 * fileName TextViewInfo
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 18:27
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TextViewInfo {

    String value();
}
