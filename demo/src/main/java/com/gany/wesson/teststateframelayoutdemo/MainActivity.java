package com.gany.wesson.teststateframelayoutdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gany.wesson.statechangeframelayout.annotation.ImageViewInfo;
import com.gany.wesson.statechangeframelayout.annotation.LayoutInfo;
import com.gany.wesson.statechangeframelayout.LayoutStateManager;
import com.gany.wesson.statechangeframelayout.OnRetryListener;
import com.gany.wesson.statechangeframelayout.annotation.TextViewInfo;

/**
 * project StateChangeFrame
 * package com.gany.wesson.teststateframelayoutdemo
 * fileName MainActivity
 *
 * @author GanYu
 * describe TODO
 * @date 2020/9/7 10:39
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btnEmpty;
    Button btnNonNetwork;
    Button btnErrorNetwork;


    @LayoutInfo("emptyLayout")
    private int emptyLayout = R.layout.layout_empty;
    @LayoutInfo("nonNetworkLayout")
    private int nonNetworkLayout = R.layout.layout_non_network;
    @LayoutInfo("errorNetworkLayout")
    private int errorNetworkLayout = R.layout.layout_error_network;
    @ImageViewInfo("emptyLayout/iv_empty")
    private int ivEmpty = R.id.iv_empty;
    @ImageViewInfo("nonNetworkLayout/iv_non_network")
    private int ivNonNetwork = R.id.iv_non_network;
    @ImageViewInfo("errorNetWorkLayout/error_network")
    private int ivErrorNetwork = R.id.iv_error_network;
    @TextViewInfo("emptyLayout/tv_empty")
    private int tvEmpty = R.id.tv_empty;
    @TextViewInfo("nonNetworkLayout/tv_non_network")
    private int tvNonNetwork = R.id.tv_non_network;
    @TextViewInfo("errorNetworkLayout/tv_error_network")
    private int tvErrorNetwork = R.id.tv_error_network;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStateLayout() {
        layoutStateManager = LayoutStateManager.newBuilder(this)
                .setLayoutWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setLayoutHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setBackgroundColorById(R.color.white)
                .setNormalLayout(R.layout.activity_main)
                .setLoadingLayout(R.layout.layout_loading)
                .setExceptionLayout(emptyLayout, nonNetworkLayout, errorNetworkLayout)
                .setImageViewForLayout(ivEmpty, ivNonNetwork, ivErrorNetwork)
                .setTexViewForLayout(tvEmpty, tvNonNetwork, tvErrorNetwork)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        layoutStateManager.showNormalLayout();
                    }
                })
                .build();
    }

    @Override
    protected void initView() {
        btnEmpty = findViewById(R.id.btn_empty);
        btnEmpty.setText("空页面");
        btnEmpty.setOnClickListener(this);
        btnNonNetwork = findViewById(R.id.btn_non_network);
        btnNonNetwork.setText("无网络");
        btnNonNetwork.setOnClickListener(this);
        btnErrorNetwork = findViewById(R.id.btn_error_network);
        btnErrorNetwork.setText("网络加载错误");
        btnErrorNetwork.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_empty:
                Log.e("mainActivity", "click empty btn");
                if (layoutStateManager.getExceptionLayout(emptyLayout) != null) {
                    layoutStateManager.setImageView(getDrawable(R.drawable.view_icon_no_data));
                    layoutStateManager.setTextView("没有数据呢，重新刷新");
                    layoutStateManager.showExceptionLayout(emptyLayout);
                }
                break;
            case R.id.btn_non_network:
                Log.e("mainActivity", "click non network btn");
                if (layoutStateManager.getExceptionLayout(nonNetworkLayout) != null) {
                    layoutStateManager.setImageView(getDrawable(R.drawable.view_icon_network_error));
                    layoutStateManager.setTextView("没有网络，请设置网络");
                    layoutStateManager.showExceptionLayout(nonNetworkLayout);
                }
                break;
            case R.id.btn_error_network:
                Log.e("mainActivity", "click error network btn");
                if (layoutStateManager.getExceptionLayout(errorNetworkLayout) != null) {
                    layoutStateManager.setImageView(getDrawable(R.drawable.icon_network_error));
                    layoutStateManager.setTextView("数据错误");
                    layoutStateManager.showExceptionLayout(errorNetworkLayout);
                }
                break;
            default:
                break;
        }
    }
}
