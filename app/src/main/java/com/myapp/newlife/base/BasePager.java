package com.myapp.newlife.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.myapp.newlife.MainActivity;
import com.myapp.newlife.R;


/**
 * 五个标签页的基类
 * 
 * @author Kevin
 * @date 2015-8-10
 */
public abstract class BasePager {

	public Activity mActivity;

	public View mRootView;

	public TextView tvTitle;
	public ImageButton btnMenu;
	public FrameLayout flContent;

	public BasePager(Activity activity) {
		mActivity = activity;
		initView();
	}

	/**
	 * 初始化布局
	 */
	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		btnMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}
		});
	}

	private void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();// 开关(如果状态为开,它就关;如果状态为关,它就开)
	}

	/**
	 * 初始化数据
	 */
	public abstract void initData();
}
