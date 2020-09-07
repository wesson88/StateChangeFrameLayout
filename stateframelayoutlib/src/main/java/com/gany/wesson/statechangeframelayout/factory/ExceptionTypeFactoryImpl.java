package com.gany.wesson.statechangeframelayout.factory;

import android.content.Context;

import com.gany.wesson.statechangeframelayout.LayoutStateManager;
import com.gany.wesson.statechangeframelayout.annotation.ImageViewInfo;
import com.gany.wesson.statechangeframelayout.annotation.LayoutInfo;
import com.gany.wesson.statechangeframelayout.annotation.TextViewInfo;

import java.lang.reflect.Field;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout.factory
 * fileName ExceptionTypeFactoryImpl
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 14:05
 */
public class ExceptionTypeFactoryImpl implements ExceptionTypeFactory {
    /**
     * 获取exception type
     *
     * @return type
     */
    @Override
    public String[] getExceptionType(Context context, LayoutStateManager.LayoutStateBuilder builder) {
        int exceptionType = 0;
        int length = 0;

        for (Field field : context.getClass().getDeclaredFields()) {
            LayoutInfo annotation = field.getAnnotation(LayoutInfo.class);
            if (annotation != null) {
                length++;
            }
        }

        String[] parameterNames = new String[length];

        for (Field field : context.getClass().getDeclaredFields()) {
            LayoutInfo annotation = field.getAnnotation(LayoutInfo.class);
            if (annotation != null) {
                parameterNames[exceptionType++] = annotation.value();
            }
        }

        return parameterNames;
    }

    /**
     * 获取TextView来源
     *
     * @param builder LayoutStateManager.LayoutStateBuilder builder
     * @return textView来源
     */
    @Override
    public String[] getTextViewInfo(Context context, LayoutStateManager.LayoutStateBuilder builder) {
        int textViews = 0;
        int length = 0;

        for (Field field : context.getClass().getDeclaredFields()) {
            TextViewInfo annotation = field.getAnnotation(TextViewInfo.class);
            if (annotation != null) {
                length++;
            }
        }

        String[] parameterNames = new String[length];

        for (Field field : context.getClass().getDeclaredFields()) {
            TextViewInfo annotation = field.getAnnotation(TextViewInfo.class);
            if (annotation != null) {
                parameterNames[textViews++] = annotation.value();
            }
        }

        return parameterNames;
    }

    /**
     * 获取ImageView来源
     *
     * @param builder LayoutStateManager.LayoutStateBuilder builder
     * @return ImageView来源
     */
    @Override
    public String[] getImageViewInfo(Context context, LayoutStateManager.LayoutStateBuilder builder) {
        int imageViews = 0;
        int length = 0;

        for (Field field : context.getClass().getDeclaredFields()) {
            ImageViewInfo annotation = field.getAnnotation(ImageViewInfo.class);
            if (annotation != null) {
                length++;
            }
        }

        String[] parameterNames = new String[length];

        for (Field field : context.getClass().getDeclaredFields()) {
            ImageViewInfo annotation = field.getAnnotation(ImageViewInfo.class);
            if (annotation != null) {
                parameterNames[imageViews++] = annotation.value();
            }
        }

        return parameterNames;
    }
}
