package com.gany.wesson.statechangeframelayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.gany.wesson.statechangeframelayout.factory.ExceptionTypeFactoryImpl;

import java.util.ArrayList;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout
 * fileName LayoutStateManager
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 13:11
 */
public class LayoutStateManager {

    final Context context;

    final int loadingLayoutId;
    final int normalLayoutId;
    final int[] exceptionLayoutIds;
    final int[] textViewIds;
    final int[] imageViewIds;

    final int backgroundSourceById;
    final int backgroundColorById;
    final int layoutWidth;
    final int layoutHeight;

    final Drawable backgroundSource;

    private final BaseStateLayout rootLayout;
    final SparseArray<String> exceptionLayoutArray;
    final SparseArray<String> textViewArray;
    final SparseArray<String> imageViewArray;

    final OnRetryListener onRetryListener;

    private ArrayList<View> textViews;
    private ArrayList<View> imageViews;

    public static LayoutStateBuilder newBuilder(Context context) {
        return new LayoutStateBuilder(context);
    }


    private LayoutStateManager(LayoutStateBuilder builder) {
        this.context = builder.context;

        this.loadingLayoutId = builder.loadingLayoutId;
        this.normalLayoutId = builder.normalLayoutId;
        this.exceptionLayoutIds = builder.exceptionLayoutIds;
        this.textViewIds = builder.textViewIds;
        this.imageViewIds = builder.imageViewIds;
        this.backgroundSource = builder.backgroundSource;
        this.backgroundSourceById = builder.backgroundSourceById;
        this.backgroundColorById = builder.backgroundColorById;
        this.layoutWidth = builder.layoutWidth;
        this.layoutHeight = builder.layoutHeight;

        this.onRetryListener = builder.onRetryListener;

        exceptionLayoutArray = new SparseArray<>();
        textViewArray = new SparseArray<>();
        imageViewArray = new SparseArray<>();

        String[] exceptionTypes = new ExceptionTypeFactoryImpl().getExceptionType(context, builder);
        String[] textViewInfo = new ExceptionTypeFactoryImpl().getTextViewInfo(context, builder);
        String[] imageViewInfo = new ExceptionTypeFactoryImpl().getImageViewInfo(context, builder);

        for (int i = 0; i < exceptionLayoutIds.length; i++) {
            exceptionLayoutArray.put(exceptionLayoutIds[i], exceptionTypes[i]);
        }

        for (int i = 0; i < textViewIds.length; i++) {
            textViewArray.put(textViewIds[i], textViewInfo[i]);
        }

        for (int i = 0; i < imageViewIds.length; i++) {
            imageViewArray.put(imageViewIds[i], imageViewInfo[i]);
        }

        rootLayout = new BaseStateLayout(this.context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(this.layoutWidth, this.layoutHeight);
        rootLayout.setLayoutParams(layoutParams);

        if (backgroundColorById != 0) {
            rootLayout.setBackgroundColor(this.backgroundColorById);
        }

        if (backgroundSourceById != 0 && backgroundSource != null) {
            throw new IllegalArgumentException("Just one background can be pick up");
        }

        if (backgroundSource != null) {
            rootLayout.setBackground(backgroundSource);
        } else if (backgroundSourceById != 0) {
            rootLayout.setBackground(this.context.getDrawable(backgroundSourceById));
        }

        rootLayout.setLayoutStateManager(this);
    }

    public View getRootLayout() {
        return rootLayout;
    }

    private boolean isLoading() {
        return rootLayout.isLoading(this.loadingLayoutId);
    }

    public void showLoadingState() {
        if (!isLoading()) {
            rootLayout.showLoading(this.loadingLayoutId);
        }
    }

    public void dismissLoadingState() {
        if (isLoading()) {
            rootLayout.hideLoading(this.loadingLayoutId);
        }
    }

    public void showNormalLayout() {
        rootLayout.showNormalState(this.normalLayoutId);
    }


    @Nullable
    public BaseStateLayout getExceptionLayout(@LayoutRes int layoutId) {
        View view = rootLayout.setExceptionLayout(getExceptionType(layoutId), exceptionLayoutArray);

        textViews = new ArrayList<>();
        imageViews = new ArrayList<>();

        if (view != null) {
            for (int index = 0; index < textViewArray.size(); index++) {
                if (TextUtils.equals(getExceptionType(layoutId).toLowerCase(),
                        textViewArray.get(textViewArray.keyAt(index))
                                .substring(0, getExceptionType(layoutId).length()).toLowerCase())) {
                    textViews.add(view.findViewById(textViewArray.keyAt(index)));
                }
            }

            for (int i = 0; i < imageViewArray.size(); i++) {
                if (TextUtils.equals(getExceptionType(layoutId).toLowerCase(),
                        imageViewArray.get(imageViewArray.keyAt(i))
                                .substring(0, getExceptionType(layoutId).length()).toLowerCase())) {
                    view.findViewById(imageViewArray.keyAt(i)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onRetryListener.onRetry();
                        }
                    });
                    imageViews.add(view.findViewById(imageViewArray.keyAt(i)));
                }
            }

            return rootLayout;
        }

