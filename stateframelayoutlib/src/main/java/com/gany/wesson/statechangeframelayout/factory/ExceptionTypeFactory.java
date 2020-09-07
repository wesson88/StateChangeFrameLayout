package com.gany.wesson.statechangeframelayout.factory;

import android.content.Context;

import com.gany.wesson.statechangeframelayout.LayoutStateManager;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout.factory
 * fileName ExceptionTypeFactory
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 14:03
 */
public interface ExceptionTypeFactory {


    /**
     * 获取异常类型
     *
     * @param context context
     * @param builder LayoutStateManager.LayoutStateBuilder
     * @return 异常类型
     */
    String[] getExceptionType(Context context, LayoutStateManager.LayoutStateBuilder builder);

    /**
     * 获取TextView来源
     *
     * @param context context
     * @param builder LayoutStateManager.LayoutStateBuilder builder
     * @return textView来源
     */
    String[] getTextViewInfo(Context context, LayoutStateManager.LayoutStateBuilder builder);


    /**
     * 获取ImageView来源
     *
     * @param context context
     * @param builder LayoutStateManager.LayoutStateBuilder builder
     * @return ImageView来源
     */
    String[] getImageViewInfo(Context context, LayoutStateManager.LayoutStateBuilder builder);
}
