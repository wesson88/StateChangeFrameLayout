package com.gany.wesson.teststateframelayoutdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gany.wesson.statechangeframelayout.LayoutStateManager;

/**
 * project StateChangeFrame
 * package com.gany.wesson.teststateframelayoutdemo
 * fileName BaseActivity
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/7 10:09
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutStateManager layoutStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_main_layout);
        initStateLayout();
        initBaseView();
        initView();
        dismissLoading();
    }


    protected abstract void initStateLayout();

    protected abstract void initView();


    private void initBaseView() {
        ConstraintLayout baseLayout = findViewById(R.id.cl_main);
        baseLayout.addView(layoutStateManager.getRootLayout());
    }

    protected void showNormalLayout() {
        layoutStateManager.showNormalLayout();
    }

    protected void showLoading() {
        layoutStateManager.showLoadingState();
    }

    protected void dismissLoading() {
        layoutStateManager.dismissLoadingState();
    }
}