        return null;
    }

    public void setTextView(String... texts) {
        if (texts.length != textViews.size()) {
            throw new IllegalArgumentException("text array size is not match textView size");
        } else {
            int i = 0;
            for (String text : texts) {
                ((TextView) textViews.get(i++)).setText(text);
            }
        }
    }

    public void setImageView(Drawable... images) {
        if (images.length != imageViews.size()) {
            throw new IllegalArgumentException("drawable array size is not match imageView size");
        } else {
            int i = 0;
            for (Drawable drawable : images) {
                imageViews.get(i++).setBackground(drawable);
            }
        }
    }

    public void showExceptionLayout(@LayoutRes int layoutId) {
        rootLayout.showHideViewById(layoutId);
    }

    private String getExceptionType(int id) {
        return exceptionLayoutArray.get(id);
    }

    public static final class LayoutStateBuilder {
        private Context context;

        private int loadingLayoutId;
        private int normalLayoutId;
        private int[] exceptionLayoutIds;
        private int[] textViewIds;
        private int[] imageViewIds;
        private int backgroundSourceById;
        private int backgroundColorById;
        private int layoutWidth;
        private int layoutHeight;

        private Drawable backgroundSource;

        private OnRetryListener onRetryListener;

        LayoutStateBuilder(Context context) {
            this.context = context;
        }

        /**
         * 自定义加载布局
         *
         * @param loadingLayoutId loadingLayoutId
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setLoadingLayout(@LayoutRes int loadingLayoutId) {
            this.loadingLayoutId = loadingLayoutId;
            return this;
        }

        /**
         * 自定义标准布局
         *
         * @param normalLayoutId normalLayoutId
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setNormalLayout(@LayoutRes int normalLayoutId) {
            this.normalLayoutId = normalLayoutId;
            return this;
        }


        /**
         * 自定义异常布局
         *
         * @param exceptionLayoutIds exceptionLayoutId
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setExceptionLayout(@LayoutRes int... exceptionLayoutIds) {
            this.exceptionLayoutIds = exceptionLayoutIds;
            return this;
        }

        /**
         * 设置TextView
         *
         * @param textViewIds textView ids
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setTexViewForLayout(@IdRes int... textViewIds) {
            this.textViewIds = textViewIds;
            return this;
        }

        /**
         * 设置ImageView
         *
         * @param imageViewIds imageView ids
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setImageViewForLayout(@IdRes int... imageViewIds) {
            this.imageViewIds = imageViewIds;
            return this;
        }

        /**
         * 自定义布局背景色
         *
         * @param backgroundColorById background color
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setBackgroundColorById(@ColorRes int backgroundColorById) {
            this.backgroundColorById = backgroundColorById;
            return this;
        }

        /**
         * 自定义布局背景
         *
         * @param backgroundSourceById background source
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setBackground(@DrawableRes int backgroundSourceById) {
            this.backgroundSourceById = backgroundSourceById;
            return this;
        }


        /**
         * 自定义布局背景
         *
         * @param backgroundSource background source
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setBackground(Drawable backgroundSource) {
            this.backgroundSource = backgroundSource;
            return this;
        }

        /**
         * 自定义布局宽度
         *
         * @param layoutWidth layout width
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setLayoutWidth(int layoutWidth) {
            this.layoutWidth = layoutWidth;
            return this;
        }

        /**
         * 自定义布局高度
         *
         * @param layoutHeight layout height
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder setLayoutHeight(int layoutHeight) {
            this.layoutHeight = layoutHeight;
            return this;
        }


        /**
         * 自定义RetryListener
         *
         * @param onRetryListener onRetryListener
         * @return LayoutStateBuilder
         */
        public LayoutStateBuilder onRetryListener(OnRetryListener onRetryListener) {
            this.onRetryListener = onRetryListener;
            return this;
        }

        /**
         * 生成对象
         *
         * @return LayoutStateManager
         */
        public LayoutStateManager build() {
            return new LayoutStateManager(this);
        }
    }
}
