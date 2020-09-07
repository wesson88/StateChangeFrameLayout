package com.gany.wesson.statechangeframelayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * project StateChangeFrameLayout
 * package com.gany.wesson.statechangeframelayout
 * fileName BaseStateLayout
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/4 13:24
 */
public class BaseStateLayout extends FrameLayout {

    /**
     * loading 加载id
     */
    public static final int LAYOUT_LOADING_ID = 1;

    /**
     * 内容id
     */
    public static final int LAYOUT_NORMAL_ID = 2;

    /**
     * 布局管理器
     */
    private LayoutStateManager mLayoutStateManager;

    /**
     * 存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray<>();

    private SparseArray<ViewStub> viewStubSparseArray = new SparseArray<>();


    public BaseStateLayout(@NonNull Context context) {
        super(context);
    }

    public BaseStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutStateManager(LayoutStateManager layoutStateManager) {
        mLayoutStateManager = layoutStateManager;
        addLayoutInRoot();
    }

    public boolean isLoading(int loadingId) {
        View view = layoutSparseArray.get(loadingId);
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        } else {
            return false;
        }
    }

    public void showLoading(int loadingId) {
        if (layoutSparseArray.get(loadingId) != null) {
            showHideViewById(loadingId);
        }
    }

    public void hideLoading(int loadingId) {
        if (layoutSparseArray.get(loadingId) != null) {
            View view = layoutSparseArray.get(loadingId);
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public void showNormalState(int normalId) {
        if (layoutSparseArray.get(normalId) != null) {
            showHideViewById(normalId);
        }
    }

    @Nullable
    public View setExceptionLayout(String exception, SparseArray<String> arrays) {
        for (int i = 0; i < arrays.size(); i++) {
            if (TextUtils.equals(exception, arrays.get(arrays.keyAt(i)))) {
                if (viewStubSparseArray.get(arrays.keyAt(i)) != null) {
                    View view = viewStubSparseArray.get(arrays.keyAt(i)).inflate();
                    viewStubSparseArray.remove(arrays.keyAt(i));
                    layoutSparseArray.put(arrays.keyAt(i), view);
                    return view;
                } else {
                    return layoutSparseArray.get(arrays.keyAt(i));
                }
            }
        }

        return null;
    }

    private void addLayoutInRoot() {
        // 添加正常视图
        if (mLayoutStateManager.normalLayoutId != 0) {
            addLayoutById(mLayoutStateManager.normalLayoutId, BaseStateLayout.LAYOUT_NORMAL_ID);
        }
        // 添加加载视图
        if (mLayoutStateManager.loadingLayoutId != 0) {
            addLayoutById(mLayoutStateManager.loadingLayoutId, BaseStateLayout.LAYOUT_LOADING_ID);
        }
        // 添加异常视图
        if (mLayoutStateManager.exceptionLayoutIds.length != 0) {
            for (int id : mLayoutStateManager.exceptionLayoutIds) {
                mLayoutStateManager.exceptionLayoutArray.get(id);
                ViewStub viewStub = new ViewStub(mLayoutStateManager.context);
                viewStub.setLayoutResource(id);
                addView(viewStub);

                viewStubSparseArray.put(id, viewStub);
            }
        }
    }


    private void addLayoutById(@LayoutRes int layoutId, int id) {
        View view = LayoutInflater.from(mLayoutStateManager.context).inflate(layoutId, null);
        if (id == BaseStateLayout.LAYOUT_LOADING_ID) {
            view.setOnClickListener(null);
        }
        layoutSparseArray.put(layoutId, view);
        addView(view);
    }

    public void showHideViewById(int id) {
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);

            if (key == id) {
                valueView.setVisibility(View.VISIBLE);
            } else {
                valueView.setVisibility(View.GONE);
            }
        }
    }
}
