package com.gany.wesson.statechangeframelayout.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout.annotation
 * fileName LayoutInfo
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 13:55
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LayoutInfo {

    String value();
}
