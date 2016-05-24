package com.myapp.newlife.base;

import android.app.Activity;
import android.view.View;

/**
 * 侧边栏菜单详情页基类
 * 
 * @author Kevin
 * @date 2015-8-11
 */
public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	// 菜单详情页根布局
	public View mRootView;

	public BaseMenuDetailPager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}

	public abstract View initView();

	public void initData() {

	}
}
