package com.myapp.newlife.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.myapp.newlife.MainActivity;
import com.myapp.newlife.base.BaseMenuDetailPager;
import com.myapp.newlife.base.BasePager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.myapp.newlife.base.impl.menudetail.InteractMenuDetailPager;
import com.myapp.newlife.base.impl.menudetail.NewsMenuDetailPager;
import com.myapp.newlife.base.impl.menudetail.PhotosMenuDetailPager;
import com.myapp.newlife.base.impl.menudetail.TopicMenuDetailPager;
import com.myapp.newlife.entity.NewsMenuData;
import com.myapp.newlife.global.Constants;
import com.myapp.newlife.utils.CacheUtils;

import java.util.ArrayList;

/**
 * 新闻中心
 *
 */
public class NewsCenterPager extends BasePager {
	// 菜单详情页集合
	private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;
	private NewsMenuData mNewsMenuData;// 新闻分类信息网络数据
	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTitle.setText("新闻");
		String cache = CacheUtils.getCache(Constants.CATEGORIES_URL, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			// 有缓存
			System.out.println("发现缓存....");
			processResult(cache);
		}
		getDataFromServer();
	}
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, Constants.CATEGORIES_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 请求成功
						String result = responseInfo.result;// 获取json字符串
						System.out.println("result:" + result);
						processResult(result);
						// 写缓存
						CacheUtils.setCache(Constants.CATEGORIES_URL, result,
								mActivity);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 请求失败
						error.printStackTrace();
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}
	protected void processResult(String result) {
		// gson->json
		Gson gson = new Gson();
		mNewsMenuData = gson.fromJson(result, NewsMenuData.class);
		System.out.println("解析结果:" + mNewsMenuData);

		// 获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
		// 将网络数据设置给侧边栏
		leftMenuFragment.setData(mNewsMenuData.data);
//
//		// 初始化4个菜单详情页
		mMenuDetailPagers = new ArrayList<>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsMenuData.data.get(0).children));
		mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
		mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
		mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

		// 菜单详情页-新闻作为初始页面
		setCurrentMenuDetailPager(0);
	}
	protected void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);
		// 移除之前所有的view对象, 清理屏幕
		flContent.removeAllViews();
		flContent.addView(pager.mRootView);
		pager.initData();// 初始化数据

		// 更改标题
		tvTitle.setText(mNewsMenuData.data.get(position).title);
	}
}
