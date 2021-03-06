package com.myapp.newlife.base.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.myapp.newlife.MainActivity;
import com.myapp.newlife.R;
import com.myapp.newlife.base.BaseFragment;
import com.myapp.newlife.entity.NewsMenuData;

import java.util.ArrayList;

/**
 * Created by lijie on 2016/5/18.
 */


public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.lv_list)
    private ListView lvList;

    private ArrayList<NewsMenuData.NewsData> mMenuList;

    private MenuAdapter mAdapter;

    private int mCurrrentPos;// 当前被选中的菜单位置

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        ViewUtils.inject(this, view);
        return view;
    }
    public void setData(ArrayList<NewsMenuData.NewsData> data) {
        mMenuList = data;
        mAdapter = new MenuAdapter();
        lvList.setAdapter(mAdapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCurrrentPos = position;
                mAdapter.notifyDataSetChanged();
                // 通知新闻中心,切换页面
                setCurrentMenuDetailPager(position);
//                // 隐藏侧边栏
                toggle();
            }
        });

        mCurrrentPos = 0;//重置当前页面位置
    }
    protected void setCurrentMenuDetailPager(int position) {
        // 获取新闻中心对象NewsCenterPager
        // 1.先获取MainActivity,
        // 2.通过MainActiivty获取ContentFragment
        // 3.通过ContentFragment获取NewsCenterPager
        MainActivity mainUI = (MainActivity) mActivity;
        ContentFragment contentFragment = mainUI.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();

        // 给新闻中心页面的FrameLayout填充布局
        newsCenterPager.setCurrentMenuDetailPager(position);
    }
    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();// 开关(如果状态为开,它就关;如果状态为关,它就开)
    }
    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public NewsMenuData.NewsData getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);

            NewsMenuData.NewsData data = getItem(position);
            tvMenu.setText(data.title);

            if (mCurrrentPos == position) {
                // 如果当前要绘制的item刚好是被选中的, 需要设置为红色
                tvMenu.setEnabled(true);
            } else {
                // 其他item都是白色
                tvMenu.setEnabled(false);
            }
            return view;
        }

    }
}
